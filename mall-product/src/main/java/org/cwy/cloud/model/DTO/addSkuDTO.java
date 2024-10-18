package org.cwy.cloud.model.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/2 18:23
 */
@Data
public class addSkuDTO implements Serializable {
    Integer gid;
    String skuKey;
    List<String> skuValue;
}
