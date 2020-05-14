/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.tools.service.impl;

import co.yixiang.tools.service.dto.QiniuContentDto;
import co.yixiang.tools.domain.QiniuContent;
import co.yixiang.common.service.impl.BaseServiceImpl;
import co.yixiang.tools.service.dto.QiniuQueryCriteria;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.FileUtil;
import co.yixiang.tools.service.QiniuContentService;
import co.yixiang.tools.service.mapper.QiniuContentMapper;
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
* @date 2020-05-13
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "qiniuContent")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QiniuContentServiceImpl extends BaseServiceImpl<QiniuContentMapper, QiniuContent> implements QiniuContentService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(QiniuQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<QiniuContent> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), QiniuContentDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<QiniuContent> queryAll(QiniuQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(QiniuContent.class, criteria));
    }


    @Override
    public void download(List<QiniuContentDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QiniuContentDto qiniuContent : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("Bucket 识别符", qiniuContent.getBucket());
            map.put("文件名称", qiniuContent.getName());
            map.put("文件大小", qiniuContent.getSize());
            map.put("文件类型：私有或公开", qiniuContent.getType());
            map.put("上传或同步的时间", qiniuContent.getUpdateTime());
            map.put("文件url", qiniuContent.getUrl());
            map.put(" suffix",  qiniuContent.getSuffix());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
