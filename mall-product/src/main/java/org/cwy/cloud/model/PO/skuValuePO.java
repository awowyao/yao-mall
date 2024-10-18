package org.cwy.cloud.model.PO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/2 15:19
 */
@Data
public class skuValuePO implements Serializable {
    Integer id;
    Integer keyId;
    String skuValue;
}
