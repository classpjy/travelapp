package com.pjy.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @TableName t_user
 */
@TableName(value = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户实体类")
public class User implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 邮箱号
     */
    @ApiModelProperty("邮箱号")
    private String email;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}