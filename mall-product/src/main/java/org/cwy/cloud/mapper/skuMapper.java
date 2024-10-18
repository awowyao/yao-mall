package org.cwy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cwy.cloud.model.PO.goodsSkuPO;
import org.cwy.cloud.model.PO.skuKeyPO;
import org.cwy.cloud.model.PO.skuValuePO;

import java.util.List;


/**
 * @author yao
 * @version 1.0
 * @description: 管理suk
 * @date 2024/8/30 15:57
 */
@Mapper
public interface skuMapper extends BaseMapper<goodsSkuPO> {



    List<skuKeyPO> getSkuKeys(@Param("gid") Integer gid);

    List<skuValuePO> getSkuValues(@Param("keyId") Integer skuKeyId);

    Integer addSkuKey(@Param("id") Integer id,
                      @Param("gid") Integer gid,
                      @Param("sid") Integer sid,
                      @Param("skuKey") String skuKey,
                      @Param("ranking") Integer ranking);

    Integer addSkuValue(@Param("id") int getId,
                        @Param("keyId") Integer keyId,
                        @Param("value") String value);

    Integer getSkuCount(@Param("gid") Integer gid);
}
