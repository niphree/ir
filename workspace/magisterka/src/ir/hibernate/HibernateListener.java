package ir.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateListener implements ServletContextListener {
	 
    public void contextInitialized(ServletContextEvent event) {
        HibernateUtil.getSession(); // Just call the static initializer of that class    
    }
 
    public void contextDestroyed(ServletContextEvent event) {
        HibernateUtil.getSession().close(); // Free all resources
    }
}
