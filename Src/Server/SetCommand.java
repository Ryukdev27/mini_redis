package mini_redis.Src.Server;

import java.util.List;

public class SetCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database){
         if(arguments.size()!=2)
            return "ERR wrong arguments";
        String key=arguments.get(0);
        String value=arguments.get(1);
        database.Set(key, value);
        return RespEncoder.simpleString("OK");
    }
}
