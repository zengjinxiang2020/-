package co.yixiang.constant;

/**
 * 商城统一常量
 * @author hupeng
 * @since 2020-02-27
 */
public interface ShopConstants {

	/**
	 * 订单自动取消时间（分钟）
	 */
	long ORDER_OUTTIME_UNPAY = 30;
	/**
	 * 订单自动收货时间（天）
	 */
	long ORDER_OUTTIME_UNCONFIRM = 7;
	/**
	 * redis订单未付款key
	 */
	String REDIS_ORDER_OUTTIME_UNPAY = "order:unpay:";
	/**
	 * redis订单收货key
	 */
	String REDIS_ORDER_OUTTIME_UNCONFIRM = "order:unconfirm:";

	/**
	 * 微信支付service
	 */
	String YSHOP_WEIXIN_PAY_SERVICE = "yshop_weixin_pay_service";

	/**
	 * 微信公众号service
	 */
	String YSHOP_WEIXIN_MP_SERVICE = "yshop_weixin_mp_service";



}
