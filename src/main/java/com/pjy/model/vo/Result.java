package com.pjy.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户接口传输对象")
public class Result {
    @ApiModelProperty("是否成功")
    private boolean status;
    @ApiModelProperty("信息")
    private String msg;
    @ApiModelProperty("用户令牌")
    private String token;
    @ApiModelProperty("用户名")
    private String username;

    public Result(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
