package org.cwy.cloud.modle.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_table")
@Data
public class userPO {
    private Integer id;
    private String name;
    private String nickName;
    private String password;
    private Integer sex;
    private String email;
    private String phone;
    private String cover;
    private Integer member;
    private Integer state;
    private Data createTime;
    private Data updateTime;
}
