package mini_redis.Src.Server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;

public class ClientSession {

    private final SocketChannel client;

    private final ByteBuffer readBuffer;

    private final Queue<ByteBuffer> writeQueue;

    private final RespParser parser;


    public ClientSession(SocketChannel client) {

        this.client = client;

        this.readBuffer =
                ByteBuffer.allocate(4096);

        this.writeQueue =
                new LinkedList<>();

        this.parser =
                new RespParser();
    }


    public SocketChannel getClient() {
        return client;
    }


    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }


    public Queue<ByteBuffer> getWriteQueue() {
        return writeQueue;
    }


    public RespParser getParser() {
        return parser;
    }
}
