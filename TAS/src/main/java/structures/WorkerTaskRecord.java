package structures;

/**
 * Created by Ti_g_programmist(no) on 26.05.2017.
 */
public class WorkerTaskRecord {
    private String login;
    private String taskstatus;
    private String taskname;

    public WorkerTaskRecord(String login, String taskstatus, String taskname) {
        this.login = login;
        this.taskstatus = taskstatus;
        this.taskname = taskname;
    }

    public String getLogin() {
        return login;
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public String getTaskname() {
        return taskname;
    }
}
