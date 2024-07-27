package org.cwy.cloud.model.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/27 17:12
 */
@Data
public class orderMsgDTO implements Serializable {
    private Integer omId;
    private Double price;
    private Double couponsPrice;
    private Integer couponsId;
}
