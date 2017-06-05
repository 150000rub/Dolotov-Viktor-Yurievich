package servlets;

import org.json.JSONObject;
import structures.TaskRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ti_g_programmist(no) on 25.05.2017.
 */
public class WorkerServlet extends WorkServlet {

    protected void serveCurrentTaskUpdate(JSONObject queryData, Integer id, String login) {
        TaskRecord record = new TaskRecord(
                queryData.getInt("id"),
                "",
                "",
                "",
                queryData.getString("status"),
                queryData.getString("comment")
        );
        connectorBean.updateTaskForWorker(record);
    }

    @Override
    protected void serve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer id = (Integer) req.getSession().getAttribute("personalID");
        String login = (String) req.getSession().getAttribute("login");

        String queryType = "";
        JSONObject queryData = null;

        try {
            JSONObject currentQuery = query(req);
            if (currentQuery != null) {
                queryType = currentQuery.getString("type");
                queryData = currentQuery.getJSONObject("data");
            }
            if (queryType.equals("update")) {
                serveCurrentTaskUpdate(queryData, id, login);
            }
        } catch (Exception e) {
            System.out.println("Incorrect URL!");
        }

        TaskRecord currentTask = connectorBean.getCurrentTaskForWorker(login);
        session.setAttribute("currenttask", currentTask);

        ArrayList<TaskRecord> oldTasks = connectorBean.getTasksForWorker(login);
        session.setAttribute("oldtasks", oldTasks);

        req.getRequestDispatcher("/worker.jsp").forward(req, resp);
    }
}
