import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;


/**
 * Selector用来管理一个或者多个Channel
 */
public class SelectorDemo {
    public static Logger log = Logger.getLogger(ScatterGather.class.toString());

    @Test
    public void first() throws IOException {
        /**
         * 使用Selector时，Channel必须是非阻塞模式，因此FileChannel不能使用Selector
         */
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("http://127.0.0.1", 9999));
        //ByteBuffer buf = ByteBuffer.allocate(48);
        //int bytesRead = socketChannel.read(buf);

        //将Channel注册到Selector上
        SelectionKey key = socketChannel.register(selector,SelectionKey.OP_READ);

        int interestSet = key.interestOps();

        log.info("interestSet:"+interestSet);

        socketChannel.close();
    }
}
