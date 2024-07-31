package com.openmall.system.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * @author wuxuan
 * @since 2024/7/31 15:47:26
 */
@Schema(description = "创建系统用户入参")
public record CreateSystemUserDTO(@Schema(description = "用户名") @NotBlank(message = "用户名不能为空") String username,
                                  @Schema(description = "密码") @NotBlank(message = "密码不能为空") String password,
                                  @Schema(description = "昵称") @NotBlank(message = "昵称不能为空") String nickname,
                                  @Schema(description = "邮箱") String email,
                                  @Schema(description = "头像") String avatar) {
}
