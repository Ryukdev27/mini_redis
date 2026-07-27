package mini_redis.Src.Server;

import java.util.List;

public interface Command {
    String execute(List<String> arguments, RedisDatabase database, ClientSession session);
}
