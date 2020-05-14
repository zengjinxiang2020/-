/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author hupeng
* @date 2020-05-12
*/
@Data
public class YxStorePinkDto implements Serializable {

    private Integer id;

    /** 用户id */
    private Integer uid;

    /** 订单id 生成 */
    private String orderId;

    /** 订单id  数据库 */
    private Integer orderIdKey;

    /** 购买商品个数 */
    private Integer totalNum;

    /** 购买总金额 */
    private BigDecimal totalPrice;

    /** 拼团产品id */
    private Integer cid;

    /** 产品id */
    private Integer pid;

    /** 拼图总人数 */
    private Integer people;

    /** 拼团产品单价 */
    private BigDecimal price;

    /** 开始时间 */
    private String addTime;

    private String stopTime;

    /** 团长id 0为团长 */
    private Integer kId;

    /** 是否发送模板消息0未发送1已发送 */
    private Integer isTpl;

    /** 是否退款 0未退款 1已退款 */
    private Integer isRefund;

    /** 状态1进行中2已完成3未完成 */
    private Integer status;
}
