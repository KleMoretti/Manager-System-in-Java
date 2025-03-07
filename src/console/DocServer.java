package console;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * The DocServer class represents a multi-threaded server that handles various client requests.
 */

public class DocServer {
    private static final int PORT = 9999;
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private int counter = 1;

    /**
     * The main method to start the server.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            System.out.println("Waiting for connection...");
            while (true) {
                Socket connection = server.accept();
                System.out.println("Connection received from: " + connection.getInetAddress().getHostName());

                // Hand off the connection to a new thread
                threadPool.execute(new ClientHandler(connection));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    /**
     * The ClientHandler class handles client requests in a separate thread.
     */
    private static class ClientHandler implements Runnable {
        private final Socket socket;

        /**
         * Constructor for ClientHandler.
         *
         * @param socket The client socket.
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

                while (!socket.isClosed()) {
                    socket.setSoTimeout(5000);
                    try {
                        String message = (String) ois.readObject();
                        if (message == null || message.isEmpty()) {
                            continue;
                        }
                        processMessage(message, ois, oos);
                    } catch (SocketTimeoutException e) {
                        continue;
                    } catch (ClassNotFoundException e) {
                        System.err.println("Received invalid object from client");
                        break;
                    } catch (EOFException e) {
                        break;
                    } catch (IOException e) {
                        System.err.println("IO Exception: " + e.getMessage());
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Processes the received message and performs the corresponding action.
         *
         * @param message The received message.
         * @param ois     The ObjectInputStream to read from.
         * @param oos     The ObjectOutputStream to write to.
         * @throws IOException If an I/O error occurs.
         */
        private static void processMessage(String message, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
            System.out.println("服务器收到的信息: " + message);
            String response = "UNKNOWN_COMMAND";
            try {
                if (message.startsWith("CLIENT>>> USER_LOGIN")) {
                    handleUserLogin(ois, oos, message);
                } else if (message.startsWith("CLIENT>>> ADD_USER")) {
                    handleAddUser(ois, oos, message);
                } else if (message.startsWith("CLIENT>>> DELETE_USER")) {
                    handleDeleteUser(ois, oos, message);
                } else if (message.startsWith("CLIENT>>> UPDATE_USER")) {
                    handleUpdateUser(ois, oos, message);
                } else if (message.startsWith("CLIENT>>> LIST_USER")) {
                    handleListUser(ois, oos);
                } else if (message.startsWith("CLIENT>>> DOWNLOAD_FILE")) {
                    handleDownloadFile(ois, oos, message);
                } else if (message.startsWith("CLIENT>>> LIST_DOC")) {
                    handleListDoc(ois, oos);
                } else if (message.startsWith("CLIENT>>> FIlE_DESCRIPTION")) {
                    handleFileDiscription(ois, oos, message);
                } else if (message.startsWith("CLIENT>>> FILE_UPLOAD")) {
                    handleFileUpload(ois, oos, message);
                } else if (message.startsWith("CLIENT>>> EXIT")) {
                    handleExit(ois, oos, message);
                } else {
                    System.err.println("Invalid message format: " + message);
                    oos.writeObject(response);
                    oos.flush();
                }
            } catch (Exception e) {
                System.err.println("Error processing message: " + e.getMessage());
                oos.writeObject("ERROR_OCCURRED");
                oos.flush();
            }
        }


        private static void handleExit(ObjectInputStream ois, ObjectOutputStream oos, String message) {
            try {
                oos.writeObject("EXIT_SUCCESS");
                oos.flush();
                System.out.println("Exit success");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static boolean handleFileUpload(ObjectInputStream ois, ObjectOutputStream oos, String message) throws IOException {
            String[] parts = message.split(" ");
            if (parts.length < 7) {
                try {
                    oos.writeObject("UPDATE_FAILED");
                    oos.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Invalid message format: " + message);
                return false;
            }
            String Id = parts[2];
            String creator = parts[3];
            Timestamp createTime = Timestamp.valueOf(parts[4] + " " + parts[5]);
            String description = parts[6];

            String filename;
            try {
                filename = ois.readObject().toString();
            } catch (ClassNotFoundException e) {
                throw new IOException("Error reading filename", e);
            }

            try {
                DataProcessing.init();
                Doc doc = DataProcessing.searchDoc(Id);
                String uploadpath = "D:\\@Java\\Object-oriented and multithreaded comprehensive experiment\\Manager System\\uploadfile\\";
                if (doc != null) {
                    oos.writeObject("ID_ALREADY_EXISTS");
                    System.out.println("Document with the same ID exists.");
                    oos.flush();
                    return false;
                } else {
                    oos.writeObject("NONE_SAME_ID");
                    oos.flush();
                }

                try (BufferedOutputStream targetFile = new BufferedOutputStream(new FileOutputStream(uploadpath + filename))) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;

                    while ((bytesRead = ois.read(buffer)) != -1) {
                        String checkEnd = new String(buffer, 0, bytesRead).trim();
                        if (checkEnd.endsWith("FILE_UPLOAD_END")) {
                            break;
                        }
                        targetFile.write(buffer, 0, bytesRead);
                    }

                    targetFile.flush();

                    DataProcessing.insertDoc(Id, creator, createTime, description, filename);
                    oos.writeObject("UPLOAD_SUCCESS");
                    System.out.println("Update File success");
                    oos.flush();
                    return true;
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                try {
                    oos.writeObject("UPDATE_FAILED");
                    oos.flush();
                    return false;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                try {
                    oos.writeObject("UPDATE_FAILED");
                    oos.flush();
                    return false;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }

        private static boolean handleFileDiscription(ObjectInputStream ois, ObjectOutputStream oos, String message) {
            String[] parts = message.split(" ");
            if (parts.length < 4) {
                System.out.println("Invalid message format: " + message);
                return false;
            }

            String Id = parts[2];
            StringBuilder descriptionBuilder = new StringBuilder();
            for (int i = 3; i < parts.length; i++) {
                descriptionBuilder.append(parts[i]).append(" ");
            }
            String description = descriptionBuilder.toString().trim();

            System.out.println("Id: " + Id + " File description: " + description);
            try {
                oos.writeObject("FILE_DESCRIPTION_SUCCESS");
                oos.flush();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static boolean handleListDoc(ObjectInputStream ois, ObjectOutputStream oos) {
            try {
                DataProcessing.init();
                ResultSet rs = DataProcessing.listDoc();
                ArrayList<String[]> dataList = new ArrayList<>();
                int columnCount = rs.getMetaData().getColumnCount();
                String[] columnNames = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = rs.getMetaData().getColumnName(i);
                }
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getString(i);
                    }
                    dataList.add(row);
                }
                ResultSetData resultSetData = new ResultSetData(columnNames, dataList);
                oos.writeObject("LIST_DOC_SUCCESS");
                oos.writeObject(resultSetData);
                System.out.println("List doc success");
                oos.flush();
                DataProcessing.disconnectFromDataBase();
                return true;
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                try {
                    oos.writeObject("LIST_FAILED");
                    System.out.println("List doc failed");
                    oos.flush();
                    return false;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        }

        private static void handleAddUser(ObjectInputStream ois, ObjectOutputStream oos, String message) throws IOException {
            String[] parts = message.split(" ");
            if (parts.length < 5) {
                System.err.println("Invalid message format.");
                oos.writeObject("ADD_FAILED");
                oos.flush();
                return;
            }
            String userName = parts[2];
            String password = parts[3];
            String role = parts[4];
            DataProcessing.init();
            try {
                if (DataProcessing.insertUser(userName, password, role)) {
                    oos.writeObject("ADD_SUCCESS");
                    System.out.println("Add user success: " + userName + " " + role);
                    oos.flush();
                } else {
                    oos.writeObject("ADD_FAILED");
                    System.err.println("Add user failed: " + userName + " " + role);
                    oos.flush();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                oos.writeObject("ADD_FAILED");
                System.err.println("Add user failed: " + userName + " " + role);
                oos.flush();
            }
            DataProcessing.disconnectFromDataBase();
        }


        private static boolean handleUserLogin(ObjectInputStream ois, ObjectOutputStream oos, String message) throws IOException {
            if (message == null || message.isEmpty()) {
                return false;
            }
            String[] parts = message.split(" ");
            if (parts.length < 4) {
                System.err.println("Invalid message format.");
                oos.writeObject("LOGIN_FAILED");
                oos.flush();
                return false;
            }
            String userName = parts[2];
            String password = parts[3];

            try {
                DataProcessing.init();
                boolean isSuccess = DataProcessing.searchUser(userName, password) != null;
                oos.writeObject(isSuccess ? "LOGIN_SUCCESS" : "LOGIN_FAILED");
                oos.writeObject(DataProcessing.searchUser(userName, password));
                System.out.println("Login success: " + userName);
                oos.flush();
                DataProcessing.disconnectFromDataBase();
                return isSuccess;
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                try {
                    oos.writeObject("LOGIN_FAILED");
                    oos.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        }

        private static void handleDeleteUser(ObjectInputStream ois, ObjectOutputStream oos, String message) throws IOException {
            String[] parts = message.split(" ");
            if (parts.length < 3) {
                System.err.println("Invalid message format.");
                oos.writeObject("DELETE_FAILED");
                oos.flush();
                return;
            }
            String userName = parts[2];
            DataProcessing.init();

            try {
                DataProcessing.deleteUser(userName);
                oos.writeObject("DELETE_SUCCESS");
                System.out.println("Delete user success: " + userName);
                oos.flush();
            } catch (SQLException e) {
                e.printStackTrace();
                oos.writeObject("DELETE_FAILED");
                System.err.println("Delete user failed: " + userName);
                oos.flush();
            }
            DataProcessing.disconnectFromDataBase();
        }

        private static void handleUpdateUser(ObjectInputStream ois, ObjectOutputStream oos, String message) throws IOException {
            String[] parts = message.split(" ");
            if (parts.length < 5) {
                System.err.println("Invalid message format.");
                oos.writeObject("UPDATE_FAILED");
                oos.flush();
                return;
            }
            String userName = parts[2];
            String newPassword = parts[3];
            String role = parts[4];
            DataProcessing.init();
            try {
                boolean isSuccess = DataProcessing.updateUser(userName, newPassword, role);
                if (!isSuccess) {
                    oos.writeObject("UPDATE_FAILED");
                    System.out.println("Update user failed.");
                    oos.flush();
                    return;
                } else {
                    oos.writeObject("UPDATE_SUCCESS");
                    System.out.println("Update user success: " + userName);
                    oos.flush();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                oos.writeObject("UPDATE_FAILED");
                System.err.println("Update user failed: " + userName);
                oos.flush();
            }
            DataProcessing.disconnectFromDataBase();
        }

        private static void handleListUser(ObjectInputStream ois, ObjectOutputStream oos) {
            try {
                DataProcessing.init();
                ResultSet rs = DataProcessing.listUser();
                ArrayList<String[]> dataList = new ArrayList<>();
                int columnCount = rs.getMetaData().getColumnCount();
                String[] columnNames = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = rs.getMetaData().getColumnName(i);
                }
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getString(i);
                    }
                    dataList.add(row);
                }
                ResultSetData resultSetData = new ResultSetData(columnNames, dataList);
                oos.writeObject("LIST_USER_SUCCESS");
                oos.writeObject(resultSetData);
                System.out.println("List user success");
                oos.flush();
                DataProcessing.disconnectFromDataBase();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                try {
                    oos.writeObject("LIST_USER_FAILED");
                    System.err.println("List user failed");
                    oos.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

        private static boolean handleDownloadFile(InputStream inputStream, ObjectOutputStream oos, String message) throws IOException {
            String[] parts = message.split(" ");
            String Id = parts[2];
            if (parts.length < 3) {
                System.err.println("Invalid message format.");
                oos.writeObject("DOWNLOAD_FILE_FAILURE");
                oos.flush();
                return false;
            }
            try {
                DataProcessing.init();
                Doc doc = DataProcessing.searchDoc(Id);
                if (doc != null) {
                    System.out.println("Download file:" + doc.getFilename());
                    oos.writeObject(doc.getFilename());
                    oos.flush();
                    String uploadpath = "D:\\@Java\\Object-oriented and multithreaded comprehensive experiment\\Manager System\\uploadfile\\";

                    File tempFile = new File(uploadpath + doc.getFilename());
                    if(!tempFile.exists()){
                        oos.writeObject("DOWNLOAD_FILE_FAILURE");
                        oos.flush();
                        return false;
                    }

                    try (FileInputStream fis = new FileInputStream(tempFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            oos.writeObject(Arrays.copyOf(buffer, bytesRead));
                            oos.flush();
                        }
                        oos.writeObject("DOWNLOAD_FILE_END");
                        oos.flush();
                    }

                    oos.writeObject("DOWNLOAD_FILE_SUCCESS");
                    oos.flush();
                    return true;
                } else {
                    oos.writeObject("DOWNLOAD_FILE_FAILURE");
                    oos.flush();
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
