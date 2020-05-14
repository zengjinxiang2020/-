/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.rest;

import co.yixiang.logging.aop.log.Log;
import co.yixiang.modules.activity.domain.YxStoreCoupon;
import co.yixiang.modules.activity.service.YxStoreCouponService;
import co.yixiang.modules.activity.service.dto.YxStoreCouponQueryCriteria;
import co.yixiang.utils.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author hupeng
* @date 2019-11-09
*/
@Api(tags = "商城:优惠券管理")
@RestController
@RequestMapping("api")
public class StoreCouponController {

    private final YxStoreCouponService yxStoreCouponService;

    public StoreCouponController(YxStoreCouponService yxStoreCouponService) {
        this.yxStoreCouponService = yxStoreCouponService;
    }

    @Log("查询")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/yxStoreCoupon")
    @PreAuthorize("@el.check('admin','YXSTORECOUPON_ALL','YXSTORECOUPON_SELECT')")
    public ResponseEntity getYxStoreCoupons(YxStoreCouponQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(yxStoreCouponService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增")
    @ApiOperation(value = "新增")
    @PostMapping(value = "/yxStoreCoupon")
    @PreAuthorize("@el.check('admin','YXSTORECOUPON_ALL','YXSTORECOUPON_CREATE')")
    public ResponseEntity create(@Validated @RequestBody YxStoreCoupon resources){
        resources.setAddTime(OrderUtil.getSecondTimestampTwo());
        return new ResponseEntity(yxStoreCouponService.save(resources),HttpStatus.CREATED);
    }

    @Log("修改")
    @ApiOperation(value = "修改")
    @PutMapping(value = "/yxStoreCoupon")
    @PreAuthorize("@el.check('admin','YXSTORECOUPON_ALL','YXSTORECOUPON_EDIT')")
    public ResponseEntity update(@Validated @RequestBody YxStoreCoupon resources){
        yxStoreCouponService.saveOrUpdate(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/yxStoreCoupon/{id}")
    @PreAuthorize("@el.check('admin','YXSTORECOUPON_ALL','YXSTORECOUPON_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        YxStoreCoupon resources = new YxStoreCoupon();
        resources.setId(id);
        resources.setIsDel(1);
        yxStoreCouponService.saveOrUpdate(resources);
        return new ResponseEntity(HttpStatus.OK);
    }
}
