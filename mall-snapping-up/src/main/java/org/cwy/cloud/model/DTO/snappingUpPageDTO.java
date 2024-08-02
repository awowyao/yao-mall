package org.cwy.cloud.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.cwy.cloud.DTO.CommonPage;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 15:21
 */
@Data
public class snappingUpPageDTO extends CommonPage implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
