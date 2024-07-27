package org.cwy.cloud.common;

import org.springframework.util.Assert;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/26 17:44
 */
public class MyAssert extends Assert {
    public static void isZero(Integer i) {
        if (i==0) {
            throw new IllegalStateException("变更数量为0");
        }
    }
}
