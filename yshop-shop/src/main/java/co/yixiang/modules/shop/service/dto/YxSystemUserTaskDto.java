/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.shop.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author hupeng
* @date 2020-05-12
*/
@Data
public class YxSystemUserTaskDto implements Serializable {

    private Integer id;

    /** 任务名称 */
    private String name;

    /** 配置原名 */
    private String realName;

    /** 任务类型 */
    private String taskType;

    /** 限定数 */
    private Integer number;

    /** 等级id */
    private Integer levelId;

    /** 排序 */
    private Integer sort;

    /** 是否显示 */
    private Integer isShow;

    /** 是否务必达成任务,1务必达成,0=满足其一 */
    private Integer isMust;

    /** 任务说明 */
    private String illustrate;

    /** 新增时间 */
    private Integer addTime;
}
