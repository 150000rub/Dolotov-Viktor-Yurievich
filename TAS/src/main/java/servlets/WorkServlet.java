package servlets;

import beans.ConnectorBean;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ti_g_programmist(no) on 21.05.2017.
 */
abstract public class WorkServlet extends HttpServlet {
    protected ConnectorBean connectorBean;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ApplicationContext context = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
        connectorBean = (ConnectorBean) context.getBean("connectorBean");
    }

    abstract protected void serve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    protected JSONObject query(HttpServletRequest req) {
        String queryString = req.getParameter("query");
        if (queryString != null) {
            return new JSONObject(queryString);
        } else {
            return null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serve(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serve(req, resp);
    }
}