package console;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class DocClient implements Serializable , AutoCloseable {

    private Socket socket;
    private  ObjectOutputStream oos ;
    private  ObjectInputStream ois ;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ReentrantLock lock = new ReentrantLock();
    private final AtomicBoolean running = new AtomicBoolean(true);
    private volatile Thread receiveThread;

    public DocClient(String host, int port) throws IOException {
        connect(host, port);
    }

    private void connect(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }


    public synchronized void sendMessage(String message) throws IOException {
        lock.lock();
        try {
            oos.writeObject(message);
            oos.flush();
        } finally {
            lock.unlock();
        }
    }

    public  void sendMessage(Object message) throws IOException {
        lock.lock();
        try {
            if (message instanceof byte[]) {
                // 如果传递的是字节数组（文件内容），直接写入流
                oos.write((byte[]) message);
            } else {
                oos.writeObject(message);
            }
        } finally {
            lock.unlock();
        }
    }

    public void sendBytes(byte[] data, int length) throws IOException {
        lock.lock();
        try {
            oos.write(data, 0, length); // 直接发送字节流
            oos.flush();
        } finally {
            lock.unlock();
        }
    }


    public CompletableFuture<Object> receiveMessage() {
        return CompletableFuture.supplyAsync(() -> {
            lock.lock();
            try {
                System.out.println("Attempting to read object...");
                return ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("I/O error occurred: " + e.getMessage());
                throw new RuntimeException("I/O error during object deserialization", e);
            }finally {
                lock.unlock();
            }
        });
    }


    @Override
    public synchronized void close() {
        running.set(false);
        executorService.shutdownNow();
        try {
            if (ois != null) {
                ois.close();
            }
            if (oos != null) {
                oos.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (receiveThread != null && !receiveThread.isInterrupted()) {
                receiveThread.interrupt();
            }
        }
    }


}
