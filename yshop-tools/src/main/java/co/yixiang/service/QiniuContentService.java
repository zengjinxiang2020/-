package co.yixiang.service;
import co.yixiang.common.service.BaseService;
import co.yixiang.domain.QiniuContent;
import co.yixiang.service.dto.QiniuContentDto;
import co.yixiang.service.dto.QiniuContentQueryCriteria;
import co.yixiang.service.dto.QiniuQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author hupeng
* @date 2020-05-13
*/
public interface QiniuContentService  extends BaseService<QiniuContent>{

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(QiniuQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<QiniuContentDto>
    */
    List<QiniuContent> queryAll(QiniuQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<QiniuContentDto> all, HttpServletResponse response) throws IOException;
}
