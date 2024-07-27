package org.cwy.cloud.modle.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("address_table")
public class addressPO {
    private Integer id;
    private Integer uid;
    private String province;
    private String city;
    private String district;
    private Integer version;
    private Date create_time;
    private Date updata_time;
}
