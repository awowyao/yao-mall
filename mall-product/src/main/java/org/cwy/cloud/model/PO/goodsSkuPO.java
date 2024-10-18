package org.cwy.cloud.model.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/2 15:20
 */
@TableName("goods_sku_table")
@Data
public class goodsSkuPO implements Serializable {
    private Integer id;
    private Integer gid;
    private Long keyO;
    private Long valueO;
    private Long keyT;
    private Long valueT;
    private Long keyS;
    private Long stock;
    private Long valueS;
    private Double price;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
