package co.yixiang.modules.order.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @ClassName ComputeOrderParam
 * @Author Shuo Xing <610796224@qq.com>
 * @Date 2020/6/22
 **/
@Getter
@Setter
@ToString
public class ComputeOrderParam {
    //@NotBlank(message = "请选择地址")
    @ApiModelProperty(value = "地址ID")
    private String addressId;

    @ApiModelProperty(value = "优惠券ID")
    private String couponId;

    //@NotBlank(message = "请选择支付方式")
    @ApiModelProperty(value = "支付方式")
    private String payType;

    @ApiModelProperty(value = "使用积分 1-表示使用")
    private String useIntegral;

    @JsonProperty(value = "shipping_type")
    @ApiModelProperty(value = "配送方式 1=快递 ，2=门店自提  3=同城配送")
    private String shippingType;

    @ApiModelProperty(value = "砍价ID")
    private String bargainId;

    @ApiModelProperty(value = "拼团ID")
    private String pinkId;

    @ApiModelProperty(value = "拼团ID")
    private String combinationId;
}
