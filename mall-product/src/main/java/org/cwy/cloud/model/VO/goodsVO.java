package org.cwy.cloud.model.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("goods_table")
public class goodsVO {
    @TableId(type = IdType.AUTO)
    Integer id;
    String title;
    String goods_type;
    String synopsis;
    String photo;
    double price;
    String Inventory;
    Integer store;

}
