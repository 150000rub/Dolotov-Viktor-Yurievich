package com.exigenservices.lectures;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ti_g_programmist(no) on 03.12.2016.
 */
@WebServlet("/send")
public class ProducerServlet extends HttpServlet {

    @Resource(name = "jms/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "Topic1")
    private Destination topic;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("msg");
        try {
            send(message);
        } catch (JMSException ex) {
            throw new ServletException(ex);
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    private void send(String text) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer;
            producer = session.createProducer(topic);
            producer.send(session.createTextMessage(text));
        } finally {
            connection.close();
        }
    }
}
