package com.tpp.threat_perception_platform.filter;

import com.alibaba.fastjson.JSON;
import com.tpp.threat_perception_platform.pojo.LoginUser;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.utils.WebUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class PermissionCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get the current path
        String requestPath = request.getRequestURI();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            for (var path:loginUser.getAccessiblePathList()) {
                if (requestPath.startsWith(path)) {
                    // If has permission, pass to next filter
                    filterChain.doFilter(request, response);
                    return;
                }
            }
//            filterChain.doFilter(request, response);
//            return;
            // If logged in, but without permission, return 403 Forbidden
            WebUtils.renderString(response, JSON.toJSONString(new ResponseResult<>(403, "没有访问权限")));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);
        return;
    }
}
