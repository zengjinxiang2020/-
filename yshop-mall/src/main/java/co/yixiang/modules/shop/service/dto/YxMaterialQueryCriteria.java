/**
 * Copyright (C) 2018-2021
 * All rights reserved, Designed By www.yixiang.co

 */
package co.yixiang.modules.shop.service.dto;

import co.yixiang.annotation.Query;
import lombok.Data;

/**
* @author hupeng
* @date 2020-05-12
*/
@Data
public class YxMaterialQueryCriteria{

    @Query
    private String groupId;
}
