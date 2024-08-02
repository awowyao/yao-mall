package org.cwy.cloud.model.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("order_msg_table")
public class orderMsgPO implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer omId;
    @TableField(exist=false)
    private Integer oId;
    private Integer goodsId;
    private String goodsTitle;
    private Double price;
    private Double couponsPrice;
    private Integer storeId;
    private Integer buyersId;
    private Integer couponsId;
    private Integer addressId;
    private Integer orderStatue;
    private Integer returnableId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    public orderMsgPO(){

    }
}
