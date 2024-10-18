package org.cwy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.modle.PO.userPO;

import java.util.List;

@Mapper
public interface userMapper extends BaseMapper<userPO> {

    int useCoupons(@Param("coupons") CouponsDTO coupons);

    Integer insertCoupons(@Param("coupons") CouponsDTO coupons);

    List<Integer> getCouponsListByUid(@Param("uid") int uid);

    Integer concernStore(@Param("id") int Id,
                         @Param("uid") Integer userId,
                         @Param("sid") Integer storeId);

    List<Integer> storeGetFens(@Param("sid") Integer storeId);
}
