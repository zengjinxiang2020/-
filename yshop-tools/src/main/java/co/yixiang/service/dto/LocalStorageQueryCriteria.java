package co.yixiang.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import co.yixiang.annotation.Query;

/**
* @author hupeng
* @date 2020-05-13
*/
@Data
public class LocalStorageQueryCriteria{

    @Query(blurry = "name,suffix,type,operate,size")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
