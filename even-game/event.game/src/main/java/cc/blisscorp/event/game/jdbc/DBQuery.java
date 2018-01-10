/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.event.game.jdbc;

import ga.log4j.GA;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

/**
 * @author hoanv
 * @author tuyenpv
 */
public class DBQuery extends DBObject {

	private static final int LOCK_WAIT = 1;
	private static final int LOCK_NOWAIT = 2;

	private StringBuffer table;
	private StringBuffer order_clause;
	private StringBuffer group_clause;
	private String limit;
	private int lock;

	// create a cached DBQuery: there was SQLQuery
	public DBQuery(DBFactory aFactory, String hash, String aSQLString) {
		super(aFactory, hash, aSQLString);
	}

	// create a new DBQuery
	public DBQuery(DBFactory aFactory, String hash) {
		super(aFactory, hash);
		this.table = new StringBuffer();
		this.order_clause = new StringBuffer();
		this.group_clause = new StringBuffer();
	}

	public ResultSet executeQuery() throws SQLException {
		String str_select = this.query.toString();
		if (!isCached()) {
			str_select = toString();
			if (getID() != null) {
				cache(str_select);
			}
		}

		switch (this.lock) {
		case 1:
			str_select = str_select.concat(" FOR UPDATE");
			break;
		case 2:
			str_select = str_select.concat(" FOR UPDATE NOWAIT");
		}

		GA.jdbc.debug("TF: " + getTF());

		PreparedStatement statement = getTF().connection.prepareStatement(str_select);
		if (where_clause != null) {
			parameters.primeStatements(statement, 0);
			parameters.reset();
		}

		GA.jdbc.debug("query: " + statement.toString());

		ResultSet rs = statement.executeQuery();
		return rs;
	}

	public void addTable(String table) {
		addTable(table, null);
	}

	public void addTable(String ntable, String shortname) {
		if (isCached()) {
			GA.jdbc.warn(
					"[ DBQuery ] addTable(String ntable, String shortname) is ineffective to SQL Query String ...");
			return;
		}
		if (this.table.length() > 0) {
			this.table.append(',');
		}
		this.table.append(ntable);
		if (shortname != null) {
			this.table.append(' ');
			this.table.append(shortname);
		}
	}

	public DBParameters getParameter() {
		return parameters;
	}

	public void addColumn(String aColumn) {
		if (isCached()) {
			GA.jdbc.warn("[ DBQuery ] addColumn(String aColumn) is ineffective to SQL Query String ...");
			return;
		}
		if (this.query.length() > 0) {
			this.query.append(',');
		}
		this.query.append(aColumn);
	}

	public void addJoin(String left, String right) throws SQLException {
		if (isCached()) {
			GA.jdbc.warn("[ DBQuery ] addJoin(String left, String right) is ineffective to SQL Query String ...");
			return;
		}
		addClause(left + "=" + right);
	}

	public void setQueryOrder(String field) throws SQLException {
		if (isCached()) {
			GA.jdbc.warn("[ DBQuery ] setQueryOrder(String field) is ineffective to SQL Query String ...");
			return;
		}
		if (this.order_clause.length() == 0) {
			this.order_clause.append(" ORDER BY ");
		} else {
			this.order_clause.append(",");
		}
		this.order_clause.append(field);
	}

	public void setQueryOrder(String field, String dir) throws SQLException {
		if (isCached()) {
			GA.jdbc.warn("[ DBQuery ] setQueryOrder(String field, String dir) is ineffective to SQL Query String ...");
			return;
		}
		if (this.order_clause.length() == 0) {
			this.order_clause.append(" ORDER BY ");
		} else {
			this.order_clause.append(",");
		}
		this.order_clause.append(field);
		this.order_clause.append(' ');
		this.order_clause.append(dir);
	}

	public void setGroupBy(String field) throws SQLException {
		if (isCached()) {
			GA.jdbc.warn("[ DBQuery ] setGroupBy(String field) is ineffective to SQL Query String ...");
			return;
		}
		if (this.group_clause.length() == 0) {
			this.group_clause.append(" GROUP BY ");
		} else {
			this.group_clause.append(",");
		}
		this.group_clause.append(field);
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String toString() {
		StringBuffer select = new StringBuffer();
		StringBuffer finalString = new StringBuffer();
		select.append("SELECT ");
		if (this.query.length() == 0) {
			select.append("*");
		} else {
			select.append(this.query);
		}
		if (this.table.length() == 0) {
			select.append(" FROM DUAL");
		} else {
			select.append(" FROM ");
			select.append(this.table);
		}
		if (where_clause.length() > 0) {
			select.append(" WHERE ");
			select.append(where_clause);
		}
		if (this.group_clause.length() > 0) {
			select.append(this.group_clause);
		}
		if (this.order_clause.length() > 0) {
			select.append(this.order_clause);
		}

		if (this.limit != null) {
			select.append(" LIMIT ");
			select.append(this.limit);
		}

		finalString = finalString.append(select.toString());
		return finalString.toString();
	}

	public void lock() {
		this.lock = LOCK_WAIT;
	}

	public void lockNoWait() {
		this.lock = LOCK_NOWAIT;
	}

	public static void defineCriterion(String param, Object value, DBQuery dbQuery) throws Exception {
		if (value instanceof Object[]) {
			String inQuery = "IN(";
			Object[] objects = (Object[]) value;
			for (int index = 0; index < objects.length - 1; index++) {
				inQuery = inQuery.concat("\"").concat(objects[index].toString());
				inQuery = inQuery.concat("\"").concat(",");
			}
			inQuery = inQuery.concat("\"").concat(objects[objects.length - 1].toString());
			inQuery = inQuery.concat("\"").concat(")");
			dbQuery.addClause(param, inQuery);
		} else if (StringUtils.equals(param, "objIdFrom")) {
			dbQuery.addClause("id", ">=" + Long.valueOf(value.toString()));
		} else if (StringUtils.equals(param, "objIdTo")) {
			dbQuery.addClause("id", "<" + Long.valueOf(value.toString()));
		} else if (StringUtils.equals(param, "fromCreatedDate")) {
			String inQuery = "\"" + new java.sql.Timestamp(((java.util.Date) value).getTime()) + "\"";
			dbQuery.addClause("created_at", ">=" + inQuery);
		} else if (StringUtils.equals(param, "toCreatedDate")) {
			String inQuery = "\"" + new java.sql.Timestamp(((java.util.Date) value).getTime()) + "\"";
			dbQuery.addClause("created_at", "<" + inQuery);
		} else if (StringUtils.equals(param, "fromUpdatedDate")) {
			String inQuery = "\"" + new java.sql.Timestamp(((java.util.Date) value).getTime()) + "\"";
			dbQuery.addClause("updated_at", ">=" + inQuery);
		} else if (StringUtils.equals(param, "toUpdatedDate")) {
			String inQuery = "\"" + new java.sql.Timestamp(((java.util.Date) value).getTime()) + "\"";
			dbQuery.addClause("updated_at", "<" + inQuery);
		} else if (StringUtils.equals(param, "fromScore")) {
			dbQuery.addClause("score", ">=" + Integer.valueOf(value.toString()));
		} else if (StringUtils.equals(param, "fromGiftsTotal")) {
			dbQuery.addClause("gifts_total", ">=" + Integer.valueOf(value.toString()));
		} else if (StringUtils.equals(param, "fromAccrueScore")) {
			dbQuery.addClause("accrue_score", ">=" + Integer.valueOf(value.toString()));
		} else {
			dbQuery.addClause(param, "=?");
			assignValue2Query(value, dbQuery);
		}
	}

	private static void assignValue2Query(Object value, DBQuery query) throws Exception {
		if (value instanceof Integer)
			query.getParameter().add(Integer.valueOf(value.toString()));
		else if (value instanceof String)
			query.getParameter().add(value.toString());
		else if (value instanceof Long)
			query.getParameter().add(Long.valueOf(value.toString()));
		else if (value instanceof BigDecimal)
			query.getParameter().add(BigDecimal.valueOf((Long) value));
		else {
			throw new Exception("not found type " + value);
		}
	}

	@Override
	protected void cache(String aSQLString) {

	}
}
