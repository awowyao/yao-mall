package org.cwy.cloud.model.PO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/30 15:56
 */
@Data
public class skuKeyPO implements Serializable {
    private Integer id;
    private Integer gid;
    private String sid;
    private String skuKey;
}
