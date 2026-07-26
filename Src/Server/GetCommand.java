package mini_redis.Src.Server;

import java.util.List;

public class GetCommand implements Command{
    @Override
    public String execute(List<String> arguments,RedisDatabase database){
        if(arguments.size()!=1)
            return RespEncoder.error("wrong arguments for 'GET' command");
        String value=database.get(arguments.get(0));
        if(value==null)
            return RespEncoder.bulkString(null);
        return RespEncoder.bulkString(value);
    }
}