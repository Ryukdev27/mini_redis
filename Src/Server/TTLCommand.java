package mini_redis.Src.Server;

import java.util.List;

public class TTLCommand implements Command {
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=1)
            return RespEncoder.error("wrong arguments for 'TTL' command");
        String key=arguments.get(0);
        int ttl=database.ttl(key);
        return RespEncoder.integer(ttl);
    }
    
}
