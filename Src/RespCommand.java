package mini_redis.Src;

public class RespCommand {
    public static String command(String... args){
        StringBuilder builder = new StringBuilder();
        builder.append("*").append(args.length).append("\r\n");
        for(String arg : args){
         builder.append("$").append(arg.length()).append("\r\n");
            builder.append(arg).append("\r\n");
        }
        return builder.toString();
    }
}

