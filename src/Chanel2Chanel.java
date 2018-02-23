import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class Chanel2Chanel {

    @Test
    public void transferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("src/data/nio-data-for-read.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("src/data/nio-data-for-write.txt", "rw");
        FileChannel  toChannel = toFile.getChannel();

        long position = 0;
        long count    = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);
    }
}
