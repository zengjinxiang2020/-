/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.shop.service.impl;

import co.yixiang.modules.shop.domain.YxSystemConfig;
import co.yixiang.common.service.impl.BaseServiceImpl;
import co.yixiang.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.modules.shop.service.YxSystemConfigService;
import co.yixiang.modules.shop.service.dto.YxSystemConfigDto;
import co.yixiang.modules.shop.service.dto.YxSystemConfigQueryCriteria;
import co.yixiang.modules.shop.service.mapper.SystemConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author hupeng
* @date 2020-05-12
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxSystemConfig")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxSystemConfigServiceImpl extends BaseServiceImpl<SystemConfigMapper, YxSystemConfig> implements YxSystemConfigService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemConfigQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemConfig> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxSystemConfigDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemConfig> queryAll(YxSystemConfigQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemConfig.class, criteria));
    }


    @Override
    public void download(List<YxSystemConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemConfigDto yxSystemConfig : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("字段名称", yxSystemConfig.getMenuName());
            map.put("默认值", yxSystemConfig.getValue());
            map.put("排序", yxSystemConfig.getSort());
            map.put("是否隐藏", yxSystemConfig.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public YxSystemConfig findByKey(String key) {
        return this.getOne(new QueryWrapper<YxSystemConfig>().eq("menu_name",key));
    }
}
