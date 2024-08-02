package org.cwy.cloud.model.DTO;

import lombok.Data;
import org.cwy.cloud.DTO.CommonPage;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 13:18
 */
@Data
public class couponsPageDTO extends CommonPage implements Serializable {
    private Date beginTime;
    private Date endTime;
    private Integer couponsType;
}
