package mini_redis.Src.Server;

import java.util.List;

public class ExpireCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database){
        if(arguments.size()!=2)
            return RespEncoder.error("wrong arguments for 'EXPIRE' command");
        String key=arguments.get(0);
        int seconds=Integer.parseInt(arguments.get(1));
        boolean success=database.expire(key, seconds);
        return RespEncoder.integer(success ? 1 : 0);
    }
}
