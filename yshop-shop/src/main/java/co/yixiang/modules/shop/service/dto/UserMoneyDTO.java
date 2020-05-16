/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.shop.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName UserMoneyDTO
 * @Author hupeng <610796224@qq.com>
 * @Date 2019/12/4
 **/
@Data
public class UserMoneyDTO implements Serializable {
   //@NotNull(message = "参数缺失")
    private Integer uid;
   //@NotNull(message = "请选择修改余额方式")
    private Integer ptype;
   //@NotNull(message = "金额必填")
    @Min(message = "最低金额为0",value = 0)
    private Double money;
}
