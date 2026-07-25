package mini_redis.Src.Server;
import java.util.List;
public class ExistsCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database){
        if(arguments.size()!=1)
            return "ERR wrong arguments";
        String key=arguments.get(0);
        Boolean exists=database.Exists(key);
        return RespEncoder.integer(exists ? 1 : 0);

}
}
