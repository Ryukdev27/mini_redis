package mini_redis.Src.Server;

import java.util.List;

public class Ping implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=0)
            return RespEncoder.error("wrong arguments for 'PING' command");
        return RespEncoder.simpleString("PONG");
}
}
