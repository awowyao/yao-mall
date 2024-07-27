package org.cwy.cloud;

import jakarta.annotation.Resource;
import org.cwy.cloud.model.DTO.editGoodsDTO;
import org.cwy.cloud.model.DTO.goodsPageDTO;
import org.cwy.cloud.service.goodsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/26 16:12
 */
@SpringBootTest
public class storeTest {
    @Resource
    private goodsService goodsService;

    @Test
    public void getGoodsTest() {
        goodsPageDTO page = new goodsPageDTO();
        goodsService.getGoods(page);
    }

    @Test
    public void editGoodsTest() {
        editGoodsDTO goods = new editGoodsDTO();
        goods.setId(123456);
        goodsService.editGoods(goods);
    }
}
