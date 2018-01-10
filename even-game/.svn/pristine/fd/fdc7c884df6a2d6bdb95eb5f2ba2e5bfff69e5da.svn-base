/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.event.game.jdbc;

import ga.log4j.GA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hoanv
 */
public class DBInsert extends DBObject {

    private String table;
    private StringBuffer columns;
    protected StringBuffer place_holders;

    public DBInsert(DBFactory aFactory, String hash, String aSQLString) {
        super(aFactory, hash, aSQLString);
        this.columns = new StringBuffer();
        this.place_holders = new StringBuffer();
    }

    public DBInsert(DBFactory aFactory, String hash) {
        super(aFactory, hash);
        this.columns = new StringBuffer();
        this.place_holders = new StringBuffer();
    }

    @Override
    public DBParameters getParameter() {
        return this.parameters;
    }

    public void setTable(String table) {
        if (isCached()) {
            GA.jdbc.warn("[ DBInsert ] setTable(String table) is ineffective to SQL Insert String ...");
            return;
        }
        this.table = table;
    }

    public void addPlaceHolder(int colNum){

        if (this.place_holders.length() > 0)
            this.place_holders.append(", (");
        else
            this.place_holders.append("(");

        for(int i = 1 ; i <= colNum; i++){
            if(i == colNum )
                this.place_holders.append("?)");
            else
                this.place_holders.append("?,");
        }
    }


    public long executeInsert() throws SQLException {
        String str_insert = this.query.toString();
        if (!isCached()) {
            str_insert = getQuery();
            if (getID() != null)
            {
                cache(str_insert);
            }
        }

        PreparedStatement statement = getTF().connection.prepareStatement(str_insert, Statement.RETURN_GENERATED_KEYS);
        this.parameters.primeStatements(statement, 0);
        this.parameters.reset();

        GA.jdbc.debug("SQL: " + statement.toString());
        long result = statement.executeUpdate();
        if(result > 0) {
            ResultSet rs = statement.getGeneratedKeys();
            if(rs != null && rs.next())
                result = rs.getLong(1);
        }
        return result;
    }

    private String getQuery() {
        StringBuffer ins = new StringBuffer();
        ins.append("INSERT INTO ");
        ins.append(this.table);

        if (this.columns.length() != 0) {
            ins.append(" (");
            ins.append(this.columns);
            ins.append(") ");

            ins.append("VALUES ");
            ins.append(this.place_holders);
        }
        return ins.toString();
    }


    @Override
	public void addColumn(String name) throws SQLException {
        if (this.columns.length() > 0)
            this.columns.append(',');
        this.columns.append(name);
    }

    @Override
    protected void cache(String aSQLString) {
        if (!isCached()) {
            getFactory().setCacheData(DBFactory.INSERT, getID(), aSQLString);
        }
    }

    public String toString() {
        return this.parameters.expand(getQuery());
    }
}
