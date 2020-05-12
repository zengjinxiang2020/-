package co.yixiang.modules.shop.service.impl;

import co.yixiang.modules.shop.domain.YxSystemUserTask;
import co.yixiang.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import co.yixiang.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import co.yixiang.common.utils.QueryHelpPlus;
import co.yixiang.utils.ValidationUtil;
import co.yixiang.utils.FileUtil;
import co.yixiang.modules.shop.service.YxSystemUserTaskService;
import co.yixiang.modules.shop.service.dto.YxSystemUserTaskDto;
import co.yixiang.modules.shop.service.dto.YxSystemUserTaskQueryCriteria;
import co.yixiang.modules.shop.service.mapper.YxSystemUserTaskMapper;
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
//@CacheConfig(cacheNames = "yxSystemUserTask")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxSystemUserTaskServiceImpl extends BaseServiceImpl<YxSystemUserTaskMapper, YxSystemUserTask> implements YxSystemUserTaskService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemUserTaskQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemUserTask> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxSystemUserTaskDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemUserTask> queryAll(YxSystemUserTaskQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemUserTask.class, criteria));
    }


    @Override
    public void download(List<YxSystemUserTaskDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemUserTaskDto yxSystemUserTask : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务名称", yxSystemUserTask.getName());
            map.put("配置原名", yxSystemUserTask.getRealName());
            map.put("任务类型", yxSystemUserTask.getTaskType());
            map.put("限定数", yxSystemUserTask.getNumber());
            map.put("等级id", yxSystemUserTask.getLevelId());
            map.put("排序", yxSystemUserTask.getSort());
            map.put("是否显示", yxSystemUserTask.getIsShow());
            map.put("是否务必达成任务,1务必达成,0=满足其一", yxSystemUserTask.getIsMust());
            map.put("任务说明", yxSystemUserTask.getIllustrate());
            map.put("新增时间", yxSystemUserTask.getAddTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}