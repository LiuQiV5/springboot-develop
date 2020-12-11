package com.uni.common.springboot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel(value = "用户学校DTO")
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class UserSchoolDTO {

    @ApiModelProperty(value = "用户账号")
    @NotBlank(message = "用户账号不能为空")
    private String userName;

    @ApiModelProperty(value = "学校名")
    @NotBlank(message = "学校名不能为空")
    private String schoolName;

    @ApiModelProperty(value = "学校guid")
    @NotBlank(message = "学校guid不能为空")
    private String schoolGuid;

}
