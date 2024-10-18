package org.cwy.cloud.model.VO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class goodsVO {
    Integer id;
    String title;
    String goodsType;
    String synopsis;
    String photo;
    double price;
    Integer Inventory;
    Integer storeId;
    Integer goodsStatue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
