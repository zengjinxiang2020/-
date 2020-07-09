/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.service;

import co.yixiang.common.service.BaseService;
import co.yixiang.modules.activity.domain.YxStoreCouponUser;
import co.yixiang.modules.activity.service.dto.YxStoreCouponUserDto;
import co.yixiang.modules.activity.service.dto.YxStoreCouponUserQueryCriteria;
import co.yixiang.modules.activity.vo.StoreCouponUserVo;
import co.yixiang.modules.activity.vo.YxStoreCouponUserQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author hupeng
* @date 2020-05-13
*/
public interface YxStoreCouponUserService  extends BaseService<YxStoreCouponUser>{

    /**
     * 获取当前用户优惠券数量
     * @param uid uid
     * @return int
     */
    int getUserValidCouponCount(Long uid);

    void useCoupon(int id);

    /**
     * 获取用户优惠券
     * @param id 优惠券id
     * @param uid 用户id
     * @return YxStoreCouponUser
     */
    YxStoreCouponUser getCoupon(Integer id,Long uid);


    /**
     * 获取满足条件的可用优惠券
     * @param cartIds 购物车ids
     * @return list
     */
    List<StoreCouponUserVo> beUsableCouponList(Long uid,String cartIds);

    /**
     * 获取下单时候满足的优惠券
     * @param uid uid
     * @param price 总价格
     * @param productIds list
     * @return list
     */
    List<StoreCouponUserVo> getUsableCouponList(Long uid, double price, List<String> productIds);



    /**
     * 获取用户优惠券
     * @param uid uid
     * @return list
     */
    List<YxStoreCouponUserQueryVo> getUserCoupon(Long uid);

    /**
     * 添加优惠券记录
     * @param uid 用户id
     * @param cid 优惠券id
     */
    void addUserCoupon(Long uid,Integer cid);

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreCouponUserQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreCouponUserDto>
    */
    List<YxStoreCouponUser> queryAll(YxStoreCouponUserQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreCouponUserDto> all, HttpServletResponse response) throws IOException;
}
