package ca.mkk.project;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import ca.mkk.project.DataSourceFactory;

/**
 * Application Lifecycle Listener implementation class SenHotelContextListener
 *
 */
@WebListener
public class SenHotelContextListener implements ServletContextListener {
	@Resource(name = "SenHotelDS")
	private DataSource DS;

    /**
     * Default constructor. 
     */
    public SenHotelContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent context) {
		DataSourceFactory.setDataSource(null);
    	}

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent context) {
    	DataSourceFactory.setDataSource(DS);
    }
	
}
