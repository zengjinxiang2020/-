package co.yixiang.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuo Xing
 * 同城配送距离相关枚举
 */
@Getter
@AllArgsConstructor
public enum DistributionDistanceEnum {

    DISTRIBUTION_DISTANCE_0(0,"同城配送起送费"),
    DISTRIBUTION_DISTANCE_1(3000,"同城配送起送费"),
    DISTRIBUTION_DISTANCE_2(4000,"同城配送起送费"),
    DISTRIBUTION_DISTANCE_3(5000,"同城配送起送费"),
    DISTRIBUTION_DISTANCE_4(10000,"同城配送起送费");

    private Integer value;
    private String desc;
}
