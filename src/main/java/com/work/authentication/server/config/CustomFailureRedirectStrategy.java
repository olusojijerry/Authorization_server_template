package com.work.authentication.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.work.authentication.server.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.DefaultRedirectStrategy;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class CustomFailureRedirectStrategy extends DefaultRedirectStrategy {
    boolean forwardToDestination = false;
    boolean allowSessionCreation = true;

    private boolean wantsJson(HttpServletRequest request){
        String acceptHeader = request.getHeader("accept");

        if(acceptHeader != null && acceptHeader.trim().contains(Constant.JSON_APPLICATION_TYPE)){
            return true;
        }

        return false;
    }

    private void handleJsonResponse(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        int status = response.getStatus();

        log.info("Response status ======" + status);

        //create response json object
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode res = objectMapper.createObjectNode();
        response.getStatus();
        res.put("success", false);

        AuthenticationException authenticationException = getException(request);
        res.put("message", authenticationException.getMessage());
        res.put("redirectUrl", url);

        response.setContentType(Constant.JSON_APPLICATION_TYPE);
        response.getWriter().write(res.toString());
    }

    protected final AuthenticationException getException(HttpServletRequest request){
        if(this.forwardToDestination){
            return (AuthenticationException) request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        }else{
            HttpSession session = request.getSession(false);
            if (session != null || this.allowSessionCreation){
                return (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            }
        }

        return null;
    }
}
