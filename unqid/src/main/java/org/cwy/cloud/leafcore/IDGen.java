package org.cwy.cloud.leafcore;


import org.cwy.cloud.leafcore.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
