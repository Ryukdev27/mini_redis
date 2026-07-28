package mini_redis.Src.Server;

import java.util.LinkedList;
import java.util.List;

public class RPopCommand implements Command {
    @Override
    public String execute(List<String> arguments,RedisDatabase database,ClientSession session) {
        if(arguments.size() != 1)
            return RespEncoder.error("ERR wrong number of arguments for 'rpop' command");
        String key = arguments.get(0);
        RedisEntry entry = database.getEntry(key);
        // Key does not exist
        if(entry == null)
            return RespEncoder.bulkString(null);
        // Type check
        if(entry.getType() != RedisType.LIST)
            return RespEncoder.error("WRONGTYPE Operation against a key holding the wrong kind of value");
        LinkedList<String> list = entry.getList();
        // Empty list
        if(list.isEmpty())
            return RespEncoder.bulkString(null);
        String value = list.removeLast();
        // Redis deletes key when list becomes empty
        if(list.isEmpty()) {
            database.delete(key);
        }
        return RespEncoder.bulkString(value);
    }
}

