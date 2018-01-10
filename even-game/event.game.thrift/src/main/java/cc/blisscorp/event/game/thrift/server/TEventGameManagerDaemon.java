package cc.blisscorp.event.game.thrift.server;

import ga.log4j.GA;

import org.apache.thrift.TProcessor;

import cc.blisscorp.event.game.thrift.gen.TEventGameService;

import com.nct.framework.thriftutil.TServerManager;


public class TEventGameManagerDaemon {
	
	public static void main(String[] args) {
		try {
			TProcessor tProcessor = new TEventGameService.Processor<TEventGameProcessor>(new TEventGameProcessor());
			TServerManager server = new TServerManager();
	        server.start("thrift_event_game_server", tProcessor);
		} catch (Exception e) {
			GA.app.error("Don't start Server Thrift " + e.getMessage(), e);
			System.exit(1);
		}
	}
}
