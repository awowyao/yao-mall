package org.cwy.cloud.service;

import org.cwy.cloud.model.storeUserDTO;
import org.cwy.cloud.model.userSignDTO;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/24 14:51
 */
public interface userService {
    boolean userSign(userSignDTO userData);

    boolean storeUserSign(storeUserDTO userData);
}
