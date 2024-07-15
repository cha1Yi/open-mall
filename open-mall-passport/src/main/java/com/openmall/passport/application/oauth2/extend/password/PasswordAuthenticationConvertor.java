package com.openmall.passport.application.oauth2.extend.password;

import cn.hutool.core.util.StrUtil;
import com.openmall.passport.application.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 密码模式参数解析器
 * <p>
 * 解析请求参数中的用户名和密码，并构建相应的身份验证(Authentication)对象
 *
 * @author wuxuan
 * @see org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter
 * @since 2024/7/5 11:11:24
 */
public class PasswordAuthenticationConvertor implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!OAuth2ParameterNames.PASSWORD.equals(grantType)) {
            return null;
        }

        //客户端信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //参数提取验证
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
        //令牌申请访问范围验证（可选）
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);

        if (StrUtil.isNotBlank(scope) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE, OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
        Set<String> requestedScopes = null;

        if (StrUtil.isNotBlank(scope)) {
            requestedScopes = new HashSet<>(StrUtil.split(scope, " "));
        }

        //用户名验证
        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
        if (StrUtil.isBlank(username)) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME, OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        //密码必须
        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
        if (StrUtil.isBlank(password)) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.PASSWORD, OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
        // 附加参数(保存用户名/密码传递给 PasswordAuthenticationProvider 用于身份认证)
        Map<String, Object> additionalParameters = parameters.entrySet()
                .stream()
                .filter(e -> !e.getKey().equals(OAuth2ParameterNames.GRANT_TYPE) && !e.getKey()
                        .equals(OAuth2ParameterNames.SCOPE))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

        return new PasswordAuthenticationToken(authentication, requestedScopes, additionalParameters);
    }
}
