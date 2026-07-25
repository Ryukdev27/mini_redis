package mini_redis.Src.Server;

import java.util.List;

public class Ping implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database){
        if(arguments.size()!=0)
            return "ERR wrong arguments";
        return RespEncoder.simpleString("PONG");
}
}
