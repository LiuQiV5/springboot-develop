package com.uni.common.springboot.config;

import com.uni.common.springboot.core.JsonMapper;
import com.uni.common.springboot.core.MessageUtil;
import com.uni.common.springboot.core.UniApiResponse;
import org.apache.http.HttpStatus;
import org.owasp.encoder.Encode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final JsonMapper jsonMapper = new JsonMapper();

    /**
     * 登陆状态下，权限不足进入此方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.SC_FORBIDDEN);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(Encode.forHtml(jsonMapper.writeValueAsString(UniApiResponse.createErrorResponse(MessageUtil.getText("error.AccessDenied")))));
        printWriter.flush();
    }
}
