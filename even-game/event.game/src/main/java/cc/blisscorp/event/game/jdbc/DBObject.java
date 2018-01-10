/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.event.game.jdbc;

/**
 *
 * @author hoanv
 */
import java.sql.SQLException;

public abstract class DBObject {

	private DBFactory mFactory;
	private TransactionFrame tf;
	private String sID;
	protected DBParameters parameters;
	protected StringBuffer where_clause;
	private boolean isCached;
	protected StringBuffer query;

	public DBObject(DBFactory aFactory, String sID) {
		this.mFactory = aFactory;
		this.sID = sID;
		parameters = new DBParameters();
		where_clause = new StringBuffer();
		query = new StringBuffer();
	}

	public DBObject(DBFactory aFactory, String sID, String aQuerySQL) {
		this.mFactory = aFactory;
		this.sID = sID;
		parameters = new DBParameters();
		where_clause = new StringBuffer();
		query = new StringBuffer(aQuerySQL);
		isCached = true;
	}

	public final void initialization(TransactionFrame tf) throws SQLException {
		this.tf = tf;
		tf.initConnection();
	}

	public boolean isCached(){
		return isCached;
	}

	public void addClause(String aClause) throws SQLException {
		if (this.where_clause.length() > 0) {
			this.where_clause.append(" AND ");
		}
		this.where_clause.append(aClause);
	}

	public void addClause(String column, String expression) throws SQLException {
		addClause(column + " " + expression);
	}

	protected TransactionFrame getTF() {
		return this.tf;
	}

	protected String getID() {
		return this.sID;
	}

	protected abstract void cache(String aSQLString);

	public abstract void addColumn(String name) throws SQLException;

	public abstract DBParameters getParameter();

	protected DBFactory getFactory() {
		return this.mFactory;
	}
	
}
