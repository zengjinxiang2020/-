package co.yixiang.modules.activity.service;
import co.yixiang.common.service.BaseService;
import co.yixiang.modules.activity.domain.YxStoreCouponIssueUser;
import co.yixiang.modules.activity.service.dto.YxStoreCouponIssueUserDto;
import co.yixiang.modules.activity.service.dto.YxStoreCouponIssueUserQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author hupeng
* @date 2020-05-13
*/
public interface YxStoreCouponIssueUserService  extends BaseService<YxStoreCouponIssueUser>{

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreCouponIssueUserQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreCouponIssueUserDto>
    */
    List<YxStoreCouponIssueUser> queryAll(YxStoreCouponIssueUserQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreCouponIssueUserDto> all, HttpServletResponse response) throws IOException;
}