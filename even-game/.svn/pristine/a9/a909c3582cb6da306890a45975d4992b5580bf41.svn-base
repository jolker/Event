package cc.blisscorp.event.game.jdbc;

import ga.log4j.GA;

import java.sql.Connection;
import java.sql.SQLException;

import com.bliss.framework.dbconn.ClientManager;
import com.bliss.framework.dbconn.ManagerIF;

/**
 * Manager connectionPool 
 *
 */
public class TransactionFrame {

	protected boolean connected;
	protected boolean mAutoCommit;
	protected String mConnectionName;
	protected Connection connection;
	protected ManagerIF mConnectionPool;

	public boolean isConnected() {
		return connected;
	}

	/**
	 * init connection
	 * @param isAutoCommit
	 */
	public TransactionFrame(boolean isAutoCommit) {
		GA.jdbc.info("create TransactionFrame");
		this.mAutoCommit = isAutoCommit;
		this.connected = true;
		this.mConnectionName = DBContants.M_CONNECTION_NAME;
	}

	/**
	 * init connection. autoCommit = true
	 */
	public TransactionFrame() {
		this(true);
	}

	public void setAutoCommit(boolean mAutoCommit) {
		try {
			this.mAutoCommit = mAutoCommit;
			if (!this.mAutoCommit && this.connection != null) {
				this.connection.setAutoCommit(this.mAutoCommit);
			}
		} catch (SQLException e) {
			GA.jdbc.error("setAutoCommit: ", e);
		}
	}

	private void releasePooledConn() {
		GA.jdbc.info("TF releasedPooledConnection " + mConnectionName);
		if (this.connection != null && this.mConnectionPool != null) {
			try {
				if (!this.mAutoCommit) {
					this.connection.setAutoCommit(true);
				}
				this.mConnectionPool.returnClient(this.connection);

				// TODO: UNCOMMENT
				// mConnectionPool = null;

			} catch (SQLException e) {
				GA.jdbc.error("ReleasePooledConnection failed: " + this.connection + "" + e);
			}
		}
	}

	private void commit() throws SQLException {
		this.connected = false;
		try {
			if (this.connection != null) {
				this.connection.commit();
			}

		} catch (SQLException e) {
			GA.jdbc.error("COMMIT failed", e);
			throw e;
		}
	}

	private void rollback() throws SQLException {
		this.connected = false;
		try {
			if (this.connection != null) {
				this.connection.rollback();
			}
		} catch (SQLException e) {
			GA.jdbc.error("ROLLBACK failed: " + e);
			throw e;
		}
	}

	public void close() throws SQLException {
		this.connected = false;
		try {
			if (this.connection != null) {
				this.connection.rollback();
			}
		} catch (SQLException e) {
			GA.jdbc.error("ROLLBACK failed: " + e);
			throw e;
		}
	}

	public void initConnection() throws SQLException {
		try {
			if (!this.connected) {
				throw new IllegalStateException("Transaction Frame is Closed");
			}

			if (this.connection != null) {
				return;
			}

			mConnectionPool = ClientManager.getInstance(mConnectionName);
			this.connection = mConnectionPool.borrowClient();
			this.connection.setAutoCommit(this.mAutoCommit);

			GA.jdbc.debug(getClass().getName() + " .Connection. " + this.connection);
		} catch (Exception e) {
			throw new SQLException(e.getMessage(), e);
		}	
	}

	public void returnResource() throws SQLException {
		if (!this.mAutoCommit) {
			commit();	
		}		
		releasePooledConn();
	}

	public void returnBrokenResource() throws SQLException {
		rollback();
		releasePooledConn();
	}

}
