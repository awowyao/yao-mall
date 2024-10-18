package org.cwy.cloud.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/2 17:03
 */
@Data
public class pageVO<T> implements Serializable {
    private long total;
    private long page;
    private long pageSize;
    private long totalPage;
    private List<T> data;

//    public pageVO(IPage<T> pageData) {
//
//    }
}
