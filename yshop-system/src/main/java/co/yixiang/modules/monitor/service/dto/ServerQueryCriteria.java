package co.yixiang.modules.monitor.service.dto;

import co.yixiang.annotation.Query;
import lombok.Data;

/**
* @author Zhang houying
* @date 2019-11-03
*/
@Data
public class ServerQueryCriteria{

    @Query(blurry = "name,address")
    private String blurry;
}
