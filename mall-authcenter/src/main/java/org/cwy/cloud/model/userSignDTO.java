package org.cwy.cloud.model;

import lombok.Data;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/24 14:47
 */

@Data
public class userSignDTO {
    private String userName;
    private String nickName;
    private String password;
    private String email;
    private Integer sex;
    private String phone;
}
