package co.yixiang.mp.service;
import co.yixiang.common.service.BaseService;
import co.yixiang.mp.domain.YxWechatTemplate;
import co.yixiang.mp.service.dto.YxWechatTemplateDto;
import co.yixiang.mp.service.dto.YxWechatTemplateQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author xuwenbo
* @date 2020-05-12
*/
public interface YxWechatTemplateService  extends BaseService<YxWechatTemplate>{

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxWechatTemplateQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxWechatTemplateDto>
    */
    List<YxWechatTemplate> queryAll(YxWechatTemplateQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxWechatTemplateDto> all, HttpServletResponse response) throws IOException;

    YxWechatTemplate findByTempkey(String recharge_success_key);
}
