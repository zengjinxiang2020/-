/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.mp.config;

import co.yixiang.constant.ShopConstants;
import co.yixiang.enums.RedisKeyEnum;
import co.yixiang.mp.handler.RedisHandler;
import co.yixiang.utils.RedisUtil;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 支付配置
 * @author hupeng
 * @date 2020/03/01
 */
@Slf4j
@Configuration
public class WxPayConfiguration {

	private static Map<String, WxPayService> payServices = Maps.newHashMap();

	private static RedisHandler redisHandler;

	@Autowired
	public WxPayConfiguration(RedisHandler redisHandler) {
		this.redisHandler = redisHandler;
	}

	/**
	 *  获取WxPayService
	 * @return
	 */
	public static WxPayService getPayService() {
		WxPayService wxPayService = payServices.get(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE);
        if(wxPayService == null || RedisUtil.get(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE) == null) {
			WxPayConfig payConfig = new WxPayConfig();
			payConfig.setAppId(RedisUtil.get(RedisKeyEnum.WECHAT_APPID.getValue()));
			payConfig.setMchId(RedisUtil.get(RedisKeyEnum.WXPAY_MCHID.getValue()));
			payConfig.setMchKey(RedisUtil.get(RedisKeyEnum.WXPAY_MCHKEY.getValue()));
			payConfig.setKeyPath(RedisUtil.get(RedisKeyEnum.WXPAY_KEYPATH.getValue()));
			// 可以指定是否使用沙箱环境
			payConfig.setUseSandboxEnv(false);
			wxPayService = new WxPayServiceImpl();
			wxPayService.setConfig(payConfig);
			payServices.put(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE, wxPayService);

			//增加标识
			RedisUtil.set(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE,"yshop");
        }
		return wxPayService;
    }

	/**
	 *  获取小程序WxAppPayService
	 * @return
	 */
	public static WxPayService getWxAppPayService() {
		WxPayService wxPayService = payServices.get(ShopConstants.YSHOP_WEIXIN_MINI_PAY_SERVICE);
		if(wxPayService == null || RedisUtil.get(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE) == null) {
			WxPayConfig payConfig = new WxPayConfig();
			payConfig.setAppId(RedisUtil.get(RedisKeyEnum.WXAPP_APPID.getValue()));
			payConfig.setMchId(RedisUtil.get(RedisKeyEnum.WXPAY_MCHID.getValue()));
			payConfig.setMchKey(RedisUtil.get(RedisKeyEnum.WXPAY_MCHKEY.getValue()));
			payConfig.setKeyPath(RedisUtil.get(RedisKeyEnum.WXPAY_KEYPATH.getValue()));
			// 可以指定是否使用沙箱环境
			payConfig.setUseSandboxEnv(false);
			wxPayService = new WxPayServiceImpl();
			wxPayService.setConfig(payConfig);
			payServices.put(ShopConstants.YSHOP_WEIXIN_MINI_PAY_SERVICE, wxPayService);

			//增加标识
			RedisUtil.set(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE,"yshop");
		}
		return wxPayService;
	}

	/**
	 *  获取APPPayService
	 * @return
	 */
	public static WxPayService getAppPayService() {
		WxPayService wxPayService = payServices.get(ShopConstants.YSHOP_WEIXIN_APP_PAY_SERVICE);
		if(wxPayService == null || RedisUtil.get(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE) == null) {
			WxPayConfig payConfig = new WxPayConfig();
			payConfig.setAppId(RedisUtil.get(RedisKeyEnum.WX_NATIVE_APP_APPID.getValue()));
			payConfig.setMchId(RedisUtil.get(RedisKeyEnum.WXPAY_MCHID.getValue()));
			payConfig.setMchKey(RedisUtil.get(RedisKeyEnum.WXPAY_MCHKEY.getValue()));
			payConfig.setKeyPath(RedisUtil.get(RedisKeyEnum.WXPAY_KEYPATH.getValue()));
			// 可以指定是否使用沙箱环境
			payConfig.setUseSandboxEnv(false);
			wxPayService = new WxPayServiceImpl();
			wxPayService.setConfig(payConfig);
			payServices.put(ShopConstants.YSHOP_WEIXIN_APP_PAY_SERVICE, wxPayService);

			//增加标识
			RedisUtil.set(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE,"yshop");
		}
		return wxPayService;
	}

	/**
	 * 移除WxPayService
	 */
	public static void removeWxPayService(){
		RedisUtil.del(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE);
		payServices.remove(ShopConstants.YSHOP_WEIXIN_PAY_SERVICE);
		payServices.remove(ShopConstants.YSHOP_WEIXIN_MINI_PAY_SERVICE);
		payServices.remove(ShopConstants.YSHOP_WEIXIN_APP_PAY_SERVICE);
	}



}
