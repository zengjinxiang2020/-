package co.yixiang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author Shuo Xing
 * 同城配送费用相关枚举
 */
@Getter
@AllArgsConstructor
public enum ShopLocationEnum {

    SHOP_LONGITUDE(114.071907,"不在团内"),
    SHOP_LATITUDE(22.91304,"在团内");

    private Double value;
    private String desc;

}
