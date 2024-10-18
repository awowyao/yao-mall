package org.cwy.cloud.model.VO;

import lombok.Data;

import java.util.Date;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/27 16:29
 */
@Data
public class carVO {
    private Integer gid;
    private String title;
    private Double price;
    private String cover;
    private Integer nub;
    private Integer suk;
    private String sukTitle;
    private Double createTime;
}
