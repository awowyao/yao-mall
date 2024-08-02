package org.cwy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cwy.cloud.model.PO.couponsPO;

import java.util.List;

@Mapper
public interface couponsMapper extends BaseMapper<couponsPO> {
    Integer addGoodsAndCoupons(@Param("id") Integer id,
                               @Param("goodsId") Integer goodsId,
                               @Param("couponsId") Integer couponsId);

    List<couponsPO> getCouponsByGoodsId(@Param("goodsId") Integer goodsId);

    Integer checkCouponsById(@Param("gid") Integer gid,@Param("cid")  Integer couponsId);
}
