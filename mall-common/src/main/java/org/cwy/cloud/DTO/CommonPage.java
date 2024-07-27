package org.cwy.cloud.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/27 16:25
 */
@Data
public class CommonPage implements Serializable{
    private Integer page;
    private Integer pageSize;
    public CommonPage() {
        this.page = 1;
        this.pageSize = 20;
    }
}
