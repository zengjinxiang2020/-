package co.yixiang.modules.shop.service.impl;

import co.yixiang.modules.shop.domain.YxSystemGroupData;
import co.yixiang.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.ValidationUtil;
import co.yixiang.utils.FileUtil;
import co.yixiang.modules.shop.service.YxSystemGroupDataService;
import co.yixiang.modules.shop.service.dto.YxSystemGroupDataDto;
import co.yixiang.modules.shop.service.dto.YxSystemGroupDataQueryCriteria;
import co.yixiang.modules.shop.service.mapper.YxSystemGroupDataMapper;
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
* @date 2020-05-12
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxSystemGroupData")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxSystemGroupDataServiceImpl extends BaseServiceImpl<YxSystemGroupDataMapper, YxSystemGroupData> implements YxSystemGroupDataService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemGroupDataQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemGroupData> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxSystemGroupDataDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemGroupData> queryAll(YxSystemGroupDataQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemGroupData.class, criteria));
    }


    @Override
    public void download(List<YxSystemGroupDataDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemGroupDataDto yxSystemGroupData : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("对应的数据名称", yxSystemGroupData.getGroupName());
            map.put("数据组对应的数据值（json数据）", yxSystemGroupData.getValue());
            map.put("添加数据时间", yxSystemGroupData.getAddTime());
            map.put("数据排序", yxSystemGroupData.getSort());
            map.put("状态（1：开启；2：关闭；）", yxSystemGroupData.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}