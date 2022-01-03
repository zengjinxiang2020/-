/**
* Copyright (C) 2018-2022
* All rights reserved, Designed By www.yixiang.co
* 注意：
* 本软件为www.yixiang.co开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/
package co.yixiang.modules.template.service;

import co.yixiang.common.service.BaseService;
import co.yixiang.modules.template.domain.YxShippingTemplates;
import co.yixiang.modules.template.service.dto.ShippingTemplatesDto;
import co.yixiang.modules.template.service.dto.YxShippingTemplatesDto;
import co.yixiang.modules.template.service.dto.YxShippingTemplatesQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author hupeng
* @date 2020-06-29
*/
public interface YxShippingTemplatesService  extends BaseService<YxShippingTemplates>{

    /**
     * 新增与更新模板
     * @param id 模板id
     * @param shippingTemplatesDto ShippingTemplatesDto
     */
    void addAndUpdate(Integer id,ShippingTemplatesDto shippingTemplatesDto);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxShippingTemplatesQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxShippingTemplatesDto>
    */
    List<YxShippingTemplates> queryAll(YxShippingTemplatesQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxShippingTemplatesDto> all, HttpServletResponse response) throws IOException;
}
