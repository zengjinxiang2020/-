package co.yixiang.mp.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author xuwenbo
* @date 2020-05-12
*/
@Data
public class YxWechatMenuDto implements Serializable {

    private String key;

    /** 缓存数据 */
    private String result;

    /** 缓存时间 */
    private Integer addTime;
}