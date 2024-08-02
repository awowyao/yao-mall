package org.cwy.cloud.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 13:34
 */
@Data
public class CouponsDTO implements Serializable {
    private String title;
    private Integer max;
    private Integer subtract;
    private Integer kind;
    private Integer number;
    private List<Integer> goodsList;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
