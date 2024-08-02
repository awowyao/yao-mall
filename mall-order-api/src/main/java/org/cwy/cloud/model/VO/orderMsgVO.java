package org.cwy.cloud.model.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("order_msg_table")
public class orderMsgVO {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer goodsId;
    private String goods_title;
    private Double price;
    private Double couponsPrice;
    private Integer storeId;
    private Integer buyersId;
    private Integer addressId;
    private Integer couponsId;
    private Date create_time;
    private Date update_time;
}
