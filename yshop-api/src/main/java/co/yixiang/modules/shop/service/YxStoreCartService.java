/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.shop.service;

import co.yixiang.common.service.BaseService;
import co.yixiang.modules.shop.entity.YxStoreCart;
import co.yixiang.modules.shop.web.vo.YxStoreCartQueryVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author hupeng
 * @since 2019-10-25
 */
public interface YxStoreCartService extends BaseService<YxStoreCart> {

    void removeUserCart(int uid, List<String> ids);

    void changeUserCartNum(int cartId,int cartNum,int uid);

    Map<String,Object> getUserProductCartList(int uid,String cartIds,int status);

    int getUserCartNum(int uid,String type,int numType);

    int addCart(int uid,int productId,int cartNum, String productAttrUnique,
                String type,int isNew,int combinationId,int seckillId,int bargainId);

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    YxStoreCartQueryVo getYxStoreCartById(Serializable id);


}
