package org.cwy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cwy.cloud.model.PO.goodsSkuPO;


import java.util.List;


/**
 * @author yao
 * @version 1.0
 * @description: 管理suk
 * @date 2024/8/30 15:57
 */
@Mapper
public interface skuMapper extends BaseMapper<goodsSkuPO> {

}
