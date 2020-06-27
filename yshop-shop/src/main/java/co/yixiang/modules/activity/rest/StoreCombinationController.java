/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package co.yixiang.modules.activity.rest;

import cn.hutool.core.util.ObjectUtil;
import co.yixiang.logging.aop.log.Log;
import co.yixiang.modules.activity.domain.YxStoreCombination;
import co.yixiang.modules.activity.service.YxStoreCombinationService;
import co.yixiang.modules.activity.service.dto.YxStoreCombinationQueryCriteria;
import co.yixiang.modules.aop.ForbidSubmit;
import co.yixiang.utils.OrderUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author hupeng
* @date 2019-11-18
*/
@Api(tags = "商城:拼团管理")
@RestController
@RequestMapping("api")
public class StoreCombinationController {

    private final YxStoreCombinationService yxStoreCombinationService;

    public StoreCombinationController(YxStoreCombinationService yxStoreCombinationService) {
        this.yxStoreCombinationService = yxStoreCombinationService;
    }

    @Log("查询拼团")
    @ApiOperation(value = "查询拼团")
    @GetMapping(value = "/yxStoreCombination")
    @PreAuthorize("@el.check('admin','YXSTORECOMBINATION_ALL','YXSTORECOMBINATION_SELECT')")
    public ResponseEntity getYxStoreCombinations(YxStoreCombinationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(yxStoreCombinationService.queryAll(criteria,pageable),HttpStatus.OK);
    }



    @Log("修改拼团")
    @ApiOperation(value = "新增/修改拼团")
    @PutMapping(value = "/yxStoreCombination")
    @PreAuthorize("@el.check('admin','YXSTORECOMBINATION_ALL','YXSTORECOMBINATION_EDIT')")
    public ResponseEntity update(@Validated @RequestBody YxStoreCombination resources){
        if(ObjectUtil.isNull(resources.getId())){
            return new ResponseEntity<>(yxStoreCombinationService.save(resources),HttpStatus.CREATED);
        }else{
            yxStoreCombinationService.saveOrUpdate(resources);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

    }

    @ForbidSubmit
    @ApiOperation(value = "开启关闭")
    @PostMapping(value = "/yxStoreCombination/onsale/{id}")
    public ResponseEntity onSale(@PathVariable Long id,@RequestBody String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer status = jsonObject.getInteger("status");
        yxStoreCombinationService.onSale(id,status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("删除拼团")
    @ApiOperation(value = "删除拼团")
    @DeleteMapping(value = "/yxStoreCombination/{id}")
    @PreAuthorize("@el.check('admin','YXSTORECOMBINATION_ALL','YXSTORECOMBINATION_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        yxStoreCombinationService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
