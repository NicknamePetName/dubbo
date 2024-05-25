package com.youkeda.dewu.util;

import java.util.UUID;

/**
 * @Author 亦忻 songchuanming
 * @DATE 2024/5/25.
 */
public class UUIDUtils {
    public static final String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
