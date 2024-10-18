package org.cwy.cloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_table")
public class UserPO implements Serializable {
//    private static final long serialVersionUID = 1;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String nickName;
    private Integer sex;
    private String phone;
    private String email;
    private String password;
}
