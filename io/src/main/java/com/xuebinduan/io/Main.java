package com.xuebinduan.io;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/**
 * 实现了Closeable接口的都可以放在try()的括号里面，然后它们会被自动关闭。（这是从java7开始的）。
 *
 * BufferedOutputStream
 */
public class Main {
    @Test
    public void io1() {
        try (OutputStream outputStream = new FileOutputStream("./text.txt")) {
            outputStream.write('a');
            outputStream.write('b');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void io2() {
        try (InputStream inputStream = new FileInputStream("./text.txt")) {
            System.out.println((char) inputStream.read());
            System.out.println((char) inputStream.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //在字节流上插一根管子，出来的就不是字节流了，是字符流
    @Test
    public void io3() {
        try (InputStream inputStream = new FileInputStream("./text.txt");
             Reader reader = new InputStreamReader(inputStream);
             //我们在字符流上再插一根管子，就可以整行的读取了。另外Buffer主要是为了缓冲，整行读取是顺带提供的功能
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            System.out.println(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //有缓冲，write不会立即写入，有三种情况会写入：缓冲区满了、手动flush了、close了（close里面其实也是调用的flush）。
    @Test
    public void io4() {
        try (OutputStream outputStream = new FileOutputStream("./text.txt");
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            bufferedOutputStream.write('c');
            bufferedOutputStream.write('d');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void io5() {
        //TODO 当然我们可以套一个Buffer来减少与文件交互的次数。在如今，我们没有必要自己写复制文件代码了。
        try (InputStream inputStream = new FileInputStream("./text.txt");
             OutputStream outputStream = new FileOutputStream("./newtext.txt")) {
            //TODO 我只是想打破1024的印象，才写的30000，但不建议这样，因为这样浪费很多内存空间
            byte[] data = new byte[30000];
            int read;
            while ((read = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void io6() {
        try (//建一个TCP连接
            Socket socket = new Socket("baidu.com",80);
            //HTTP是纯文本的，所以我们应该针对字符而不是字节。为了方便，我们加一个Buffer。
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            writer.write("GET / HTTP/1.1\n"+
                    "Host:www.baidu.com\n\n"); //连续两个换行表示内容结束了
            writer.flush();
            String message;
            while((message = reader.readLine())!=null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void io7(){
        //可以在浏览器中输入 127.0.0.1 查看效果。这不是while循环，所以它接受一个请求就结束了。
        try (ServerSocket serverSocket = new ServerSocket(80);
            // 这是一个阻塞方法，一直等别人连我
            Socket socket = serverSocket.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            writer.write("HTTP/1.1 200 OK\n" +
                    "Date: Thu, 27 May 2021 04:11:51 GMT\n" +
                    "Server: Apache\n" +
                    "Last-Modified: Tue, 12 Jan 2010 13:48:00 GMT\n" +
                    "ETag: \"51-47cf7e6ee8400\"\n" +
                    "Accept-Ranges: bytes\n" +
                    "Content-Length: 81\n" +
                    "Cache-Control: max-age=86400\n" +
                    "Expires: Fri, 28 May 2021 04:11:51 GMT\n" +
                    "Connection: Keep-Alive\n" +
                    "Content-Type: text/html\n" +
                    "\n" +
                    "<html>\n" +
                    "<meta http-equiv=\"refresh\" content=\"0;url=http://www.baidu.com/\">\n" +
                    "</html>\n\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //todo NIO用的很少
    /*
     * NIO的Buffer可以被操作，是强制使用的，它不太好用；
     * NIO支持非阻塞式，默认是阻塞式，但只有网络交互支持非阻塞，文件交互不支持
     */

    //NIO(New IO)
    @Test
    public void io8(){
        try {
            RandomAccessFile file = new RandomAccessFile("./text.txt","r");
            FileChannel channel = file.getChannel();
            //ByteBuffer的模型，capacity、position、limit
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);
//            byteBuffer.limit(byteBuffer.position());
//            byteBuffer.position(0);
            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //非阻塞NIO
    //telnet localhost 80
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void io9(){
        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(80));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while(true) {
                selector.select();
                for(SelectionKey key:selector.selectedKeys()){
                    if(key.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while(socketChannel.read(byteBuffer)!=-1) {
                            byteBuffer.flip();
                            socketChannel.write(byteBuffer);
                            byteBuffer.clear();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //OKIO:Source、Sink
    @Test
    public void test10(){
        try(BufferedSource source = Okio.buffer(Okio.source(new File("./text.txt")))){
            System.out.println(source.readUtf8Line());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
