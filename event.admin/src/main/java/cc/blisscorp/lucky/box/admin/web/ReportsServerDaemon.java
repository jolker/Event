/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.web;

import com.nct.framework.common.LogUtil;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author anhlnt
 */
public class ReportsServerDaemon {

    private static final Logger logger = LogUtil.getLogger(ReportsServerDaemon.class);

    public static void main(String[] agrs) {

        JettyServer webserver = new JettyServer();
        String pidFile = System.getProperty("pidfile");

        try {
            logger.info("report web server is starting...");
            if (pidFile != null) {
                new File(pidFile).deleteOnExit();
            }

            logger.info("reports web server started");
            webserver.start();
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));

            System.exit(3);
        }
    }
}
