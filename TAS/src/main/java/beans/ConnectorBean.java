package beans;

import org.apache.log4j.Logger;
import structures.AccountRecord;
import structures.TaskRecord;
import structures.WorkerTaskRecord;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Ti_g_programmist(no) on 21.05.2017.
 */
public class ConnectorBean {
    InitialContext ictx;
    DataSource ds;
    Connection con;
    Statement stmt;
    boolean initialized = false;
    private static final Logger log = Logger.getLogger(ConnectorBean.class);

    public void init() {
        if(initialized) {
            return;
        }
        log.info("Bean initialization start.");
        try {
            ictx = new InitialContext();
            ds = (DataSource) ictx.lookup("java:comp/env/jdbc/task");
        } catch (NamingException e) {
            log.warn("Failed to locate datasource.",e);
            e.printStackTrace();
            return;
        }
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
        } catch (SQLException e) {
            log.warn("Failed to establish connection.",e);
            e.printStackTrace();
            return;
        }
        initialized = true;
    }

    public void destroy() {
        log.debug("Bean deinitialization start.");

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.warn("Failed to close connection!",e);

                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.warn("Failed to close connection!",e);

                e.printStackTrace();
            }
        }
    }

    private ResultSet query(String query) {
        log.trace("SQL query: " + query);
        //
        if(!initialized) {
            log.fatal("");
            return null;
        }
        try {
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            log.warn("SQL failure during query execution.",e);

            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<AccountRecord> ResultSetToAccounts(ResultSet rs) {
        log.info("ResultSet to AccountRecord list conversion.");

        ArrayList<AccountRecord> result = new ArrayList<AccountRecord>();
        if (rs != null) {
            try {
                while (rs.next()) {
                    String pass = null;
                    result.add(new AccountRecord(
                            rs.getInt("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("status")
                    ));
                }
                rs.close();
            } catch (SQLException e) {
                log.warn("SQL failure during ResultSet parsing.",e);

                e.printStackTrace();
                return new ArrayList<AccountRecord>();
            }
        }
        return result;
    }

    public AccountRecord getAccountsRecord(String login, String password) {
        log.info("Singular AccountRecord retrieval with check. Login attempt.");

        if ((login == null) || (password == null)) {
            log.warn("Empty login or password.");

            return null;
        }
        ResultSet rs = query("SELECT * FROM accounts WHERE login='" + login + "' AND password='" + password + "'");
        AccountRecord result = null;
        if (rs != null) {
            try {
                rs.next();
                result = new AccountRecord(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("status")
                );
                rs.close();
            } catch (SQLException e) {
                log.warn("Incorrect login/pass.",e);

                return null;
            }
        }
        return result;
    }

    public ArrayList<AccountRecord> getAccounts() {
        ResultSet rs = query("SELECT * FROM accounts");
        return ResultSetToAccounts(rs);
    }

    public void updateAccounts(ArrayList<AccountRecord> data) {
        for (AccountRecord x : data) {
            String queryString = "INSERT INTO accounts (id, login, status, password) VALUES(" +
                    Integer.toString(x.getId()) + ",\"" +
                    x.getLogin() + "\",\"" +
                    x.getStatus() + "\",\"" +
                    x.getPassword() + "\"" +
                    ") ON DUPLICATE KEY UPDATE login=\"" +
                    x.getLogin() + "\"," +
                    "status=\"" +
                    x.getStatus() + "\"," +
                    "password=\"" +
                    x.getPassword() + "\"";
            try {
                stmt.executeUpdate(queryString);
            } catch (SQLException e) {
                log.warn("SQL failure during AccountRecord-based update.",e);
                e.printStackTrace();
            }
        }
    }

    public Integer getAccountIDByLogin(String login) {
        ResultSet rs = query("SELECT * FROM accounts WHERE login = \"" + login + "\"");
        Integer result = null;
        if (rs != null) {
            try {
                rs.next();
                result = rs.getInt("ID");
                rs.close();
            } catch (SQLException e) {
                log.warn("SQL failure during account ID retrieval.");
                e.printStackTrace();
            }
        }
        return result;
    }

    public ArrayList<TaskRecord> getTasksForManager(String login) {
        ResultSet rs = query("SELECT * FROM task WHERE idManager=\"" + login + "\"");
        ArrayList<TaskRecord> result = new ArrayList<TaskRecord>();
        log.trace("Manager-bound task retrieval.");
        if (rs != null) {
            try {
                while (rs.next()) {
                    result.add(new TaskRecord(
                            rs.getInt("id"),
                            rs.getString("idManager"),
                            rs.getString("idWorker"),
                            rs.getString("name"),
                            rs.getString("status"),
                            rs.getString("comment")
                    ));
                }
                rs.close();
            } catch (SQLException e) {
                log.warn("SQL failure during manager-bound tasks retrieval.",e);
                e.printStackTrace();
                return new ArrayList<TaskRecord>();
            }
        }
        return result;
    }

    public void updateTasks(ArrayList<TaskRecord> data) {
        for (TaskRecord x : data) {
            String queryString = "INSERT INTO task (id, idManager, idWorker, name, status, comment) VALUES(" +
                    Integer.toString(x.getId()) + ",\"" +
                    x.getIdManager() + "\",\"" +
                    x.getIdWorker() + "\",\"" +
                    x.getName() + "\",\"" +
                    x.getStatus() + "\",\"" +
                    x.getComment() + "\"" +
                    ") ON DUPLICATE KEY UPDATE idManager=\"" + x.getIdManager() + "\"," +
                    "idWorker =\"" + x.getIdWorker() + "\"," +
                    "name =\"" + x.getName() + "\"," +
                    "status =\"" + x.getStatus() + "\"," +
                    "comment =\"" + x.getComment() + "\"";
            String enslaveString = "INSERT INTO workers (idWorker, idManager)\n" +
                    "SELECT * FROM (SELECT\"" + x.getIdWorker() + "\",\"" + x.getIdManager() + "\") AS tmp\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT idWorker FROM workers WHERE idWorker = \"" + x.getIdWorker() + "\") LIMIT 1;";
            try {
                stmt.executeUpdate(queryString);
                stmt.executeUpdate(enslaveString);
            } catch (SQLException e) {
                log.warn("SQL failure during manager task update.",e);
                e.printStackTrace();
            }
        }
    }

    public ArrayList<AccountRecord> getFreeWorkers() {
        log.trace("Unbound workers retrieval.");
        String queryString =
                "SELECT a.id, a.login, a.status, a.password FROM " +
                        "accounts a " +
                        "WHERE status=\"Worker\" AND " +
                        "NOT EXISTS(" +
                        "SELECT NULL FROM " +
                        "workers w WHERE a.login = w.idWorker)";
        ResultSet rs = query(queryString);
        ArrayList<AccountRecord> result = ResultSetToAccounts(rs);

        return result;
    }

    public ArrayList<AccountRecord> getFreeWorkersOfManager(String login) {
        log.trace("Manager-bound free workers retrieval.");
        String queryString = "SELECT a.id, a.login, a.status, a.password FROM accounts a \n" +
                "WHERE status=\"Worker\"\n" +
                "AND EXISTS(\n" +
                "\tSELECT NULL FROM workers w WHERE \n" +
                "\t\tw.idManager = \"" + login + "\" AND\n" +
                "\t\tw.idWorker = a.login\n" +
                ")\n" +
                "AND NOT EXISTS(\n" +
                "\tSELECT NULL FROM task t WHERE \n" +
                "\t\tt.idWorker = a.login AND\n" +
                "\t\tt.status != \"Finished\"\n" +
                ")";
        ResultSet rs = query(queryString);
        ArrayList<AccountRecord> result = ResultSetToAccounts(rs);

        return result;
    }

    public ArrayList<WorkerTaskRecord> getWorkerTasks(String login) {
        log.trace("Worker-task data retrieval.");
        String queryString = "SELECT accounts.login, task.name, task.status \n" +
                "\n" +
                "FROM accounts, task\n" +
                "WHERE accounts.login = task.idWorker AND\n" +
                "task.status != \"Finished\" AND \n" +
                "EXISTS(\n" +
                "\tSELECT NULL FROM task WHERE\n" +
                "\t\tidWorker = accounts.login AND\n" +
                "        idManager = \"" + login + "\"" +
                ")";
        ResultSet rs = query(queryString);
        ArrayList<WorkerTaskRecord> result = new ArrayList<WorkerTaskRecord>();
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(new WorkerTaskRecord(
                            rs.getString("accounts.login"),
                            rs.getString("task.name"),
                            rs.getString("task.status")
                    ));
                }
                rs.close();
            }
            return result;
        } catch (SQLException e) {
            log.warn("SQL failure during worker-data retrieval.",e);
            return new ArrayList<WorkerTaskRecord>();
        }
    }

    public TaskRecord getCurrentTaskForWorker(String login) {
        log.trace("Worker's current task retrieval.");
        String queryString = "SELECT * FROM task WHERE\n" +
                "idWorker = \"" + login + "\" AND\n" +
                "status != \"Finished\"";
        ResultSet rs = query(queryString);
        TaskRecord result = null;
        if (rs != null) {
            try {
                rs.next();
                result = new TaskRecord(
                        rs.getInt("id"),
                        rs.getString("idManager"),
                        rs.getString("idWorker"),
                        rs.getString("name"),
                        rs.getString("status"),
                        rs.getString("comment")
                );
                rs.close();
            } catch (SQLException e) {
                log.warn("SQL failure during worker's task retrieval.",e);
                return null;
            }
        }
        return result;
    }

    public ArrayList<TaskRecord> getTasksForWorker(String login) {
        log.trace("Worker's task history retrieval.");
        String queryString = "SELECT * FROM task WHERE\n" +
                "idWorker = \"" + login + "\" AND\n" +
                "status = \"Finished\"";
        ResultSet rs = query(queryString);
        ArrayList<TaskRecord> result = new ArrayList<TaskRecord>();
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(new TaskRecord(
                            rs.getInt("id"),
                            rs.getString("idManager"),
                            rs.getString("idWorker"),
                            rs.getString("name"),
                            rs.getString("status"),
                            rs.getString("comment")
                    ));
                }
                rs.close();
            }
            return result;
        } catch (SQLException e) {
            log.warn("SQL failure during worker's task history retrieval.",e);
            return new ArrayList<TaskRecord>();
        }
    }

    public void updateTaskForWorker(TaskRecord record) {
        log.trace("Worker's current task update");
        String queryString = "UPDATE task SET status= " +
                "\"" + record.getStatus() + "\"," +
                "comment= " +
                "\"" + record.getComment() + "\" WHERE " +
                "id=" + record.getId();
        try {
            stmt.executeUpdate(queryString);
        } catch (SQLException e) {
            log.warn("SQL failure during worker's current task update.",e);
            e.printStackTrace();
        }
    }
}