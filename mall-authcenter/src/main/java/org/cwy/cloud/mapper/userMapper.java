package org.cwy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cwy.cloud.model.UserPO;

@Mapper
public interface userMapper extends BaseMapper<UserPO> {
}
