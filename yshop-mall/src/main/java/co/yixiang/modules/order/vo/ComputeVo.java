package co.yixiang.modules.order.vo;

import co.yixiang.serializer.BigDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName ComputeVo
 * @Author Shuo Xing <610796224@qq.com>
 * @Date 2019/10/27
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComputeVo implements Serializable {

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal couponPrice;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal deductionPrice;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal payPostage;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal payPrice;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal totalPrice;

    private Double usedIntegral; //使用了多少积分

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal payIntegral;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal distribution;
}
