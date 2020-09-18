package com.gh.prudnikovv.mobnions.server.server;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class ConnectionHandler {

	private final InitialConnectionHandler initialConnectionHandler;
	private final ClientDisconnectionHandler disconnectionHandler;
	private final UUID connectionId;
	private final Socket socket;

	private PrintWriter writer;
	private BufferedReader reader;


	ConnectionHandler(Socket socket,
	                  InitialConnectionHandler initialConnectionHandler,
	                  ClientDisconnectionHandler disconnectionHandler) {
		this.socket = socket;
		this.initialConnectionHandler = initialConnectionHandler;
		this.disconnectionHandler = disconnectionHandler;

		connectionId = UUID.randomUUID();
	}

	public boolean initialConnection() throws IOException {
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream());

		String message = reader.readLine();
		System.out.println("Connected with initial message: " + message);

		return initialConnectionHandler.handle(connectionId, message, this);
	}

	public synchronized void send(String msg) {
		if (socket.isClosed() || !socket.isConnected()) {
			disconnectionHandler.handle(connectionId, this);
			return;
		}

		writer.println(msg);
		writer.flush();

		if (writer.checkError()) {
			disconnectionHandler.handle(connectionId, this);
		}
	}

	void stop() throws IOException {
		writer.close();
		reader.close();

		if (!socket.isClosed()) {
			socket.close();
		}
	}

	@FunctionalInterface
	interface InitialConnectionHandler {

		boolean handle(UUID connectionId, String message, ConnectionHandler handler);
	}

	@FunctionalInterface
	interface ClientDisconnectionHandler {

		void handle(UUID connectionId, ConnectionHandler handler);
	}
}