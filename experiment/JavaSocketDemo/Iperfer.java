//package iperfer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Iperfer {

	public static void main(String[] args) throws IOException {

		boolean mode;// 1 is Server mode and 0 is Client mode

		if ((args.length >= 3)) {
			if ((args.length == 3) && (args[0].equals("-s")) && (args[1].equals("-p")))
				mode = true;
			else if ((args.length == 7) && (args[0].equals("-c")) && (args[1].equals("-h")) && (args[3].equals("-p"))
					&& (args[5].equals("-t")))
				mode = false;
			else {
				System.out.println("Error: wrong arguments");
				return;
			}
		} else {
			System.out.println("Error: missing or additional arguments");
			return;
		}

		if (mode) {
			System.out.println("Server mode.");
			ServerSocket server = null;
			try {
				server = new ServerSocket(Integer.parseInt(args[2]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Socket serversocket = null;
			try {
				serversocket = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			InputStream in = null;
			try {
				in = serversocket.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] buf = new byte[1024];
			long packetnum = 0;
			long comeintime = System.currentTimeMillis();
			int len;

			while ((len = in.read(buf)) != -1) {
				if (len == 1024)
					packetnum++;
			}

			long comeouttime = System.currentTimeMillis();
			System.out.print("received = " + packetnum + "KB\t" + "rate = "
					+ (double) packetnum / (double) ((comeouttime - comeintime)) + "Mbps\n");
			server.close();

		} else {
			System.out.println("Client mode.");

			try {
				InetAddress Serveraddress = InetAddress.getByName(args[2]);
				Socket socket = new Socket(Serveraddress, Integer.parseInt(args[4]));
				OutputStream out = socket.getOutputStream();

				byte[] packet = new byte[1024];
				long msecond = Integer.parseInt(args[6]) * 1000;
				long packetnum = 0;
				long comeintime = System.currentTimeMillis();

				while (true) {
					long nowtime = System.currentTimeMillis();
					if (nowtime - comeintime >= msecond)
						break;
					out.write(packet);
					packetnum++;
				}

				System.out.print("sent = " + packetnum + "KB\t" + "rate = "
						+ (double) packetnum / (double) (Integer.parseInt(args[6]) * 1024) + "Mbps\n");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
