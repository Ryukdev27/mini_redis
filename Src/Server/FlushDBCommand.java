package mini_redis.Src.Server;

public class FlushDBCommand implements Command{
    @Override
    public String execute(java.util.List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=0)
            return RespEncoder.error("wrong arguments for 'FLUSHDB' command");
        database.flushDB();
        return RespEncoder.simpleString("OK");
}
}
