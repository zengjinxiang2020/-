package co.yixiang.service.mapper;

import co.yixiang.common.mapper.CoreMapper;
import co.yixiang.domain.QiniuContent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author hupeng
* @date 2020-05-13
*/
@Repository
@Mapper
public interface QiniuContentMapper extends CoreMapper<QiniuContent> {

}