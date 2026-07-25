package mini_redis.Src.Server;

import java.util.HashMap;
import java.util.Map;

class RedisDatabase{
    private final Map<String, String> dataStore;
    public RedisDatabase(){
        dataStore= new HashMap<>();
    }
    public void Set(String key,String value){
        dataStore.put(key,value);
    }
    public String Get(String key){
        return dataStore.get(key);
    }
    public boolean Delete(String key){
        return dataStore.remove(key)!=null;
    }
    public boolean Exists(String key){
        return dataStore.containsKey(key);
    }
}