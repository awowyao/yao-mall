package org.cwy.cloud.model.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: 获取商品分页
 * @date 2024/7/26 16:03
 */
@Data
public class goodsPageDTO implements Serializable {
    private Integer storeId;
    private Integer pageSize;
    private Integer page;
    public goodsPageDTO() {
        setPage(1);
        setPageSize(20);
    }
}
