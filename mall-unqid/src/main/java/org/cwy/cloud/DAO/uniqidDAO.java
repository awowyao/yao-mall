package org.cwy.cloud.DAO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("leaf_alloc")
public class uniqidDAO {
    private Integer bizTag;
    private Integer maxId;
    private Integer step;
    private String description;
    private Date updateTime;
}
