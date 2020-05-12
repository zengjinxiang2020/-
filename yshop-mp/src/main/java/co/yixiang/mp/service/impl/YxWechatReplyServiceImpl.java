package co.yixiang.mp.service.impl;

import co.yixiang.mp.domain.YxWechatReply;
import co.yixiang.exception.EntityExistException;
import co.yixiang.common.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.ValidationUtil;
import co.yixiang.utils.FileUtil;
import co.yixiang.mp.service.YxWechatReplyService;
import co.yixiang.mp.service.dto.YxWechatReplyDto;
import co.yixiang.mp.service.dto.YxWechatReplyQueryCriteria;
import co.yixiang.mp.service.mapper.YxWechatReplyMapper;
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

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author xuwenbo
* @date 2020-05-12
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxWechatReply")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxWechatReplyServiceImpl extends BaseServiceImpl<YxWechatReplyMapper, YxWechatReply> implements YxWechatReplyService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxWechatReplyQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxWechatReply> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxWechatReplyDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxWechatReply> queryAll(YxWechatReplyQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxWechatReply.class, criteria));
    }


    @Override
    public void download(List<YxWechatReplyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxWechatReplyDto yxWechatReply : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("关键字", yxWechatReply.getKey());
            map.put("回复类型", yxWechatReply.getType());
            map.put("回复数据", yxWechatReply.getData());
            map.put("0=不可用  1 =可用", yxWechatReply.getStatus());
            map.put("是否隐藏", yxWechatReply.getHide());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public YxWechatReply isExist(String key) {
        YxWechatReply yxWechatReply = this.getOne(new QueryWrapper<YxWechatReply>().eq("key",key));
        return yxWechatReply;
    }

    @Override
    public void create(YxWechatReply yxWechatReply) {
        if(this.isExist(yxWechatReply.getKey()) != null){
            throw new EntityExistException(YxWechatReply.class,"key",yxWechatReply.getKey());
        }
        this.save(yxWechatReply);
    }

    @Override
    public void upDate(YxWechatReply resources) {
        YxWechatReply yxWechatReply = this.getById(resources.getId());
        YxWechatReply yxWechatReply1 = null;
        yxWechatReply1 = this.isExist(resources.getKey());
        if(yxWechatReply1 != null && !yxWechatReply1.getId().equals(yxWechatReply.getId())){
            throw new EntityExistException(YxWechatReply.class,"key",resources.getKey());
        }
        yxWechatReply.copy(resources);
        this.save(yxWechatReply);
    }
}
