package filters;

import beans.ConnectorBean;
import beans.RulesBean;
import org.springframework.context.ApplicationContext;
import structures.AccountRecord;
import structures.Rule;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ti_g_programmist(no) on 21.05.2017.
 */
@WebFilter("/auth/")
public class AuthorizationFilter implements Filter {
    private ConnectorBean connectorBean;
    private RulesBean rulesBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext context = (ApplicationContext) filterConfig.getServletContext().getAttribute("applicationContext");
        connectorBean = (ConnectorBean) context.getBean("connectorBean");
        rulesBean = (RulesBean) context.getBean("rulesBean");
        rulesBean.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String login = (String) req.getParameter("login");
        String password = (String) req.getParameter("password");
        boolean rez = false;

        String status = null;
        AccountRecord record = connectorBean.getAccountsRecord(login, password);

        if (record != null) {
            status = record.getStatus();
        }

        for (Rule r : rulesBean.getRules()) {
            if (r.status.equals(status)) {
                ((HttpServletRequest) req).getSession().setAttribute("status", record.getStatus());
                ((HttpServletRequest) req).getSession().setAttribute("personalID", record.getId());
                ((HttpServletRequest) req).getSession().setAttribute("login", record.getLogin());
                ((HttpServletResponse) resp).sendRedirect(r.page);

                rez = true;
            }
        }
        if (!rez) {
            req.setAttribute("error", "Bad Login or Password!");
            req.getRequestDispatcher("../index.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
