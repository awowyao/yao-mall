package org.cwy.cloud.common;

import java.util.Objects;

/**
 * @author yao
 * @version 1.0
 * @description: 订单状态代码
 * @date 2024/7/27 17:02
 */
public class orderStatueCode {
    public static final Integer PAY = 1;
    public static final Integer NOT_PAY = 2;
    public static final Integer REFUND = 3;
    public static final Integer RETURNABLE = 4;
    public static final Integer FILED_REFUND = 5;
    public static final Integer FILED_RETURNABLE = 6;
    public static final Integer END = 0;

    public static boolean ifFilingType(Integer filing) {
        if (Objects.equals(filing, FILED_REFUND) || filing.equals(FILED_RETURNABLE)) {
            return true;
        }else {
            return false;
        }
    }
}
