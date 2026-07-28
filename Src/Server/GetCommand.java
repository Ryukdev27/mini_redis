package mini_redis.Src.Server;

import java.util.List;

public class GetCommand implements Command{
    @Override
    public String execute(List<String> arguments,RedisDatabase database, ClientSession session){
        if(arguments.size()!=1)
            return RespEncoder.error("wrong arguments for 'GET' command");
        RedisEntry entry=database.getEntry(arguments.get(0));
        if(entry==null)
            return RespEncoder.bulkString(null);
        if(entry.getType()!=RedisType.STRING)
            return RespEncoder.error("WRONGTYPE Operation against a key holding the wrong kind of value\"");
        return RespEncoder.bulkString(entry.getString());
    }
}