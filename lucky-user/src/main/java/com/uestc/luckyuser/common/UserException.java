package com.uestc.luckyuser.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jacob
 * @date 2022/4/18 14:17
 */
@Data
@AllArgsConstructor
public class UserException extends Exception {
    private ResultCode resultCode;
}
