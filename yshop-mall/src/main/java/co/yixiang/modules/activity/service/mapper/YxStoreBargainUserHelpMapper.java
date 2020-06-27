/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.service.mapper;


import co.yixiang.common.mapper.CoreMapper;
import co.yixiang.modules.activity.domain.YxStoreBargainUserHelp;
import co.yixiang.modules.activity.vo.YxStoreBargainUserHelpQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 * 砍价用户帮助表 Mapper 接口
 * </p>
 *
 * @author hupeng
 * @since 2019-12-21
 */
@Repository
public interface YxStoreBargainUserHelpMapper extends CoreMapper<YxStoreBargainUserHelp> {

}
