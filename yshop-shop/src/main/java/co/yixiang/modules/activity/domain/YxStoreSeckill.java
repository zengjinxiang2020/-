/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.domain;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author hupeng
* @date 2020-05-13
*/
@Data
@TableName("yx_store_seckill")
public class YxStoreSeckill implements Serializable {

    /** 商品秒杀产品表id */
    @TableId
    private Integer id;


    /** 商品id */
    private Integer productId;


    /** 推荐图 */
    private String image;


    /** 轮播图 */
    private String images;


    /** 活动标题 */
    private String title;


    /** 简介 */
    private String info;


    /** 价格 */
    private BigDecimal price;


    /** 成本 */
    private BigDecimal cost;


    /** 原价 */
    private BigDecimal otPrice;


    /** 返多少积分 */
    private BigDecimal giveIntegral;


    /** 排序 */
    private Integer sort;


    /** 库存 */
    private Integer stock;


    /** 销量 */
    private Integer sales;


    /** 单位名 */
    private String unitName;


    /** 邮费 */
    private BigDecimal postage;


    /** 内容 */
    private String description;


    /** 开始时间 */
    private Integer startTime;


    /** 结束时间 */
    private Integer stopTime;


    /** 添加时间 */
    private String addTime;


    /** 产品状态 */
    private Integer status;


    /** 是否包邮 */
    private Integer isPostage;


    /** 热门推荐 */
    private Integer isHot;


    /** 删除 0未删除1已删除 */
    private Integer isDel;


    /** 最多秒杀几个 */
    private Integer num;


    /** 显示 */
    private Integer isShow;


    private Timestamp endTimeDate;


    private Timestamp startTimeDate;


    /** 时间段id */
    private Integer timeId;


    public void copy(YxStoreSeckill source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
