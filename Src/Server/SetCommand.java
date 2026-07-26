package mini_redis.Src.Server;

import java.util.List;

public class SetCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database){
         if(arguments.size()!=2)
            return RespEncoder.error("wrong arguments for 'SET' command");
        String key=arguments.get(0);
        String value=arguments.get(1);
        database.set(key, value);
        return RespEncoder.simpleString("OK");
    }
}
