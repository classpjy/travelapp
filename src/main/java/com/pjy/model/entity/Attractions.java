package com.pjy.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_attractions
 */
@TableName(value ="t_attractions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("景点实体类")
public class Attractions implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 景点名
     */
    @ApiModelProperty("景点名")
    private String name;

    /**
     * 景点图片
     */
    @ApiModelProperty("景点图片")
    private String picPath;

    /**
     * 景点旺季时间
     */
    @ApiModelProperty("景点旺季时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date hotTime;

    /**
     * 旺季票数
     */
    @ApiModelProperty("旺季票数")
    private Double hotTicket;

    /**
     * 淡季票数
     */
    @ApiModelProperty("淡季票数")
    private Double dimTicket;

    /**
     * 景点描述
     */
    @ApiModelProperty("景点描述")
    private String describes;

    /**
     * 连接省份表外键
     */
    @ApiModelProperty("连接省份表外键")
    private Integer provinceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}