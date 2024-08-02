package org.cwy.cloud.modle.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/1 16:16
 */
@Data
public class addressDTO implements Serializable {
    private Integer id;
    private String province;
    private String city;
    private String district;
    private String detailed;
    private Integer version;
}
