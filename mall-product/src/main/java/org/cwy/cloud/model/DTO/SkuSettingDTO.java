package org.cwy.cloud.model.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/3 14:30
 */
@Data
public class SkuSettingDTO implements Serializable {
    long skuKeyOId;
    long skuValueOId;
    long skuKeyTId;
    long skuValueTId;
    long skuKeySId;
    long skuValueSId;

    long stock;
    double price;

//    public static List<SkuSettingDTO> skuSettingDTOS() {
//        return new ArrayList<SkuSettingDTO>();
//    }

    @Data
    public static class skuSettingDTOS {
        Integer gid;
        List<SkuSettingDTO> skuPriceList;
    }
}
