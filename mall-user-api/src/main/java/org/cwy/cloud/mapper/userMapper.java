package org.cwy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.modle.PO.userPO;

@Mapper
public interface userMapper extends BaseMapper<userPO> {

    int useCoupons(@Param("coupons") CouponsDTO coupons);
}
