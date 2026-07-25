package mini_redis.Src.Server;
import java.util.List;
public class DeleteCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database){
        if(arguments.size()!=1)
            return "ERR wrong arguments";
        String key=arguments.get(0);
        boolean deleted=database.Delete(key);
        return RespEncoder.integer(deleted ? 1 : 0);

    }
}

