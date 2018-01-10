package cc.blisscorp.event.game.jdbc;

public class DBCacheImpl {

    private static DBCacheImpl sInstance;
    
    private static final DBFactory mFactory = new DBFactory();

    public static DBCacheImpl getInstance() {
        if (sInstance == null) {
            synchronized (DBCacheImpl.class) {
                if (sInstance == null) {
                    sInstance = new DBCacheImpl();
                }
            }
        }
        return sInstance;
    }

    public DBQuery query(String hash) {
        return mFactory.query(hash);
    }

    public DBInsert insert(String hash) {
        return mFactory.insert(hash);
    }

    public DBUpdate update(String hash) {
        return mFactory.update(hash);
    }

    public DBDelete delete(String hash) {
        return mFactory.delete(hash);
    }
}
