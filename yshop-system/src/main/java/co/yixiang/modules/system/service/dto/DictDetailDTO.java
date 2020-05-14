/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.system.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author hupeng
* @date 2019-04-10
*/
@Getter
@Setter
public class DictDetailDTO implements Serializable {

    private Long id;

    private String label;

    private String value;

    private String sort;

    private DictSmallDto dict;

    private Timestamp createTime;
}
