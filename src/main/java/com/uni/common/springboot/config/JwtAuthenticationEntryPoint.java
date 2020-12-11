package com.uni.common.springboot.config;

import com.uni.common.springboot.core.JsonMapper;
import com.uni.common.springboot.core.MessageUtil;
import com.uni.common.springboot.core.UniApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JsonMapper jsonMapper = new JsonMapper();

    /**
     * token校验不通过会进入此方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        final String requestHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(requestHeader)) {
            printWriter.write(Encode.forHtml(jsonMapper.writeValueAsString(UniApiResponse.createErrorResponse(MessageUtil.getText("error.TokenNotFound")))));
        } else {
            printWriter.write(Encode.forHtml(jsonMapper.writeValueAsString(UniApiResponse.createErrorResponse(MessageUtil.getText("error.TokenExpired")))));
        }
        printWriter.flush();
    }
}
