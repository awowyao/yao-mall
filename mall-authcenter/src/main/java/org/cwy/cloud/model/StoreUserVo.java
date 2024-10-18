package org.cwy.cloud.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 14:59
 */
@Data
@TableName("store_table")
public class StoreUserVo implements Serializable {
    private Long id;
    private String userId;
    private String password;
    private String userName;
    private Integer storeType;
    private Integer storeLv;
}
