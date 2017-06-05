package com.exigenservices.lectures.servlets;

import api.messageRemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by Ti_g_programmist(no) on 02.12.2016.
 */
public class messageServlet extends HttpServlet {
    @EJB
    private messageRemote message;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mess = req.getParameter("text");
        if ((mess!=null)&&(!mess.equals(""))) {
            LinkedHashMap<String,Integer> symb = message.calcSymb(mess);
            req.getSession().setAttribute("table",symb);
            req.getRequestDispatcher("/table.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Your message is empty!");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
