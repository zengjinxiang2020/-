package co.yixiang.modules.activity.domain;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author xuwenbo
* @date 2020-05-13
*/
@Entity
@Data
@Table(name="yx_store_coupon")
public class YxStoreCoupon implements Serializable {

    /** 优惠券表ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    /** 优惠券名称 */
    @Column(name = "title",nullable = false)
    @NotBlank
    private String title;


    /** 兑换消耗积分值 */
    @Column(name = "integral",nullable = false)
    @NotNull
    private Integer integral;


    /** 兑换的优惠券面值 */
    @Column(name = "coupon_price",nullable = false)
    @NotNull
    private BigDecimal couponPrice;


    /** 最低消费多少金额可用优惠券 */
    @Column(name = "use_min_price",nullable = false)
    @NotNull
    private BigDecimal useMinPrice;


    /** 优惠券有效期限（单位：天） */
    @Column(name = "coupon_time",nullable = false)
    @NotNull
    private Integer couponTime;


    /** 排序 */
    @Column(name = "sort",nullable = false)
    @NotNull
    private Integer sort;


    /** 状态（0：关闭，1：开启） */
    @Column(name = "status",nullable = false)
    @NotNull
    private Integer status;


    /** 兑换项目添加时间 */
    @Column(name = "add_time",nullable = false)
    @NotNull
    private Integer addTime;


    /** 是否删除 */
    @Column(name = "is_del",nullable = false)
    @NotNull
    private Integer isDel;


    public void copy(YxStoreCoupon source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}