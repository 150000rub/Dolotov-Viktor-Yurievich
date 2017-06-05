package structures;

/**
 * Created by Ti_g_programmist(no) on 25.05.2017.
 */
public class AccountRecord {
    private int id;
    private String login;
    private String password;
    private String status;

    public AccountRecord(int id, String login, String password, String status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
        //"actually password's hash"
    }
}
