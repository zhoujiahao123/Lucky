package com.uestc.luckyuser.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jacob
 * @date 2022/4/12 20:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private long id;
    @ApiModelProperty(value = "用户的姓名")
    private String name;
    private Date createTime;
    private Date updateTime;
    private Integer gender;
    private String password;
    private String mobilePhoneNumber;
    private String role;
}
