/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.service.impl;

import co.yixiang.modules.activity.domain.YxStoreVisit;
import co.yixiang.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.ValidationUtil;
import co.yixiang.utils.FileUtil;
import co.yixiang.modules.activity.service.YxStoreVisitService;
import co.yixiang.modules.activity.service.dto.YxStoreVisitDto;
import co.yixiang.modules.activity.service.dto.YxStoreVisitQueryCriteria;
import co.yixiang.modules.activity.service.mapper.YxStoreVisitMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import co.yixiang.utils.PageUtil;
import co.yixiang.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author hupeng
* @date 2020-05-13
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxStoreVisit")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreVisitServiceImpl extends BaseServiceImpl<YxStoreVisitMapper, YxStoreVisit> implements YxStoreVisitService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreVisitQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreVisit> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStoreVisitDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreVisit> queryAll(YxStoreVisitQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreVisit.class, criteria));
    }


    @Override
    public void download(List<YxStoreVisitDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreVisitDto yxStoreVisit : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("产品ID", yxStoreVisit.getProductId());
            map.put("产品类型", yxStoreVisit.getProductType());
            map.put("产品分类ID", yxStoreVisit.getCateId());
            map.put("产品类型", yxStoreVisit.getType());
            map.put("用户ID", yxStoreVisit.getUid());
            map.put("访问次数", yxStoreVisit.getCount());
            map.put("备注描述", yxStoreVisit.getContent());
            map.put("添加时间", yxStoreVisit.getAddTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
