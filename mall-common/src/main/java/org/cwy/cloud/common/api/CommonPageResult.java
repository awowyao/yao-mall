package org.cwy.cloud.common.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import org.cwy.cloud.model.pageVO;

import java.util.List;


/**
 * @author yao
 * @version 1.0
 * @description: 分页放回信息
 * @date 2024/9/2 15:40
 */
@Data
public class CommonPageResult<T> {
    private long code;
    private String message;
    private List<T> data;
    private long page;
    private long pageSize;
    private long total;
    private long totalPage;

    public CommonPageResult(pageVO<T> pageData, long code, String message) {
        if (pageData != null) {
            this.page = pageData.getPage();
            this.pageSize = pageData.getPageSize();
            this.totalPage = pageData.getTotalPage();
            this.total = pageData.getTotal();
            this.data = pageData.getData();
        }
        this.code = code;
        this.message = message;
    }

    public CommonPageResult<T> success(pageVO<T> pageData){
        return new CommonPageResult<T>(pageData, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    public CommonPageResult<T> failed(){
        return new CommonPageResult<T>(null, ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }
}
