package mini_redis.Src.Server;
import java.util.List;
public class DeleteCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=1)
            return RespEncoder.error("wrong arguments for 'DEL' command");
        String key=arguments.get(0);
        boolean deleted=database.delete(key);
        return RespEncoder.integer(deleted ? 1 : 0);

    }
}

