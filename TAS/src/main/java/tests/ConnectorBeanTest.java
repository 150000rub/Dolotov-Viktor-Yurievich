package tests;

import beans.ConnectorBean;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import structures.AccountRecord;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ti_g_programmist(no) on 26.05.2017.
 */
public class ConnectorBeanTest {
    public ConnectorBean nowClass = null;
    public String login = null;
    public String pass = null;
    public AccountRecord output = null;
    public Server server = null;

    @Before
    public void setUp() throws Exception {
        server = new Server(8080);

        WebAppContext ctx = new WebAppContext();
        ctx.setResourceBase("src/main/webapp");
        ctx.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/[^/]*jstl.jar$");

        org.eclipse.jetty.webapp.Configuration.ClassList classList = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classList.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classList.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

        server.setHandler(ctx);

        server.start();
        System.out.println("Server started");

        ApplicationContext appContext =  (ApplicationContext) ctx.getServletContext().getAttribute("applicationContext");;
        nowClass = (ConnectorBean) appContext.getBean("connectorBean");
    }

    @After
    public void tearDown() throws Exception {
        nowClass = null;
        login = null;
        pass = null;
        server.stop();
        server = null;
    }

    @Test
    public void testgetAccountsRecordNullString() throws Exception {
        assertEquals(output, nowClass.getAccountsRecord(login, pass));
    }

    @Test
    public void testgetAccountsRecordNullLoginUncorrectPass() throws Exception {
        pass = "a's;dlfkgjh";
        assertEquals(output, nowClass.getAccountsRecord(login, pass));
    }

    @Test
    public void testgetAccountsRecordNullLogCorectPass() throws Exception {
        pass = "admin";
        assertEquals(output, nowClass.getAccountsRecord(login, pass));
    }

    @Test
    public void testgetAccountsRecordUncorrectLogNullPass() throws Exception {
        login = "z/x.c,vmbna's;dlfkgjh";
        assertEquals(output, nowClass.getAccountsRecord(login, pass));
    }

    @Test
    public void testgetAccountsRecordCorrectLogUncorectPass() throws Exception {
        login = "admin";
        assertEquals(output, nowClass.getAccountsRecord(login, pass));
    }

    @Test
    public void testgetAccountsRecordEzeLogEzePass() throws Exception {
        login = "qwerty";
        pass = "12345";
        nowClass.init();
        assertEquals(output, nowClass.getAccountsRecord(login, pass));
    }

    @Test
    public void testgetAccountsRecordCorrectLogCorectPass() throws Exception {
        login = "admin";
        pass = "admin";
        nowClass.init();
        output = new AccountRecord(1,"admin","admin","Admin");
        AccountRecord rec = nowClass.getAccountsRecord(login, pass);
        assertEquals(output.getId(), rec.getId());
        assertEquals(output.getLogin(), rec.getLogin());
        assertEquals(output.getPassword(), rec.getPassword());
        assertEquals(output.getStatus(), rec.getStatus());

    }

}
