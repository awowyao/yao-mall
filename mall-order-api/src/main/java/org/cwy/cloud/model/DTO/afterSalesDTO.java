package org.cwy.cloud.model.DTO;

import lombok.Data;
import org.cwy.cloud.common.orderStatueCode;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/27 17:30
 */
@Data
public class afterSalesDTO implements Serializable {
    private Integer filingType;
    private Integer omId;
    private Integer disposal;
    private String disposalReason;

    public boolean isDisposal() {
        if (orderStatueCode.ifFilingType(getFilingType())) {
            return getDisposal() == 1;
        }
        return false;
    }
}
