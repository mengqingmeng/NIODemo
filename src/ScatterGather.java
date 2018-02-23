import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 分发器收集器概念
 * scatter:分发器，是一个读的操作，从Channel中读数据到一个或者多个Buffer中
 * gether：收集器，是一个写的操作，将一个或者多个Buffer中的数据写入Channel中
 */
public class ScatterGather {
    public static Logger log = Logger.getLogger(ScatterGather.class.toString());

    @Test
    public void Scatter(){
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = { header, body };
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("src/data/nio-data-for-write.txt","rw");
        } catch (FileNotFoundException e) {
            log.info("文件未找到："+e.getMessage());
        }

        if (randomAccessFile !=null) {
            FileChannel fileChannel = randomAccessFile.getChannel();
            try {
                fileChannel.read(bufferArray);
            } catch (IOException e) {
                log.info("channel读取异常："+e.getMessage());
            }
        }

        try {
            randomAccessFile.close();
        } catch (IOException e) {
            log.log(Level.INFO,"关闭通道异常："+e.getMessage());
        }

        log.info("scatter finish");

    }

    @Test
    public void Gather(){
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);

        //write data into buffers
        header.put("hello".getBytes());
        try {
            body.put("你好".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ByteBuffer[] bufferArray = { header, body };

        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("src/data/nio-data-for-write.txt","rw");
        } catch (FileNotFoundException e) {
            log.info("文件未找到："+e.getMessage());
        }
        //要调用flip 将Buffer写模式改为读的模式，然后从Buffer中读取数据写入Channel中
        header.flip();
        body.flip();
        if (randomAccessFile !=null) {
            FileChannel fileChannel = randomAccessFile.getChannel();
            try {
                fileChannel.write(bufferArray);
            } catch (IOException e) {
                log.info("channel读取异常："+e.getMessage());
            }
        }

        try {
            randomAccessFile.close();
        } catch (IOException e) {
            log.log(Level.INFO,"关闭通道异常："+e.getMessage());
        }

        log.info("gather finish");
    }
}
