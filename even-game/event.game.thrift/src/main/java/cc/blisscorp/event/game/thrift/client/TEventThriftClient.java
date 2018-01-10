package cc.blisscorp.event.game.thrift.client;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.thrift.TException;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

import cc.blisscorp.event.game.thrift.gen.TEventGameService.Iface;
import cc.blisscorp.event.game.thrift.gen.TTransaction;

import com.bliss.framework.common.Config;
import com.bliss.framework.util.ConvertUtils;

public class TEventThriftClient implements Iface {

	private Integer maxPool;
	private Integer minPool;
	private String host;
	private Integer port;
	private int timeOut;

	private static final Lock createLock = new ReentrantLock();
	private ArrayBlockingQueue<PooledClient> queue;
	private static final long maxRecyleAge = 5 * 60 * 1000;
	private static Map<String, TEventThriftClient> instances = new NonBlockingHashMap<String, TEventThriftClient>();


	public static TEventThriftClient getInstance(String name) {
		if (!instances.containsKey(name)) {
			try {
				createLock.lock();
				if (!instances.containsKey(name)) {
					instances.put(name, new TEventThriftClient(name));
				}
			} finally {
				createLock.unlock();
			}
		}
		return instances.get(name);
	}

	public TEventThriftClient(String name) {
		host = Config.getParam(name, "host");
		port = ConvertUtils.toInt(Config.getParam(name, "port"), 9996);
		maxPool = ConvertUtils.toInt(Config.getParam(name, "maxpool"), 1024);
		minPool = ConvertUtils.toInt(Config.getParam(name, "minpool"), 256);
		timeOut = ConvertUtils.toInt(Config.getParam(name, "time_out"), 60000);

		queue = new ArrayBlockingQueue<PooledClient>(maxPool);
	}


	private PooledClient borrowClient() {
		PooledClient pooledClient = null;
		while (queue.size() > 0) {
			try {
				pooledClient = queue.take();
			} catch (InterruptedException ex) {

			}
			if (pooledClient.isAlive()) {
				return pooledClient;
			}
		}
		pooledClient = new PooledClient(host, port, timeOut);
		return pooledClient;
	}


	private void returnClient(PooledClient client) {
		if (client == null) {
			return;
		}
		if (queue.size() >= maxPool) {
			client.destroy();
			return;
		}

		long diffInSec = (System.currentTimeMillis() - client.getDateCreated());

		if (queue.size() > minPool && diffInSec > maxRecyleAge) {
			client.destroy();
			return;
		}
		try {
			queue.put(client);
		} catch (InterruptedException ex) {
		}
	}


	private void invalidClient(PooledClient client) {
		if (client == null) {
			return;
		}
		if (client.isAlive()) {
			returnClient(client);
		}
	}


	@Override
	public boolean ping() throws TException {
		return true;
	}


	@Override
	public TTransaction saveTrans(TTransaction tTrans) throws TException {
		PooledClient client = borrowClient();
		try {
			tTrans = client.getClient().saveTrans(tTrans);
			returnClient(client);
			return tTrans;
		} catch (Exception e) {
			invalidClient(client);
			throw new TException(e.getMessage());
		}
	}

}
