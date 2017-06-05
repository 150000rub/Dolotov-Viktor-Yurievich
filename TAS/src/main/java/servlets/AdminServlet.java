package servlets;

import org.json.JSONArray;
import org.json.JSONObject;
import structures.AccountRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ti_g_programmist(no) on 25.05.2017.
 */
public class AdminServlet extends WorkServlet {

    private void serveQueryUpdate(JSONArray queryData) {
        ArrayList<AccountRecord> array = new ArrayList<AccountRecord>();
        for (Object i : queryData) {
            JSONObject x = (JSONObject) i;
            Integer x_id;
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
            array.add(new AccountRecord(
                    x_id,
                    x.getString("name"),
                    x.getString("password"),
                    x.getString("status")
            ));
        }
        connectorBean.updateAccounts(array);
    }

    @Override
    protected void serve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryType = "";
        JSONArray queryData = null;

        try {
            JSONObject currentQuery = query(req);
            if (currentQuery != null) {
                queryType = currentQuery.getString("type");
                queryData = currentQuery.getJSONArray("data");
            }

            if (queryType.equals("update")) {
                serveQueryUpdate(queryData);
            }
        } catch (Exception e) {
            System.out.println("Incorrect URL!");
        }
        HttpSession session = req.getSession();
        ArrayList<AccountRecord> data = connectorBean.getAccounts();
        session.setAttribute("users", data);
        req.getRequestDispatcher("/admin.jsp").forward(req, resp);
    }
}
