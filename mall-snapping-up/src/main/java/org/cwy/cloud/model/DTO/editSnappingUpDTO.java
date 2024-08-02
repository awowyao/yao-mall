package org.cwy.cloud.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 15:29
 */
@Data
public class editSnappingUpDTO implements Serializable {
    private Integer id;
    private Integer kind;
    private Integer stockNumber;
    private Integer limit;
    private Double price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
