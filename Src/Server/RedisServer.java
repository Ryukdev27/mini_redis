package mini_redis.Src.Server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class RedisServer {

    private static final int PORT = 7379;


    private final RedisDatabase database;

    private final CommandProcessor processor;



    public RedisServer() {

        database = new RedisDatabase();

        processor = new CommandProcessor();
    }




    public void start() {


        try(
            ServerSocketChannel server =
                    ServerSocketChannel.open();

            Selector selector =
                    Selector.open()

        ) {


            server.bind(
                    new InetSocketAddress(PORT)
            );


            server.configureBlocking(false);



            server.register(
                    selector,
                    SelectionKey.OP_ACCEPT
            );


            System.out.println(
                    "Redis server started on port " + PORT
            );



            while(true) {


                selector.select();



                Set<SelectionKey> keys =
                        selector.selectedKeys();


                Iterator<SelectionKey> iterator =
                        keys.iterator();



                while(iterator.hasNext()) {


                    SelectionKey key =
                            iterator.next();


                    iterator.remove();



                    if(key.isAcceptable()) {

                        acceptClient(
                                server,
                                selector
                        );

                    }



                    if(key.isReadable()) {

                        readClient(key);

                    }



                    if(key.isWritable()) {

                        writeClient(key);

                    }

                }
            }


        }
        catch(Exception e) {

            e.printStackTrace();

        }

    }






    private void acceptClient(
            ServerSocketChannel server,
            Selector selector
    ) throws Exception {



        SocketChannel client =
                server.accept();



        if(client == null)
            return;



        client.configureBlocking(false);



        ClientSession session =
                new ClientSession(client);



        client.register(
                selector,
                SelectionKey.OP_READ,
                session
        );


        System.out.println(
                "Client connected"
        );
    }







    private void readClient(
            SelectionKey key
    ) {


        try {


            ClientSession session =
                    (ClientSession) key.attachment();



            SocketChannel client =
                    session.getClient();



            ByteBuffer buffer =
                    session.getReadBuffer();




            int bytes =
                    client.read(buffer);



            if(bytes == -1) {


                System.out.println(
                        "Client disconnected"
                );


                key.cancel();

                client.close();

                return;
            }



            if(bytes > 0) {


    while(true) {


        List<String> command =
                session.getParser()
                       .readCommand(buffer);



        if(command == null) {

            break;

        }



        System.out.println(
                "Received: " + command
        );



        String response =
                processor.process(
                        command,
                        database
                );



        ByteBuffer responseBuffer =
                ByteBuffer.wrap(
                        response.getBytes()
                );



        session.getWriteQueue()
               .add(responseBuffer);



        key.interestOps(
                key.interestOps()
                |
                SelectionKey.OP_WRITE
        );
    }
}

            


        }
        catch(Exception e) {

            e.printStackTrace();

            key.cancel();

        }
    }








    private void writeClient(
            SelectionKey key
    ) {


        try {


            ClientSession session =
                    (ClientSession) key.attachment();



            SocketChannel client =
                    session.getClient();



            Queue<ByteBuffer> queue =
                    session.getWriteQueue();




            while(!queue.isEmpty()) {


                ByteBuffer buffer =
                        queue.peek();



                client.write(buffer);



                // Socket buffer full
                if(buffer.hasRemaining()) {

                    return;
                }



                // Response completely sent
                queue.poll();

            }




            // No more data to write
            key.interestOps(
                    key.interestOps()
                    &
                    ~SelectionKey.OP_WRITE
            );



        }
        catch(Exception e) {


            e.printStackTrace();

            key.cancel();

        }

    }





    public static void main(String[] args) {

        new RedisServer().start();

    }
}
