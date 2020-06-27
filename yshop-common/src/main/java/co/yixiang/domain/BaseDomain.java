package co.yixiang.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * @ClassName 公共模型
 * @Author hupeng <610796224@qq.com>
 * @Date 2020/6/13
 **/
@Getter
@Setter
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField(fill= FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill= FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Integer isDel;
}
