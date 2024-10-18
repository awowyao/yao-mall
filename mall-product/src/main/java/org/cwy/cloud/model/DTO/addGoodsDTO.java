package org.cwy.cloud.model.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: 接收添加的商品
 * @date 2024/7/26 15:54
 */

@Data
public class addGoodsDTO implements Serializable {
    String title;
    String goodsType;
    String synopsis;
    String photo;
    Double price;
    Integer skuType;
    Integer Inventory;

}
