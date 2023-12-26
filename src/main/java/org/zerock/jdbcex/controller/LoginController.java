package org.zerock.jdbcex.controller;

import lombok.extern.java.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


// '/login' 경로에서 GET 방식은 로그인 화면을 보여주고
// POST 방식으로는 실제 로그인 처리
@WebServlet(value = "/login")
@Log
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

        String str = mid+mpw;

        HttpSession session = req.getSession();

        session.setAttribute("loginInfo", str);

        resp.sendRedirect("/todo/list");
    }
}
