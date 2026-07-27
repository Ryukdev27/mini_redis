package mini_redis.Src.Server;

public class DBSizeCommand implements Command{
    @Override
    public String execute(java.util.List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=0)
            return RespEncoder.error("wrong arguments for 'DBSIZE' command");
        int size=database.dbSize();
        return RespEncoder.integer(size);
    }
}
