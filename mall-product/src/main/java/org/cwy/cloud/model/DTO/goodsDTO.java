package org.cwy.cloud.model.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/26 17:24
 */

@Data
public class goodsDTO implements Serializable {
    String title;
    String goodsType;
    String synopsis;
    String photo;
    Double price;
    Integer Inventory;
    public goodsDTO() {
        setPrice(0.0);
    }
}
