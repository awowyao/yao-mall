package org.cwy.cloud.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/24 15:55
 */
@Data
public class storeUserDTO implements Serializable {
    private String userId;
    private String password;
    private String userName;
    private Integer storeType;
    private Integer storeLv;
}
