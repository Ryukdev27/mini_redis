package mini_redis.Src.Server;
import java.util.LinkedList;
import java.util.List;

public class LLenCommand implements Command {
    @Override
    public String execute(List<String> arguments,RedisDatabase database,ClientSession sessio) {
        if(arguments.size() != 1)
            return RespEncoder.error("ERR wrong number of arguments for 'llen' command");
        String key = arguments.get(0);
        RedisEntry entry = database.getEntry(key);
        // Key does not exist
        if(entry == null)
            return RespEncoder.integer(0);
        // Type check
        if(entry.getType() != RedisType.LIST)
            return RespEncoder.error("WRONGTYPE Operation against a key holding the wrong kind of value");
        LinkedList<String> list = entry.getList();
        return RespEncoder.integer(list.size());
    }
}
