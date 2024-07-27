package org.cwy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cwy.cloud.PO.orderMsgPO;
import org.cwy.cloud.VO.orderVO;

@Mapper
public interface orderMsgMapper extends BaseMapper<orderMsgPO> {
}
