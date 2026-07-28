package mini_redis.Src.Server;

import java.util.LinkedList;
import java.util.List;

public class LPushCommand implements Command{
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=2)
            return RespEncoder.error("Wrong number of arguments for LPUSH");
        String key=arguments.get(0);
        RedisEntry entry=database.getEntry(key);
        LinkedList<String> list;
        if(entry==null) list= new LinkedList<>();
        else {
            if(entry.getType() != RedisType.LIST) {
                return RespEncoder.error("WRONGTYPE Operation against a key holding the wrong kind of value");
            }
            list = entry.getList();
        }
        for(int i = 1; i < arguments.size(); i++) {
            list.addFirst(arguments.get(i));
        }
        RedisEntry newEntry =new RedisEntry(RedisType.LIST,list,entry == null ? null :entry.getExpirationTime());
        database.set(key, newEntry);
        return RespEncoder.integer(list.size());    
    }
    
}
