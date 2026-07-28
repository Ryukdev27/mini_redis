package mini_redis.Src.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        RedisDatabase database = new RedisDatabase();
        PersistenceManager persistenceManager =new AofPersistenceManager("appendonly.aof");
        CommandProcessor processor =new CommandProcessor(database, persistenceManager);
        try {
            persistenceManager.load(database, processor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RedisServer server =new RedisServer(database, persistenceManager);
        server.start();
    }
}
