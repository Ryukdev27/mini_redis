package mini_redis.Src.Server;
import java.util.List;
public class ExistsCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=1)
            return RespEncoder.error("wrong arguments for 'EXISTS' command");
        String key=arguments.get(0);
        Boolean exists=database.exists(key);
        return RespEncoder.integer(exists ? 1 : 0);

}
}
