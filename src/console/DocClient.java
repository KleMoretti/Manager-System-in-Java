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
        startReceiveThread(); // Start receiving messages
    }

    private void startReceiveThread() {
        executorService.submit(() -> {
            while (running.get()) {

            }
        });
    }

    private synchronized void handleServerResponse(Object response) {
        // Implement your logic to handle the server's response here
        System.out.println("Received from server: " + response);

        // Optionally notify listeners or update state
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


    public synchronized CompletableFuture<String> sendAndWaitForResponse(String message) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                sendMessage(message);
                Object response = ois.readObject(); // Block until a response is received
                return response.toString(); // Convert response to Stringreturn response;
            } catch (StreamCorruptedException e) {
                throw new RuntimeException("Stream corrupted: " + e.getMessage(), e);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
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

    public boolean isConnected() {
        return !socket.isClosed();
    }

    public void reconnect() throws IOException {
        close(); // Close existing connection first
        this.socket = new Socket("localhost", 9999); // Use actual host and port
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        startReceiveThread(); // Restart the receive thread
    }

    public static void main(String[] args) {
        try (DocClient client = new DocClient("localhost", 9999)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connected to server. Type commands:");
            String line;
            while ((line = reader.readLine()) != null && client.isConnected()) {
                client.sendAndWaitForResponse(line).thenAccept(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
