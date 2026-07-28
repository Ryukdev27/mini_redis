package mini_redis.Src.Server;

public class Main {
    public static void main(String[] args) {
        RedisDatabase database = new RedisDatabase();
        PersistenceManager persistenceManager =new FilePersistenceManager("dump.rdb");
        try {
            persistenceManager.load(database);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RedisServer server =new RedisServer(database, persistenceManager);
        server.start();
    }
}
