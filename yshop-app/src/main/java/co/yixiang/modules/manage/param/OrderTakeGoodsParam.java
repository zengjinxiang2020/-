package co.yixiang.modules.manage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName OrderTakeGoodsParam
 * @Author Shuo Xing <610796224@qq.com>
 * @Date 2019/11/26
 **/
@Data
public class OrderTakeGoodsParam implements Serializable {

    @NotBlank(message = "订单编号错误")
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "拿货状态")
    private Integer takeGoods;
}
