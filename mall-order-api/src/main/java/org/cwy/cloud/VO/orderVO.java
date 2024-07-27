package org.cwy.cloud.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("order_table")
@Data
public class orderVO {
    @TableId(value = "oid", type = IdType.ASSIGN_UUID)
    private Integer oid;
    private Integer order_msgid;
    private Integer buy_user_id;
    private Integer buy_nub;
    private Integer buy_goods_id;
    private Integer state;
    private Date create_time;
    private Date update_time;

}
