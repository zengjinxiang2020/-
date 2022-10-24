package co.yixiang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuo Xing
 * 快递公司编码相关枚举
 */
@Getter
@AllArgsConstructor
public enum ShipperCodeEnum {

    STO("STO","申通快递"),

    YTO("YTO","圆通速递"),

    BSKT("BSKT","百事亨通速递"),

    JTSD("JTSD","极兔速递");

    private String value;
    private String desc;

    public static boolean contains(String value){
        return ShipperCodeEnum.STO.value.equals(value) || ShipperCodeEnum.YTO.value.equals(value)
                || ShipperCodeEnum.BSKT.value.equals(value) || ShipperCodeEnum.JTSD.value.equals(value);
    }
}
