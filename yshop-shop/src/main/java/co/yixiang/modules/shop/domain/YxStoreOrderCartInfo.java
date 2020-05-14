/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.shop.domain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import java.io.Serializable;

/**
* @author hupeng
* @date 2020-05-12
*/

@Data
@TableName("yx_store_order_cart_info")
public class YxStoreOrderCartInfo implements Serializable {

    @TableId
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
    private Integer id;


    /** 订单id */
    //@Column(name = "oid",nullable = false)
   //@NotNull
    private Integer oid;


    /** 购物车id */
    //@Column(name = "cart_id",nullable = false)
   //@NotNull
    private Integer cartId;


    /** 商品ID */
    //@Column(name = "product_id",nullable = false)
   //@NotNull
    private Integer productId;


    /** 购买东西的详细信息 */
    //@Column(name = "cart_info",nullable = false)
    //@NotBlank
    private String cartInfo;


    /** 唯一id */
        @TableField(value = "`unique`")
    //@NotBlank
    private String unique;


    public void copy(YxStoreOrderCartInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
