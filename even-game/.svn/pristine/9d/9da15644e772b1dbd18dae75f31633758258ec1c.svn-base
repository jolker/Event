/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.event.game.jdbc;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

public class DBParameters {

    private int count;
    private int[] types;
    private Object[] values;

    public DBParameters() {
        this.count = 0;
        this.types = new int[8];
        this.values = new Object[8];
    }

    private void doAdd(int type, Object o) {
        if (this.count == this.types.length) {
            int[] new_types = new int[this.types.length * 2];
            Object[] new_values = new Object[this.values.length * 2];
            for (int scan = 0; scan < this.count; scan++) {
                new_types[scan] = this.types[scan];
                new_values[scan] = this.values[scan];
            }
            this.values = new_values;
            this.types = new_types;
        }

        this.types[this.count] = type;
        this.values[this.count] = o;
        this.count += 1;
    }

    public void add(int type, Object o) throws SQLException {
        doAdd(Types.INTEGER, o);
    }
    
    public void add(boolean value) throws SQLException {
        doAdd(Types.BOOLEAN, value);
    }
    
    public void add(Double value) throws SQLException {
        doAdd(Types.DOUBLE, value);
    }
    
    public void add(byte[] value) throws SQLException {
    	doAdd(Types.BLOB, value);
    }

    public void add(String value) throws SQLException {
        doAdd(Types.VARCHAR, value);
    }
    
    public void add(Object[] values) throws SQLException {
        doAdd(Types.JAVA_OBJECT, values);
    }
    
    public void add(BigDecimal value) throws SQLException {
        doAdd(Types.DECIMAL, value);
    }

    public void add(long value) throws SQLException {
        doAdd(Types.BIGINT, new Long(value));
    }

    public void add(int value) throws SQLException {
        doAdd(Types.INTEGER, new Integer(value));
    }
    
    public void add(float value) throws SQLException {
        doAdd(Types.FLOAT, new Float(value));
    }

    public void add(Timestamp value) throws SQLException {
        doAdd(Types.TIMESTAMP, value);
    }
    
    public void add(Date value) throws SQLException {
        doAdd(Types.DATE, value);
    }

    public int getCount() {
        return this.count;
    }

    public void primeStatements(PreparedStatement aStatement, int aStmtIndxOffset) throws SQLException {
        for (int scan = 0; scan < this.count; scan++) {
            Object value = this.values[scan];
            int type = this.types[scan];
            if (type == 1111) {
                continue;
            }
            aStmtIndxOffset++;
            if (value == null) {
                aStatement.setNull(aStmtIndxOffset, type);
            } else if ((value instanceof BigDecimal)) {
                aStatement.setBigDecimal(aStmtIndxOffset, (BigDecimal) value);
            } else if ((value instanceof Date)) {
            	aStatement.setTimestamp(aStmtIndxOffset, new java.sql.Timestamp(((java.util.Date)value).getTime()));
            } 
            else {
                aStatement.setObject(aStmtIndxOffset, value, type);
            }
        }
    }

    public void reset() {
        this.count = 0;
    }

    public String expand(String query) {
        StringBuffer sb = new StringBuffer();
        if (query != null) {
            sb.append(query);
            int pos = -1;
            for (int scan = 0; scan < this.count; scan++) {
                Object value = this.values[scan];
                int type = this.types[scan];
                if (type == 1111) {
                    continue;
                }
                pos = sb.indexOf("?", pos + 1);
                if (pos < 0) {
                    break;
                }
                if (value == null) {
                    sb.replace(pos, pos + 1, "null");
                } else {
                    sb.replace(pos, pos + 1, value.toString());
                }
            }
        }
        return sb.toString();
    }
}
