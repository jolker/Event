/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.event.game.jdbc;

import ga.log4j.GA;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUpdate extends DBObject {
    private String table;
    private StringBuffer up_list;
    private int mLimit;


    public DBUpdate(DBFactory aFactory, String hash) {
        super(aFactory, hash);
        this.up_list = new StringBuffer();
    }

    public DBUpdate(DBFactory aFactory, String hash, String aSQLString) {
        super(aFactory, hash, aSQLString);
    }

    @Override
    public DBParameters getParameter() {
        return parameters;
    }

    public void setTable(String table) {
        if (isCached()) {
            GA.jdbc.warn("[ DBUpdate ] setTable(String table) is ineffective to SQL Update String ...");
            return;
        }
        this.table = table;
    }

    public void addNow(String name) {
        addPlaceHolder(name, "SYSTIMESTAMP");
    }

    public void addPlaceHolder(String name, String value) {
        if (isCached()) {
            GA.jdbc.warn("[ DBUpdate ] setTable(String table) is ineffective to SQL Update String ...");
            return;
        }
        if (this.up_list.length() > 0) this.up_list.append(',');
        this.up_list.append(name);
        this.up_list.append('=');
        this.up_list.append(value);
    }

    public int executeUpdate() throws SQLException {
        String str_update = this.query.toString();
        if (!isCached()) {
            str_update = toString();

            if (getID() != null) {
                cache(str_update);
            }
        }

        PreparedStatement statement = getTF().connection.prepareStatement(str_update);
        parameters.primeStatements(statement, 0);
        parameters.reset();
        return statement.executeUpdate();
    }

    public String toString() {
        StringBuffer upd = new StringBuffer();
        upd.append("UPDATE ");
        upd.append(this.table);
        upd.append(" SET ");
        upd.append(this.up_list);
        if (where_clause.toString().length() > 0) {
            upd.append(" WHERE ");
            upd.append(where_clause.toString());
        }

        if (this.mLimit > 0) {
            return upd.toString() + " LIMIT " + this.mLimit;
        }
        return upd.toString();
    }

    public void setRowLimit(int aLimit) {
        this.mLimit = aLimit;
    }

    protected void cache(String aSQLString) {
        getFactory().setCacheData(DBFactory.UPDATE, getID(), aSQLString);
    }

    @Override
    public void addColumn(String name) throws SQLException {
        addPlaceHolder(name, "?");
    }
}

