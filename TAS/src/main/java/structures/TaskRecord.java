package structures;

/**
 * Created by Ti_g_programmist(no) on 26.05.2017.
 */
public class TaskRecord {
    private int id;
    private String idManager;
    private String idWorker;
    private String name;
    private String status;
    private String comment;

    public TaskRecord(int id, String idManager, String idWorker, String name, String status, String comment) {
        this.id = id;
        this.idManager = idManager;
        this.idWorker = idWorker;
        this.name = name;
        this.status = status;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getIdManager() {
        return idManager;
    }

    public String getIdWorker() {
        return idWorker;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }
}
