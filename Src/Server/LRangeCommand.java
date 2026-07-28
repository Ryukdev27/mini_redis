package mini_redis.Src.Server;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LRangeCommand implements Command {
    @Override
    public String execute(List<String> arguments,RedisDatabase database,ClientSession session) {
        if(arguments.size() != 3)
            return RespEncoder.error("ERR wrong number of arguments for 'lrange' command");
        String key = arguments.get(0);
        int start = Integer.parseInt(arguments.get(1));
        int stop = Integer.parseInt(arguments.get(2));
       RedisEntry entry = database.getEntry(key);
        if(entry == null)
            return RespEncoder.array(Collections.emptyList());
        if(entry.getType() != RedisType.LIST)
            return RespEncoder.error("WRONGTYPE Operation against a key holding the wrong kind of value");
        LinkedList<String> list = entry.getList();
        // Handle negative indexes
        if(start < 0)
            start = list.size() + start;

        if(stop < 0)
            stop = list.size() + stop;
        // Clamp indexes
        start = Math.max(start, 0);
        stop = Math.min(stop, list.size() - 1);
        if(start > stop)
            return RespEncoder.array(Collections.emptyList());
        List<String> result =list.subList(start, stop + 1);
        return RespEncoder.array(result);
    }
}

