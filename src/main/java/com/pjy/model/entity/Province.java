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
 * 
 * @TableName t_province
 */
@TableName(value ="t_province")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("省份实体类")

public class Province implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 省份名
     */
    @ApiModelProperty("省份名")
    private String name;

    /**
     * 景点标签
     */
    @ApiModelProperty("景点标签")
    private String tags;

    /**
     * 景点个数
     */
    @ApiModelProperty("景点个数")
    private Integer attractionsCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}