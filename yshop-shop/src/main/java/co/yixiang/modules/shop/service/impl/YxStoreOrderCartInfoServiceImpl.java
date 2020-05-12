package co.yixiang.modules.shop.service.impl;

import co.yixiang.modules.shop.domain.YxStoreOrderCartInfo;
import co.yixiang.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.FileUtil;
import co.yixiang.modules.shop.service.YxStoreOrderCartInfoService;
import co.yixiang.modules.shop.service.dto.YxStoreOrderCartInfoDto;
import co.yixiang.modules.shop.service.dto.YxStoreOrderCartInfoQueryCriteria;
import co.yixiang.modules.shop.service.mapper.YxStoreOrderCartInfoMapper;
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
//@CacheConfig(cacheNames = "yxStoreOrderCartInfo")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreOrderCartInfoServiceImpl extends BaseServiceImpl<YxStoreOrderCartInfoMapper, YxStoreOrderCartInfo> implements YxStoreOrderCartInfoService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreOrderCartInfoQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreOrderCartInfo> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStoreOrderCartInfoDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreOrderCartInfo> queryAll(YxStoreOrderCartInfoQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreOrderCartInfo.class, criteria));
    }


    @Override
    public void download(List<YxStoreOrderCartInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreOrderCartInfoDto yxStoreOrderCartInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("订单id", yxStoreOrderCartInfo.getOid());
            map.put("购物车id", yxStoreOrderCartInfo.getCartId());
            map.put("商品ID", yxStoreOrderCartInfo.getProductId());
            map.put("购买东西的详细信息", yxStoreOrderCartInfo.getCartInfo());
            map.put("唯一id", yxStoreOrderCartInfo.getUnique());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
