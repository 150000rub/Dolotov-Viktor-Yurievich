package com.exigenservices.lectures.tags;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Random;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;

/**
 * Created by Ti_g_programmist(no) on 01.12.2016.
 */
public class RuletkaTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        PageContext con = (PageContext) getJspContext();
        String number = (String) con.findAttribute("number");
        if (isNumber(number)) {
            Random r = new Random();
            int n = Integer.parseInt(number);
            try {
                if (n == r.nextInt(99) + 1) {
                    con.forward("/win.jsp");
                } else {
                    con.forward("/lose.jsp");
                }
            } catch (ServletException ignored) {
            }
        } else {
            con.getOut().print("<div style=\"color:blue;\"> Incorrect number! Enter again.</div>");
        }
    }
}
