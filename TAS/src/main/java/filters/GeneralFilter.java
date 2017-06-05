package filters;

import beans.RulesBean;
import org.springframework.context.ApplicationContext;
import structures.Rule;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Ti_g_programmist(no) on 21.05.2017.
 */
@WebFilter("/")
public class GeneralFilter implements Filter {
    private RulesBean rulesBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext context = (ApplicationContext) filterConfig.getServletContext().getAttribute("applicationContext");
        rulesBean = (RulesBean) context.getBean("rulesBean");
        rulesBean.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) req).getRequestURI();
        if (path.startsWith("/auth")) {
            chain.doFilter(req, resp);
            return;
        }
        if (path.startsWith("/css")) {
            chain.doFilter(req, resp);
            return;
        }
        if (path.startsWith("/js")) {
            chain.doFilter(req, resp);
            return;
        }
        if (path.startsWith("/logout")) {
            ((HttpServletRequest) req).getSession().setAttribute("status",null);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        String status = (String) ((HttpServletRequest) req).getSession().getAttribute("status");
        if (status == null) {
            if (path.equals("/")) {
                chain.doFilter(req, resp);
                return;
            }
            req.setAttribute("error", "You aren't authorized!");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            for (Rule r : rulesBean.getRules()) {
                if (path.startsWith(r.page)) {
                    if (r.status.equals(status)) {
                        chain.doFilter(req, resp);
                        return;
                    }
                }
            }
            switch (status) {
                case ("Admin"):
                    req.getRequestDispatcher("/AdminServlet").forward(req, resp);
                    break;
                case ("Manager"):
                    req.getRequestDispatcher("/ManagerServlet").forward(req, resp);
                    break;
                case ("Worker"):
                    req.getRequestDispatcher("/WorkerServlet").forward(req, resp);
                    break;
            }
        }
    }

    @Override
    public void destroy() {
    }


}