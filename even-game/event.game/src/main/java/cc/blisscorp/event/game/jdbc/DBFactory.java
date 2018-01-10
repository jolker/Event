/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.event.game.jdbc;

import ga.log4j.GA;

import java.util.HashMap;
import java.util.Map;

public class DBFactory {

    public static final int QUERY = 0;
    public static final int INSERT = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    public static final int SEQUENCE = 4;
	@SuppressWarnings("rawtypes")
	private Map[] mCache = null;

    public DBFactory() {
        this.mCache = new Map[5];
        this.mCache[QUERY] = new HashMap<String, String>();
        this.mCache[INSERT] = new HashMap<String, String>();
        this.mCache[UPDATE] = new HashMap<String, String>();
        this.mCache[DELETE] = new HashMap<String, String>();
        this.mCache[SEQUENCE] = new HashMap<String, String>();
    }

    public DBQuery query(String aName) {
        String cmd = getCacheData(QUERY, aName);
        if (cmd == null) {
            return createQuery(aName);
        }
        return createQuery(aName, cmd);
    }

    public DBInsert insert(String aName) {
        String cmd = getCacheData(INSERT, aName);
        if (cmd == null) {
            return createInsert(aName);
        }
        return createInsert(aName, cmd);
    }

    public DBUpdate update(String aName) {
        String cmd = getCacheData(UPDATE, aName);
        if (cmd == null) {
            return createUpdate(aName);
        }
        return createUpdate(aName, cmd);
    }

    public DBDelete delete(String aName) {
        String cmd = getCacheData(DELETE, aName);
        if (cmd == null) {
            return createDelete(aName);
        }
        return createDelete(aName, cmd);
    }

	
	@SuppressWarnings("unchecked")
	public void setCacheData(int type, String hash, String aSQLString) {
        GA.jdbc.debug("Caching key: [" + hash + "], sql: [" + aSQLString + " ]");
        synchronized (this.mCache[type]) {
            this.mCache[type].put(hash, aSQLString);
        }
    }

    public String getCacheData(int type, String hash) {
        synchronized (this.mCache[type]) {
            return (String) this.mCache[type].get(hash);
        }
    }

    public DBQuery createQuery(String aName) {
        return new DBQuery(this, aName);
    }

    public DBQuery createQuery(String aName, String aQuerySQL) {
        return new DBQuery(this, aName, aQuerySQL);
    }

    public DBInsert createInsert(String aName) {
        return new DBInsert(this, aName);
    }

    public DBInsert createInsert(String aName, String aQuerySQL) {
        return new DBInsert(this, aName, aQuerySQL);
    }

    public DBUpdate createUpdate(String aName) {
        return new DBUpdate(this, aName);
    }

    public DBUpdate createUpdate(String aName, String aQuerySQL) {
        return new DBUpdate(this, aName, aQuerySQL);
    }

    public DBDelete createDelete(String aName) {
        return new DBDelete(this, aName);
    }

    public DBDelete createDelete(String aName, String aQuerySQL) {
        return new DBDelete(this, aName, aQuerySQL);
    }
}
