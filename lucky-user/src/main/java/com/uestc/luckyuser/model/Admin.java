package com.uestc.luckyuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jacob
 * @date 2022/4/23 10:51
 */
@Data
@AllArgsConstructor
public class Admin {
    private String name;
    private String password;
}
