package beans;

import structures.Rule;

import javax.servlet.FilterConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ti_g_programmist(no) on 25.05.2017.
 */
public class RulesBean {
    private static List<Rule> rules = new ArrayList<Rule>();
    private boolean initialized = false;

    public void init(FilterConfig filterConfig) {
        if (!initialized) {
            BufferedReader res = new BufferedReader(new InputStreamReader(filterConfig.getServletContext().getResourceAsStream("/WEB-INF/users.txt")));
            try {
                String tmp;
                while ((tmp = res.readLine()) != null) {
                    String[] strings = tmp.split("~");
                    if (strings.length == 2) rules.add(new Rule(strings[0], strings[1]));
                }
                initialized = true;
            } catch (IOException ioe) {
                System.err.println("Access rules not loaded!");
                ioe.printStackTrace();
            }
        }
    }

    public List<Rule> getRules() {
        return rules;
    }
}
