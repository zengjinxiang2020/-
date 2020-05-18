/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.service.impl;

import co.yixiang.modules.activity.domain.YxStorePink;
import co.yixiang.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.ValidationUtil;
import co.yixiang.utils.FileUtil;
import co.yixiang.modules.activity.service.YxStorePinkService;
import co.yixiang.modules.activity.service.dto.YxStorePinkDto;
import co.yixiang.modules.activity.service.dto.YxStorePinkQueryCriteria;
import co.yixiang.modules.activity.service.mapper.YxStorePinkMapper;
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
//@CacheConfig(cacheNames = "yxStorePink")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStorePinkServiceImpl extends BaseServiceImpl<YxStorePinkMapper, YxStorePink> implements YxStorePinkService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStorePinkQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStorePink> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStorePinkDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStorePink> queryAll(YxStorePinkQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStorePink.class, criteria));
    }


    @Override
    public void download(List<YxStorePinkDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStorePinkDto yxStorePink : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", yxStorePink.getUid());
            map.put("订单id 生成", yxStorePink.getOrderId());
            map.put("订单id  数据库", yxStorePink.getOrderIdKey());
            map.put("购买商品个数", yxStorePink.getTotalNum());
            map.put("购买总金额", yxStorePink.getTotalPrice());
            map.put("拼团产品id", yxStorePink.getCid());
            map.put("产品id", yxStorePink.getPid());
            map.put("拼图总人数", yxStorePink.getPeople());
            map.put("拼团产品单价", yxStorePink.getPrice());
            map.put("开始时间", yxStorePink.getAddTime());
            map.put(" stopTime",  yxStorePink.getStopTime());
            map.put("团长id 0为团长", yxStorePink.getKId());
            map.put("是否发送模板消息0未发送1已发送", yxStorePink.getIsTpl());
            map.put("是否退款 0未退款 1已退款", yxStorePink.getIsRefund());
            map.put("状态1进行中2已完成3未完成", yxStorePink.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
