package org.cwy.cloud.model.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class snappingUpDTO implements Serializable {
    public snappingUpDTO() {

    }
    private Integer gid;
    private Integer kind;
    private Integer stockNumber;
    private Integer limit;
    private Double price;
    private Date beginTime;
    private Date endTime;
}
