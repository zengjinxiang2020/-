/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.service.impl;

import co.yixiang.modules.activity.domain.YxStoreBargain;
import co.yixiang.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.ValidationUtil;
import co.yixiang.utils.FileUtil;
import co.yixiang.modules.activity.service.YxStoreBargainService;
import co.yixiang.modules.activity.service.dto.YxStoreBargainDto;
import co.yixiang.modules.activity.service.dto.YxStoreBargainQueryCriteria;
import co.yixiang.modules.activity.service.mapper.YxStoreBargainMapper;
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
//@CacheConfig(cacheNames = "yxStoreBargain")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreBargainServiceImpl extends BaseServiceImpl<YxStoreBargainMapper, YxStoreBargain> implements YxStoreBargainService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreBargainQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreBargain> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStoreBargainDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreBargain> queryAll(YxStoreBargainQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreBargain.class, criteria));
    }


    @Override
    public void download(List<YxStoreBargainDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreBargainDto yxStoreBargain : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("关联产品ID", yxStoreBargain.getProductId());
            map.put("砍价活动名称", yxStoreBargain.getTitle());
            map.put("砍价活动图片", yxStoreBargain.getImage());
            map.put("单位名称", yxStoreBargain.getUnitName());
            map.put("库存", yxStoreBargain.getStock());
            map.put("销量", yxStoreBargain.getSales());
            map.put("砍价产品轮播图", yxStoreBargain.getImages());
            map.put("砍价开启时间", yxStoreBargain.getStartTime());
            map.put("砍价结束时间", yxStoreBargain.getStopTime());
            map.put("砍价产品名称", yxStoreBargain.getStoreName());
            map.put("砍价金额", yxStoreBargain.getPrice());
            map.put("砍价商品最低价", yxStoreBargain.getMinPrice());
            map.put("每次购买的砍价产品数量", yxStoreBargain.getNum());
            map.put("用户每次砍价的最大金额", yxStoreBargain.getBargainMaxPrice());
            map.put("用户每次砍价的最小金额", yxStoreBargain.getBargainMinPrice());
            map.put("用户每次砍价的次数", yxStoreBargain.getBargainNum());
            map.put("砍价状态 0(到砍价时间不自动开启)  1(到砍价时间自动开启时间)", yxStoreBargain.getStatus());
            map.put("砍价详情", yxStoreBargain.getDescription());
            map.put("反多少积分", yxStoreBargain.getGiveIntegral());
            map.put("砍价活动简介", yxStoreBargain.getInfo());
            map.put("成本价", yxStoreBargain.getCost());
            map.put("排序", yxStoreBargain.getSort());
            map.put("是否推荐0不推荐1推荐", yxStoreBargain.getIsHot());
            map.put("是否删除 0未删除 1删除", yxStoreBargain.getIsDel());
            map.put("添加时间", yxStoreBargain.getAddTime());
            map.put("是否包邮 0不包邮 1包邮", yxStoreBargain.getIsPostage());
            map.put("邮费", yxStoreBargain.getPostage());
            map.put("砍价规则", yxStoreBargain.getRule());
            map.put("砍价产品浏览量", yxStoreBargain.getLook());
            map.put("砍价产品分享量", yxStoreBargain.getShare());
            map.put(" endTimeDate",  yxStoreBargain.getEndTimeDate());
            map.put(" startTimeDate",  yxStoreBargain.getStartTimeDate());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
