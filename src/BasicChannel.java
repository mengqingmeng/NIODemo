import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicChannel {
    public static Logger log = Logger.getLogger(BasicChannel.class.toString());

    public static  void main(String[] args){
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("src/data/nio-data-for-read.txt","rw");
        } catch (FileNotFoundException e) {
            log.log(Level.INFO,"文件未找到："+e.getMessage());
        }

        if (randomAccessFile !=null){
            FileChannel fileChannel = randomAccessFile.getChannel();
            //初始化ByteBuffer，分配大小，Buffer包装了内存块
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //byteBuffer.put((byte) 128);
            int bytesRead=-1;
            try {
                //1.从channel中读取，写入到ByteBuffer中
                bytesRead = fileChannel.read(byteBuffer);
            } catch (IOException e) {
                log.log(Level.INFO,"channel读取异常："+e.getMessage());
            }

            while (bytesRead!=-1){
                System.out.println("Read " + bytesRead);
                //2.切换模式，将写模式转为读模式
                byteBuffer.flip();
                //3.从ByteBuffer中读取
                while(byteBuffer.hasRemaining()){
                    System.out.print((char) byteBuffer.get());
                }

                //4.清空Buffer，然后再写
                byteBuffer.clear();
                try {
                    bytesRead = fileChannel.read(byteBuffer);
                } catch (IOException e) {
                    log.log(Level.INFO,"channel读取异常："+e.getMessage());
                }

            }

            try {
                randomAccessFile.close();
            } catch (IOException e) {
                log.log(Level.INFO,"关闭通道异常："+e.getMessage());
            }

        }
    }
}
