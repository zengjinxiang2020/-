package co.yixiang.modules.wechat.web.controller;

import cn.hutool.core.util.StrUtil;
import co.yixiang.annotation.AnonymousAccess;
import co.yixiang.common.api.ApiResult;
import co.yixiang.common.web.controller.BaseController;
import co.yixiang.modules.order.entity.YxStoreOrder;
import co.yixiang.modules.order.service.YxStoreOrderService;
import co.yixiang.modules.order.web.vo.YxStoreOrderQueryVo;
import co.yixiang.modules.shop.service.YxSystemConfigService;
import co.yixiang.mp.config.WxMpConfiguration;
import co.yixiang.utils.RedisUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName WechatController
 * @Author hupeng <610796224@qq.com>
 * @Date 2019/11/5
 **/
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "微信模块", tags = "微信模块", description = "微信模块")
public class WechatController extends BaseController {

    private final WxPayService wxPayService;
    private final YxStoreOrderService orderService;
    private final YxSystemConfigService systemConfigService;


    /**
     * 微信分享配置
     */
    @AnonymousAccess
    @GetMapping("/share")
    @ApiOperation(value = "微信分享配置",notes = "微信分享配置")
    public ApiResult<Object> share() {
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("img",systemConfigService.getData("wechat_share_img"));
        map.put("title",systemConfigService.getData("wechat_share_title"));
        map.put("synopsis",systemConfigService.getData("wechat_share_synopsis"));
        Map<String,Object> mapt = new LinkedHashMap<>();
        mapt.put("data",map);
        return ApiResult.ok(mapt);
    }

    /**
     * jssdk配置
     */
    @AnonymousAccess
    @GetMapping("/wechat/config")
    @ApiOperation(value = "jssdk配置",notes = "jssdk配置")
    public ApiResult<Object> jsConfig(@RequestParam(value = "url") String url) throws WxErrorException {
        String appId = RedisUtil.get("wechat_appid");
        if(StrUtil.isBlank(appId)) return ApiResult.fail("请配置公众号");
        WxMpService wxService = WxMpConfiguration.getWxMpService(appId);
        return ApiResult.ok(wxService.createJsapiSignature(url));
    }



    /**
     * 微信支付回调
     */
    @AnonymousAccess
    @PostMapping("/wechat/notify")
    @ApiOperation(value = "微信支付回调",notes = "微信支付回调")
    public String notify(@RequestBody String xmlData) {
        try {
            WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
            String orderId = notifyResult.getOutTradeNo();
            YxStoreOrderQueryVo orderInfo = orderService.getOrderInfo(orderId,0);
            if(orderInfo.getPaid() == 1){
                return WxPayNotifyResponse.success("处理成功!");
            }

            orderService.paySuccess(orderInfo.getOrderId(),"weixin");

            return WxPayNotifyResponse.success("处理成功!");
        } catch (WxPayException e) {
            log.error(e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }

    }

    /**
     * 微信退款回调
     * @param xmlData
     * @return
     * @throws WxPayException
     */
    @AnonymousAccess
    @ApiOperation(value = "退款回调通知处理",notes = "退款回调通知处理")
    @PostMapping("/notify/refund")
    public String parseRefundNotifyResult(@RequestBody String xmlData) {
        try {
            WxPayRefundNotifyResult result = wxPayService.parseRefundNotifyResult(xmlData);
            String orderId = result.getReqInfo().getOutTradeNo();
            Integer refundFee = result.getReqInfo().getRefundFee()/100;
            YxStoreOrderQueryVo orderInfo = orderService.getOrderInfo(orderId,0);
            if(orderInfo.getRefundStatus() == 2){
                return WxPayNotifyResponse.success("处理成功!");
            }
            YxStoreOrder storeOrder = new YxStoreOrder();
            //修改状态
            storeOrder.setId(orderInfo.getId());
            storeOrder.setRefundStatus(2);
            storeOrder.setRefundPrice(BigDecimal.valueOf(refundFee));
            orderService.updateById(storeOrder);
            return WxPayNotifyResponse.success("处理成功!");
        } catch (WxPayException e) {
            log.error(e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }


    /**
     * 微信验证消息
     */
    @AnonymousAccess
    @GetMapping("/wechat/serve")
    @ApiOperation(value = "微信验证消息",notes = "微信验证消息")
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr){

        String appId = RedisUtil.get("wechat_appid");
        if(StrUtil.isBlank(appId)) return "请配置公众号";
        WxMpService wxService = WxMpConfiguration.getWxMpService(appId);
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "fail";
    }


    @AnonymousAccess
    @PostMapping("/wechat/serve")
    @ApiOperation(value = "微信获取消息",notes = "微信获取消息")
    public void post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        String appId = RedisUtil.get("wechat_appid");
        if(StrUtil.isBlank(appId)) {
            log.error("请配置公众号！");
            return;
        }
        WxMpService wxService = WxMpConfiguration.getWxMpService(appId);

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage,appId);
            if(outMessage == null) return;
            out = outMessage.toXml();;
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
                    timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = this.route(inMessage,appId);
            if(outMessage == null) return;

            out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
        }

        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(out);
        writer.close();
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message,String appId) {
        try {
            return WxMpConfiguration.getWxMpMessageRouter(appId).route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }

        return null;
    }




}
