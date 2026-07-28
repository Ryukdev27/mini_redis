package mini_redis.Src.Server;
import java.util.LinkedList;
import java.util.List;

public class RPushCommand implements Command {
    @Override
    public String execute(List<String> arguments,RedisDatabase database,ClientSession session) {
        if(arguments.size() < 2)
            return RespEncoder.error("ERR wrong number of arguments for 'rpush' command");
        String key = arguments.get(0);
        RedisEntry entry = database.getEntry(key);
        LinkedList<String> list;
        if(entry == null) {
            list = new LinkedList<>();
            entry = new RedisEntry(RedisType.LIST,list,null);

        } 
        else {
            if(entry.getType() != RedisType.LIST) {
                return RespEncoder.error("WRONGTYPE Operation against a key holding the wrong kind of value");
            }
            list = entry.getList();
        }
        for(int i = 1; i < arguments.size(); i++) {
            list.addLast(arguments.get(i));
        }
        database.set(key, entry);
        return RespEncoder.integer(list.size());
    }
}
