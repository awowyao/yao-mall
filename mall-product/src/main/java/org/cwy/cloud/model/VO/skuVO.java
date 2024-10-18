package org.cwy.cloud.model.VO;

import lombok.Data;
import org.cwy.cloud.model.PO.skuValuePO;

import java.io.Serializable;
import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/2 15:37
 */

@Data
public class skuVO implements Serializable {
    Integer skuKeyId;
    String skuKey;
    List<skuValuePO> skuValue;
}
