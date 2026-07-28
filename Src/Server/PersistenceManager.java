package mini_redis.Src.Server;

import java.io.IOException;

public interface PersistenceManager {
    public void save(RedisDatabase database) throws IOException;
    public void load(RedisDatabase database, CommandProcessor processor) throws IOException;
    public void append(String command) throws IOException;
}
