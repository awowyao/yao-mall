package org.cwy.cloud.modle.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/22 14:46
 */
@Data
@TableName("store_table")
public class storePO implements Serializable {
    Integer id;
    String name;
    String storeType;
    String storeLv;
    LocalDateTime create_time;
    LocalDateTime update_time;
}
