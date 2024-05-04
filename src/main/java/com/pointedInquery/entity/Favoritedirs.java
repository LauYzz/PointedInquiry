package com.pointedInquery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Favoritedirs对象", description = "")
public class Favoritedirs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String phone;

    private String expertId;


}
