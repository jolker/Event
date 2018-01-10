package cc.blisscorp.event.game.thrift.client;

import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import cc.blisscorp.event.game.thrift.gen.TEventGameService;

public class PooledClient {

	private long dateCreated;

	private TEventGameService.Client client;

	public PooledClient(String host, int port, int timeOut) {
		dateCreated = System.currentTimeMillis();
		TTransport transport = new TFramedTransport(new TSocket(host, port, timeOut));
		TProtocol protocol = new TBinaryProtocol(transport);
		try {
			transport.open();
			TServiceClientFactory<?> factory = new TEventGameService.Client.Factory();
			client = (TEventGameService.Client) factory.getClient(protocol);
		} catch (Exception ex) {
			//	            LogUtils.printDebug(this.getClass().getName(), ex);
		}
	}

	public boolean isAlive() {
		if (client == null) {
			return false;
		}
		TFramedTransport transport = (TFramedTransport) client.getOutputProtocol().getTransport();
		if (transport == null || transport.isOpen() == false) {
			return false;
		}
		try {
			client.ping();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public void destroy() {        
		if (client == null) {
			return;
		}
		TTransport itrans = client.getInputProtocol().getTransport();
		TTransport otrans = client.getOutputProtocol().getTransport();
		if (itrans != null) {
			itrans.close();
		}
		if (otrans != null && otrans != itrans) {
			otrans.close();
		}
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public TEventGameService.Client getClient() {
		return client;
	}

}
