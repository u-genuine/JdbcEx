package org.zerock.jdbcex.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


// 브라우저에서 '/todo/...'로 시작하는 모든 경로에 대해 필터링 시도
@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

        log.info("Login check filter....");

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        HttpSession session = req.getSession();

        //HttpSession을 구해서 'loginInfo' 이름의 값이 존재하지 않는다면 '/login'으로 이동
        if(session.getAttribute("loginInfo") == null){

            resp.sendRedirect("/login");

            return;
        }

        chain.doFilter(request, response);
    }
}
