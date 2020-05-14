/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.service.impl;

import co.yixiang.modules.activity.domain.YxUserExtract;
import co.yixiang.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.ValidationUtil;
import co.yixiang.utils.FileUtil;
import co.yixiang.modules.activity.service.YxUserExtractService;
import co.yixiang.modules.activity.service.dto.YxUserExtractDto;
import co.yixiang.modules.activity.service.dto.YxUserExtractQueryCriteria;
import co.yixiang.modules.activity.service.mapper.YxUserExtractMapper;
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
//@CacheConfig(cacheNames = "yxUserExtract")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxUserExtractServiceImpl extends BaseServiceImpl<YxUserExtractMapper, YxUserExtract> implements YxUserExtractService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxUserExtractQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxUserExtract> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxUserExtractDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxUserExtract> queryAll(YxUserExtractQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxUserExtract.class, criteria));
    }


    @Override
    public void download(List<YxUserExtractDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxUserExtractDto yxUserExtract : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" uid",  yxUserExtract.getUid());
            map.put("名称", yxUserExtract.getRealName());
            map.put("bank = 银行卡 alipay = 支付宝wx=微信", yxUserExtract.getExtractType());
            map.put("银行卡", yxUserExtract.getBankCode());
            map.put("开户地址", yxUserExtract.getBankAddress());
            map.put("支付宝账号", yxUserExtract.getAlipayCode());
            map.put("提现金额", yxUserExtract.getExtractPrice());
            map.put(" mark",  yxUserExtract.getMark());
            map.put(" balance",  yxUserExtract.getBalance());
            map.put("无效原因", yxUserExtract.getFailMsg());
            map.put(" failTime",  yxUserExtract.getFailTime());
            map.put("添加时间", yxUserExtract.getAddTime());
            map.put("-1 未通过 0 审核中 1 已提现", yxUserExtract.getStatus());
            map.put("微信号", yxUserExtract.getWechat());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
