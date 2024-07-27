package org.cwy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cwy.cloud.PO.orderPO;

@Mapper
public interface orderMapper extends BaseMapper<orderPO> {
}
