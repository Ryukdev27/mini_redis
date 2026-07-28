package mini_redis.Src.Server;

import java.util.List;

public class SetCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
         if(arguments.size()!=2)
            return RespEncoder.error("wrong arguments for 'SET' command");
        String key=arguments.get(0);
        String value=arguments.get(1);
        RedisEntry entry =new RedisEntry(RedisType.STRING,value,null); 
        database.set(key, entry);
        return RespEncoder.simpleString("OK");
    }
}
