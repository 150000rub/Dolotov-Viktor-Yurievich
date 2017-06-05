package servlets;

import org.json.JSONArray;
import org.json.JSONObject;
import structures.AccountRecord;
import structures.TaskRecord;
import structures.WorkerTaskRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ti_g_programmist(no) on 25.05.2017.
 */
public class ManagerServlet extends WorkServlet {
    protected void serveTasksUpdate(JSONArray queryData, Integer id, String name) {
        ArrayList<TaskRecord> array = new ArrayList<TaskRecord>();
        for (Object i : queryData) {
            JSONObject x = (JSONObject) i;
            Integer x_id = null;
            if (x.has("id")) {
                String x_id_s = x.getString("id");
                if (x_id_s.equals("")) {
                    x_id = 0;
                } else {
                    x_id = x.getInt("id");
                }
            } else {
                x_id = 0;
            }
            array.add(new TaskRecord(
                    x_id,
                    name,
                    x.getString("idWorker"),
                    x.getString("name"),
                    x.getString("status"),
                    x.getString("comment")
            ));
        }
        connectorBean.updateTasks(array);
    }

    protected void serveTasks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer id = (Integer) req.getSession().getAttribute("personalID");
        String login = (String) req.getSession().getAttribute("login");

        String queryType = "";
        JSONArray queryData = null;
        JSONObject currentQuery = null;

        try {
            currentQuery = query(req);
            if (currentQuery != null) {
                queryType = currentQuery.getString("type");
                queryData = currentQuery.getJSONArray("data");
            }

            if (queryType.equals("update")) {
                serveTasksUpdate(queryData, id, login);
            }
        } catch (Exception e) {
            System.out.println("Incorrect URL!");
        }

        ArrayList<TaskRecord> tasks = connectorBean.getTasksForManager(login);
        session.setAttribute("tasks", tasks);
        ArrayList<AccountRecord> freeworkers = connectorBean.getFreeWorkers();
        session.setAttribute("freeworkers", freeworkers);
        ArrayList<AccountRecord> ourfreeworkers = connectorBean.getFreeWorkersOfManager(login);
        session.setAttribute("ourfreeworkers", ourfreeworkers);

        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }

    protected void serveWorkers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = (String) req.getSession().getAttribute("login");

        ArrayList<WorkerTaskRecord> workers = connectorBean.getWorkerTasks(login);

        session.setAttribute("workers", workers);

        req.getRequestDispatcher("/workers.jsp").forward(req, resp);
    }

    @Override
    protected void serve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String targetpage = req.getParameter("targetpage");
        if (targetpage != null) {
            switch (targetpage) {
                case "/tasks.jsp":
                    serveTasks(req, resp);
                    break;
                case "/workers.jsp":
                    serveWorkers(req, resp);
                    break;
                default:
                    req.getRequestDispatcher("/manager.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/manager.jsp").forward(req, resp);
        }
    }
}
