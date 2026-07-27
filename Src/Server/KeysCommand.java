package mini_redis.Src.Server;
import java.util.List;
public class KeysCommand implements Command{
     @Override
     public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=1)
            return RespEncoder.error("wrong arguments for 'KEYS' command");
        String pattern= arguments.get(0);
        List<String> keys=database.keys(pattern);
        return RespEncoder.array(keys);
     }    
}
