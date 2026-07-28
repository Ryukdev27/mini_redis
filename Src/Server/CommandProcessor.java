package mini_redis.Src.Server;

import java.io.IOException;
import java.util.*;

public class CommandProcessor {

    private final Map<String, Command> commands = new HashMap<>();
    private final PersistenceManager persistenceManager;

    public CommandProcessor(
            RedisDatabase database,
            PersistenceManager persistenceManager
    ) {
        this.persistenceManager = persistenceManager;

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

        commands.put("AUTH", new AuthCommand("secret"));

        commands.put("LPUSH", new LPushCommand());
        commands.put("RPUSH", new RPushCommand());
        commands.put("LRANGE", new LRangeCommand());
        commands.put("LLEN", new LLenCommand());
        commands.put("LPOP", new LPopCommand());
        commands.put("RPOP", new RPopCommand());
        commands.put("SAVE",new SaveCommand(persistenceManager));
    }
    private String executeCommand(List<String> parts,RedisDatabase database,ClientSession session, boolean appendToAof) {
    if(parts == null || parts.isEmpty())
        return RespEncoder.error("empty command");
    String cmdName = parts.get(0).toUpperCase();
    Command cmd = commands.get(cmdName);
    if(cmd == null)
        return RespEncoder.error("unknown command");
    List<String> arguments = parts.subList(1, parts.size());
    String response = cmd.execute(arguments, database, session);
    if(appendToAof && isWriteCommand(cmdName)) {
        try {
            persistenceManager.append(String.join(" ", parts));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    return response;
}


public String process(List<String> parts,RedisDatabase database,ClientSession session){
    if(parts == null || parts.isEmpty())
        return RespEncoder.error("empty command");
    String cmdName = parts.get(0).toUpperCase();
    if(!session.isAuthenticated()){
        if(!cmdName.equals("AUTH")&& !cmdName.equals("PING")&& !cmdName.equals("EXIT")){
            return RespEncoder.error("NOAUTH Authentication Required");
        }
    }

    return executeCommand(parts, database,session, true);
}

    private boolean isWriteCommand(String command) {
        return switch(command) {
            case "SET","DELETE","INCR","DECR","LPUSH","RPUSH","LPOP","RPOP","EXPIRE","PERSIST","FLUSHDB"-> true;
            default -> false;};
    }
    public void replay(List<String> parts,RedisDatabase database){
        executeCommand(parts, database,null, false);
    }

}
