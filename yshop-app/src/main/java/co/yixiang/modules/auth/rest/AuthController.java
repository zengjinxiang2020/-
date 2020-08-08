/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.auth.rest;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import co.yixiang.api.ApiCode;
import co.yixiang.api.ApiResult;
import co.yixiang.api.UnAuthenticatedException;
import co.yixiang.api.YshopException;
import co.yixiang.common.enums.SmsTypeEnum;
import co.yixiang.common.util.JwtToken;
import co.yixiang.common.util.SmsUtils;
import co.yixiang.constant.ShopConstants;
import co.yixiang.enums.ShopCommonEnum;
import co.yixiang.modules.auth.param.HLoginParam;
import co.yixiang.modules.auth.param.LoginParam;
import co.yixiang.modules.auth.param.RegParam;
import co.yixiang.modules.auth.param.VerityParam;
import co.yixiang.modules.services.AuthService;
import co.yixiang.modules.user.domain.YxUser;
import co.yixiang.modules.user.service.YxUserService;
import co.yixiang.utils.RedisUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName 认证服务
 * @Author hupeng <610796224@qq.com>
 * @Date 2020/4/30
 **/
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "认证模块", tags = "商城:认证", description = "认证")
public class AuthController {

    private final YxUserService userService;
    private final RedisUtils redisUtil;
    private final AuthService authService;

    @Value("${single.login}")
    private Boolean singleLogin;



    /**
     * 小程序登陆接口
     */
    @PostMapping("/wxapp/auth")
    @ApiOperation(value = "小程序登陆", notes = "小程序登陆")
    public ApiResult<Map<String, Object>> login(@Validated @RequestBody LoginParam loginParam,
                                                HttpServletRequest request) {

            YxUser yxUser = authService.wxappLogin(loginParam);
            String token =  JwtToken.makeToken(yxUser.getUid());
            String expiresTimeStr = JwtToken.getExpireTime(token);

            // 返回 token
            Map<String, Object> map = new LinkedHashMap<>(2);

            map.put("token", token);
            map.put("expires_time", expiresTimeStr);

            // 保存在线信息
            authService.save(yxUser, token, request);
            if(singleLogin){
                authService.checkLoginOnUser(yxUser.getUsername(),token);
            }


            return ApiResult.ok(map).setMsg("登陆成功");

    }

    /**
     * 微信公众号授权
     */
    @GetMapping("/wechat/auth")
    @ApiOperation(value = "微信公众号授权", notes = "微信公众号授权")
    public ApiResult<Map<String, Object>> authLogin(@RequestParam(value = "code") String code,
                                                    @RequestParam(value = "spread") String spread,
                                                    HttpServletRequest request) {

            YxUser yxUser = authService.wechatLogin(code,spread);
            String token =  JwtToken.makeToken(yxUser.getUid());
            String expiresTimeStr = JwtToken.getExpireTime(token);



            // 返回 token
            Map<String, Object> map = new HashMap<String, Object>(2) {{
                put("token", token);
                put("expires_time", expiresTimeStr);
            }};

            // 保存在线信息
            authService.save(yxUser, token, request);
            if(singleLogin){
                authService.checkLoginOnUser(yxUser.getUsername(),token);
            }

            return ApiResult.ok(map).setMsg("登陆成功");


    }


    @ApiOperation("H5登录授权")
    @PostMapping(value = "/login")
    public ApiResult<Map<String, Object>> login(@Validated @RequestBody HLoginParam loginDTO,HttpServletRequest request) {
        YxUser yxUser = userService.getOne(Wrappers.<YxUser>lambdaQuery()
                .eq(YxUser::getUsername,loginDTO.getUsername())
                .eq(YxUser::getPassword,SecureUtil.md5(loginDTO.getPassword())),false);

        if(yxUser == null) throw new YshopException("账号或者密码不正确");

        String token =  JwtToken.makeToken(yxUser.getUid());
        String expiresTimeStr = JwtToken.getExpireTime(token);

        // 保存在线信息
        authService.save(yxUser, token, request);
        // 返回 token
        Map<String, Object> map = new HashMap<String, Object>(2) {{
            put("token", token);
            put("expires_time", expiresTimeStr);
        }};

        userService.setSpread(loginDTO.getSpread(),yxUser.getUid());

        if(singleLogin){
            //踢掉之前已经登录的token
            authService.checkLoginOnUser(yxUser.getUsername(),token);
        }

        return ApiResult.ok(map).setMsg("登陆成功");
    }


    @PostMapping("/register")
    @ApiOperation(value = "H5/APP注册新用户", notes = "H5/APP注册新用户")
    public ApiResult<String> register(@Validated @RequestBody RegParam param) {
        Object codeObj = redisUtil.get("code_" + param.getAccount());
        if(codeObj == null){
            return ApiResult.fail("请先获取验证码");
        }
        String code = codeObj.toString();
        if (!StrUtil.equals(code, param.getCaptcha())) {
            return ApiResult.fail("验证码错误");
        }
        YxUser yxUser = userService.getOne(Wrappers.<YxUser>lambdaQuery()
                .eq(YxUser::getPhone,param.getAccount()),false);
        if (ObjectUtil.isNotNull(yxUser)) {
            return ApiResult.fail("用户已存在");
        }

        authService.register(param);
        return ApiResult.ok("","注册成功");
    }


    @PostMapping("/register/verify")
    @ApiOperation(value = "短信验证码发送", notes = "短信验证码发送")
    public ApiResult<String> verify(@Validated @RequestBody VerityParam param) {
        YxUser yxUser = userService.getOne(Wrappers.<YxUser>lambdaQuery()
                .eq(YxUser::getPhone,param.getPhone()),false);
        if (SmsTypeEnum.REGISTER.getValue().equals(param.getType()) && ObjectUtil.isNotNull(yxUser)) {
            return ApiResult.fail("手机号已注册");
        }
        if (SmsTypeEnum.LOGIN.getValue().equals(param.getType()) && ObjectUtil.isNull(yxUser)) {
            return ApiResult.fail("账号不存在");
        }
        String codeKey = "code_" + param.getPhone();
        if (ObjectUtil.isNotNull(redisUtil.get(codeKey))) {
            return ApiResult.fail("10分钟内有效:" + redisUtil.get(codeKey).toString());
        }
        String code = RandomUtil.randomNumbers(ShopConstants.YSHOP_SMS_SIZE);

        //redis存储
        redisUtil.set(codeKey, code, ShopConstants.YSHOP_SMS_REDIS_TIME);

        String enable = redisUtil.getY("sms_enable");
        if (ShopCommonEnum.ENABLE_2.getValue().toString().equals(enable)) {
            return ApiResult.fail("测试阶段验证码:" + code);
        }

        //发送阿里云短信
        JSONObject json = new JSONObject();
        json.put("code",code);
        try {
            SmsUtils.sendSms(param.getPhone(),json.toJSONString());
        } catch (ClientException e) {
            redisUtil.del(codeKey);
            e.printStackTrace();
            return ApiResult.ok("发送失败："+e.getErrMsg());
       }
        return ApiResult.ok("发送成功，请注意查收");


    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @PostMapping(value = "/auth/logout")
    public ApiResult<String> logout(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String[] tokens = bearerToken.split(" ");
        String token = tokens[1];
        authService.logout(token);
        return ApiResult.ok("退出成功");
    }



}
