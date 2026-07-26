package mini_redis.Src.Server;

import java.util.List;

public class PersistCommand implements Command {
    @Override
    public String execute(List<String> arguments, RedisDatabase database) {
        if (arguments.size() != 1)
            return RespEncoder.error("wrong arguments for 'PERSIST' command");
        String key = arguments.get(0);
        boolean persisted = database.persist(key);
        return RespEncoder.integer(persisted ? 1 : 0);
    }
    
}
