package com.exigenservices.lectures.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Ti_g_programmist(no) on 26.11.2016.
 */

public class RuletkaServlet extends HttpServlet {

    private final static String ACTION_ADD = "add";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (ACTION_ADD.equals(action)) {
            String number = req.getParameter("number");
            if (isNumber(number)) {
                Random r = new Random();
                int n = Integer.parseInt(number);
                if (n == r.nextInt(99) + 1) {
                    redirectToWin(req,resp);
                } else {
                    redirectToLose(req, resp);
                }
            } else {
                req.setAttribute("error", "Incorrect number! Enter again.");
                req.getRequestDispatcher("/start.jsp").forward(req, resp);
            }
        }
    }

    private void redirectToWin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/win.jsp").forward(req, resp);
    }

    private void redirectToLose(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/lose.jsp").forward(req, resp);
    }

    private boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
