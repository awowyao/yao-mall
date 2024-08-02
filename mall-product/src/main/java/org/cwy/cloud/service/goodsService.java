package org.cwy.cloud.service;

import org.cwy.cloud.model.DTO.addGoodsDTO;
import org.cwy.cloud.model.DTO.editGoodsDTO;
import org.cwy.cloud.model.DTO.goodsDTO;
import org.cwy.cloud.model.DTO.goodsPageDTO;
import org.cwy.cloud.model.PO.couponsPO;
import org.cwy.cloud.model.PO.goodsPO;
import org.cwy.cloud.model.VO.goodsVO;

import java.util.List;
import java.util.Map;

public interface goodsService {
    List<goodsVO> GetGoodsAll(goodsPageDTO goodsPageDTO);

    Map<String, Object> GetGoodsById(Integer goosdId);

    int subtractInventory(Integer goodsId, Integer buyNub);

    couponsPO getCouponsById(Integer couponsId);

    List<goodsPO> getGoods(goodsPageDTO pageDTO);

    boolean addGoods(addGoodsDTO goods);

    void deleteGoods(Integer goodsId);

    void editGoods(editGoodsDTO goods);

    Map<String, Object> GetGoodsByStoreId(goodsPageDTO goodsPage);

    couponsPO checkCouponsById(Integer gid, Integer couponsId);
}
