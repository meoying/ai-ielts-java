package com.meoying.ai.ielts.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = "/*")
@Component
@Order(value = 3)
public class LoginFilter implements Filter {
    private final Set<String> publicPaths = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        publicPaths.add("/users/signup");
        publicPaths.add("/users/login");
        publicPaths.add("/hello");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        /*if("OPTIONS".equalsIgnoreCase(req.getMethod()) || publicPaths.contains(req.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession sess = req.getSession(false);
        if(sess == null || sess.getAttribute("uid") == null) {
            // 没有登录
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setStatus(401);
            return;
        }*/
        chain.doFilter(req, response);
    }
}
