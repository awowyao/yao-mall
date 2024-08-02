package org.cwy.cloud.model.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 13:32
 */
@Data
public class editCouponsDTO extends CouponsDTO implements Serializable {
    private Integer id;
}
