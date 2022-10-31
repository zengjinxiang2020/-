package co.yixiang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuo Xing
 * 商家经纬度相关枚举
 */
@Getter
@AllArgsConstructor
public enum DistributionEnum {

    BASIC_DISTRIBUTION(6.5,"同城配送起送费"),
    DISTRIBUTION(7.0,"5公里同城配送起送费");

    private Double value;
    private String desc;
}
