/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * rediskey 相关枚举
 */
@Getter
@AllArgsConstructor
public enum  RedisKeyEnum {

    WXAPP_APPID("wxapp_appId","微信小程序id"),
    WXAPP_SECRET("wxapp_secret","微信小程序秘钥"),
    WX_NATIVE_APP_APPID("wx_native_app_appId","支付appId"),
    WXPAY_MCHID("wxpay_mchId","商户号"),
    WXPAY_MCHKEY("wxpay_mchKey","商户秘钥"),
    WXPAY_KEYPATH("wxpay_keyPath","商户证书路径"),
    WECHAT_APPID("wechat_appid","微信公众号id"),
    WECHAT_APPSECRET("wechat_appsecret","微信公众号secret"),
    WECHAT_TOKEN("wechat_token","微信公众号验证token"),
    WECHAT_ENCODINGAESKEY("wechat_encodingaeskey","EncodingAESKey"),
    TENGXUN_MAP_KEY("tengxun_map_key","腾讯mapkey");

    private String value;
    private String desc;
}
