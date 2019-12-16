package ouc.cs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Iperfer class
 * @author qinhao
 * @date 2019/12/14
 * IDEA Community 2019.2.4 + JDK 1.8
 */
public class Iperfer {

    public static void main(String[] args) throws IOException {

        // java Iperfer -c -h <server hostname> -p <server port> -t <time>
        // java Iperfer -s -p <listen port>

        //0 is client mode and 1 is server mode
        int mode = 0;
        // shortest length of args is 3, longest length is 7
        int minLength = 3, maxLength = 7;
        // -c option is client mode, -s is server mode
        String clientOption = "-c", serverOption = "-s";
        String hOption = "-h", pOption = "-p", tOption = "-t";
        // min port number is 1024, max is 65535
        int minPort = 1024, maxPort = 65535;
        // to avoid using magic numbers, I am so difficult.
        int argsZero = 0, argsOne = 1, argsTwo = 2, argsThree = 3, argsFour =
                4,argsFive = 5, argsSix = 6;
        int zero = 0;

        if (args.length < minLength || args.length > maxLength) {
            System.err.println("Error: missing or additional arguments");
            System.exit(1);
        } else if (args[argsZero].equals(clientOption)) {
            // client mode
            if (!(args[argsOne].equals(hOption)) || !(args[argsThree]
                    .equals(pOption)) || !(args[argsFive].equals(tOption))) {
                System.err.println("Error: invalid input");
                System.exit(1);
            }
            if (Integer.parseInt(args[argsFour]) < minPort ||
                    Integer.parseInt(args[argsFour]) > maxPort) {
                System.err.println("Error: port number must be in the range " +
                        "1024 to 65535");
                System.exit(1);
            }
        } else if (args[argsZero].equals(serverOption)) {
            // server mode
            if (!(args[argsOne].equals(pOption))) {
                System.err.println("Error: invalid input");
                System.exit(1);
            }
            if (Integer.parseInt(args[argsTwo]) < minPort ||
                    Integer.parseInt(args[argsTwo]) > maxPort) {
                System.err.println("Error: port number must be in the range " +
                        "1024 to 65535");
                System.exit(1);
            }
            mode = 1;
        } else {
            System.err.println("Error: invalid input");
            System.exit(1);
        }

        if (mode == zero) {
            // client mode
            System.out.println("This is client mode.");

            String hostName = args[argsTwo];
            int portNumber = Integer.parseInt(args[argsFour]);
            // second
            int time = Integer.parseInt(args[argsSix]);

            /*
             * Any object that implements java.lang.AutoCloseable, which
             * includes all objects which implement java.io.Closeable, can be
             * used as a resource.
             */
            try (
                    Socket socket = new Socket(hostName, portNumber);
                    OutputStream out = socket.getOutputStream()
            ) {
                byte[] packet = new byte[1000];
                long totalTime = time * 1000;
                long packetNumber = 0;
                long startTime = System.currentTimeMillis();

                while (true) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - startTime >= totalTime) {
                        break;
                    }
                    out.write(packet);
                    ++packetNumber;
                }

                System.out.println("sent=" + packetNumber + "KB " + "rate=" +
                        (double)packetNumber / (1000 * time)+ "Mbps\n");

            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Couldn't get I/O for the connection to " +
                        hostName);
                System.exit(1);
            }
        } else {
            // server mode
            System.out.println("This is server mode.");

            int listenPort = Integer.parseInt(args[argsTwo]);

            try (
                    ServerSocket serverSocket = new ServerSocket(listenPort);
                    Socket clientSocket = serverSocket.accept();
                    InputStream in = clientSocket.getInputStream()
            ) {
                byte[] buf = new byte[1000];
                long packetNumber = 0;
                long startTime = System.currentTimeMillis();
                int length;

                while ((length = in.read(buf)) != -1) {
                    if (length == 1000) {
                        ++packetNumber;
                    }
                }

                long currentTime = System.currentTimeMillis();
                long totalTime = currentTime - startTime;
                System.out.println("received=" + packetNumber + "KB " + "rate" +
                        "=" + (double)packetNumber / totalTime + "Mbps\n");
            }
        }
    }
}
