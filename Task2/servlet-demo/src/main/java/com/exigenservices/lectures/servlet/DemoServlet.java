package com.exigenservices.lectures.servlet;

import com.sun.webkit.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Ti_g_programmist(no) on 25.11.2016.
 */
public class DemoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("current time is "+ new Date(System.currentTimeMillis()));
    }
}
