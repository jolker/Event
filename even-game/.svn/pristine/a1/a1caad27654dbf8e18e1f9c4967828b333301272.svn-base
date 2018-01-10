package cc.blisscorp.event.game.launcher;

import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;

import com.nct.framework.common.Config;
import com.nct.framework.util.ConvertUtils;

import cc.blisscorp.event.game.scheduler.AccrueEventScheduler;
import cc.blisscorp.event.game.scheduler.EventScheduler;
import cc.blisscorp.event.game.scheduler.TransactionScheduler;
import ga.log4j.GA;


public class Console implements Daemon {
	private int jettyPort = 9107;
	private String host = "127.0.0.1";
	private int maxThreadPoolSize = 32;
	private static final int CONNECTOR_MAX_IDLE_TIME = 60000;
	private Server jettyServer = null;

	private String webappContextPath = "event-game";

	private static final String WEBAPP_RESOURCES_LOCATION = "webapp";

	public Console() {
		host = Config.getParam("jetty", "host");
		webappContextPath = Config.getParam("jetty", "context_path");
		jettyPort = ConvertUtils.toInt(Config.getParam("jetty", "port"));
		maxThreadPoolSize = ConvertUtils.toInt(Config.getParam("jetty", "max_thread"));
	}

	public void init(DaemonContext context) throws DaemonInitException, Exception {
		GA.app.info("embeddedJetty initialized with arguments {} " + context.getArguments());
	}

	public void start() throws Exception {
		jettyServer = new Server();
		jettyServer.setThreadPool(jettyThreadPool());
		jettyServer.setConnectors(jettyConnectors());
		jettyServer.setHandler(jettyWebAppContext());
		jettyServer.start();
	}

	public void stop() throws Exception {
		GA.app.info("Stopping embeddedJetty");
		if (jettyServer != null) {
			jettyServer.stop();
		}
	}

	public void destroy() {
		GA.app.info("Destroying embeddedJetty");
		jettyServer = null;
	}
	public QueuedThreadPool jettyThreadPool() {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(10);
		threadPool.setMaxThreads(maxThreadPoolSize);
		return threadPool;
	}


	public Connector[] jettyConnectors() {
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(jettyPort);
		connector.setMaxIdleTime(CONNECTOR_MAX_IDLE_TIME);
		connector.setHost(host);
		return new Connector[]{connector};
	}


	public WebAppContext jettyWebAppContext() throws URISyntaxException {
		WebAppContext webapp = new WebAppContext();
		URL webAppDir = Thread.currentThread().getContextClassLoader().getResource(WEBAPP_RESOURCES_LOCATION);
		webapp.setResourceBase(webAppDir.toURI().toString());
		webapp.setDescriptor(webAppDir.toURI().toString() + "web.xml");
		webapp.setContextPath("/" + webappContextPath);
		return webapp;	
	}
	
	public static void main(String[] args) {
		try {
			Console console = new Console();
			console.start();		
			
			EventScheduler.getInstance().execute();
			TransactionScheduler.getInstance().execute();
			
			AccrueEventScheduler.getInstance().execute();
			
			GA.app.info("start game-news service successfully");
		} catch (Exception ex) {			
			GA.app.error("start game-news service failed." + ex.getMessage(), ex);
			System.exit(1);
		}
	}
}
