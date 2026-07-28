package mini_redis.Src.Server;

import java.util.*;

public class CommandProcessor {
    private final Map<String, Command> commands = new HashMap<>();
    public CommandProcessor() {
        commands.put("PING", new Ping());
        commands.put("GET", new GetCommand());
        commands.put("SET", new SetCommand());
        commands.put("EXISTS", new ExistsCommand());
        commands.put("DELETE", new DeleteCommand());
        commands.put("KEYS", new KeysCommand());
        commands.put("EXPIRE", new ExpireCommand());
        commands.put("INCR", new IncrCommand());
        commands.put("DECR", new DecrCommand());
        commands.put("PERSIST", new PersistCommand());
        commands.put("TTL", new TTLCommand());
        commands.put("DBSIZE", new DBSizeCommand());
        commands.put("FLUSHDB", new FlushDBCommand());
        commands.put("AUTH",new AuthCommand("secret"));
        commands.put("LPUSH", new LPushCommand());
        commands.put("RPUSH", new RPushCommand());
        commands.put("LRANGE", new LRangeCommand());
        commands.put("LLEN", new LLenCommand());
        commands.put("LPOP", new LPopCommand());
        commands.put("RPOP", new RPopCommand());
    }
    public String process(List<String> parts,RedisDatabase database,ClientSession session){
        if(parts == null || parts.isEmpty()) {
            return RespEncoder.error("empty command");
        }
        String cmdName =parts.get(0).toUpperCase();
        if(!session.isAuthenticated()){
            if(!cmdName.equals("AUTH") && !cmdName.equals("PING") && !cmdName.equals("EXIT")){
            return RespEncoder.error("NOAUTH, Authentication Required before starting");}
        }
        List<String> arguments =parts.subList(1, parts.size());
        System.out.println("COMMAND RECEIVED = " + cmdName);
        Command cmd =commands.get(cmdName);
        if(cmd == null) {
            return RespEncoder.error("unknown command");
        }
        return cmd.execute(arguments,database,session);
    }
}
