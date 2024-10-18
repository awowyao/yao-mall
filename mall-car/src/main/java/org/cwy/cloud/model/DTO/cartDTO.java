package org.cwy.cloud.model.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/28 15:01
 */
@Data
public class cartDTO implements Serializable {
    public cartDTO () {
        this.suk = 0;
        this.gid = 0;
    }
    private Integer gid;
    private Integer suk;
    private Integer editType;
    private Integer nub;
}
