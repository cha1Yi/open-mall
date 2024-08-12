package com.openmall.system.interfaces.controller;

import com.openmall.common.vo.Result;
import com.openmall.system.application.dto.CreateSystemUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuxuan
 * @since 2024/7/31 15:07:25
 */
@Tag(name = "系统用户", description = "系统用户相关的接口")
@RestController
@RequestMapping("/user")
public class SystemUserController {

    @Operation(summary = "创建系统用户")
    @PostMapping("/create")
    public Result<String> create(@RequestBody @Parameter(description = "创建系统用户") CreateSystemUserDTO dto) {
        return Result.success("创建成功");
    }


    @GetMapping("/get")
//    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("hello world");
    }

    @GetMapping("/foo")
    public Result<String> get1(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return Result.success(principal.getAttribute("sub") + " is the subject");
    }

    @GetMapping("/foo2")
    public Result<String> get2(Authentication principal) {
        return Result.success(principal.getName() + " is the subject");
    }

}
