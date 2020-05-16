/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.mp.controller;

import cn.hutool.core.util.StrUtil;
import co.yixiang.dozer.service.IGenerator;
import co.yixiang.exception.BadRequestException;
import co.yixiang.mp.domain.YxWechatTemplate;
import co.yixiang.mp.service.YxWechatTemplateService;
import co.yixiang.mp.service.dto.YxWechatTemplateDto;
import co.yixiang.mp.service.dto.YxWechatTemplateQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
* @author hupeng
* @date 2019-12-10
*/
@Api(tags = "商城:微信模板管理")
@RestController
@RequestMapping("/api/yxWechatTemplate")
@AllArgsConstructor
public class WechatTemplateController {


    private final YxWechatTemplateService yxWechatTemplateService;
    private final IGenerator generator;


    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','yxWechatTemplate:list')")
    public void download(HttpServletResponse response, YxWechatTemplateQueryCriteria criteria) throws IOException {
        yxWechatTemplateService.download(generator.convert(yxWechatTemplateService.queryAll(criteria), YxWechatTemplateDto.class), response);
    }

    @GetMapping
    @ApiOperation("查询微信模板消息")
    @PreAuthorize("@el.check('admin','yxWechatTemplate:list')")
    public ResponseEntity<Object> getYxWechatTemplates(YxWechatTemplateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(yxWechatTemplateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("新增微信模板消息")
    @PreAuthorize("@el.check('admin','yxWechatTemplate:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody YxWechatTemplate resources){
        return new ResponseEntity<>(yxWechatTemplateService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation("修改微信模板消息")
    @PreAuthorize("@el.check('admin','yxWechatTemplate:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody YxWechatTemplate resources){
        yxWechatTemplateService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除微信模板消息")
    @PreAuthorize("@el.check('admin','yxWechatTemplate:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Integer[] ids) {
        Arrays.asList(ids).forEach(id->{
            yxWechatTemplateService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
