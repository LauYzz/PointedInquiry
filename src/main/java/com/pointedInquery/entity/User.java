package com.pointedInquery.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String name;

    private String password;

    private String school;

    @ApiModelProperty(value = "是否有专家身份，0表示不是专家，1表示是专家")
    @TableField("is_expert")
    private Integer isExpert;
    
}
