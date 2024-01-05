package org.zerock.jdbcex.controller;

import lombok.extern.log4j.Log4j2;
import org.zerock.jdbcex.dto.MemberDTO;
import org.zerock.jdbcex.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;


// '/login' 경로에서 GET 방식은 로그인 화면을 보여주고
// POST 방식으로는 실제 로그인 처리
@WebServlet(value = "/login")
@Log4j2
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        log.info("login get..........");

        //login.jsp는 POST 방식으로 '/login' 경로로 로그인에 필요한 아이디와 패스워드 데이터를 전송하도록 구성됨
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        log.info("login post........");

        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");

        //'auto'라는 이름으로 체크박스에서 전송되는 값 확인
        String auto = req.getParameter("auto");

        boolean rememberMe = auto != null && auto.equals("on");

        try{
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid,mpw);

            if(rememberMe){
                String uuid = UUID.randomUUID().toString();

                MemberService.INSTANCE.updateUuid(mid, uuid);
                memberDTO.setUuid(uuid);

                // 쿠키 생성 및 전송
                Cookie rememberCookie = new Cookie("remember-me", uuid);
                rememberCookie.setMaxAge(60*60*24*7);
                rememberCookie.setPath("/");

                resp.addCookie(rememberCookie);
            }

            HttpSession session = req.getSession();
            //정상적으로 로그인 된 경우 HttpSession을 이용해서 'loginInfo' 이름으로 객체를 저장
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/todo/list");

        } catch (Exception e){
            //예외 발생 시 '/login'으로 이동하는데 'result'라는 파라미터를 전달해서 문제가 발생했다는 사실 같이 전달
            resp.sendRedirect("/login?result=error");
        }
    }
}
