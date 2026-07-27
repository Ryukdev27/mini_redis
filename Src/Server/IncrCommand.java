package mini_redis.Src.Server;

import java.util.List;

public class IncrCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=1)
            return RespEncoder.error("wrong arguments for 'INCR' command");
        String key=arguments.get(0);
        int newValue=database.incr(key,1);
        return RespEncoder.integer(newValue);
}
    
}
