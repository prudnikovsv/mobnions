package com.gh.prudnikovv.mobnions.server.server;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static com.gh.prudnikovv.mobnions.common.StringUtils.isNotBlank;

public class SimpleSocketServer extends AbstractSocketServer {

	public SimpleSocketServer(int port) {
		super(port);
	}

	@Override
	public void runServer() {
		while (isRunning()) {
			listenForConnections();
		}
	}

	private void listenForConnections() {
		try {
			System.out.println("Listening for a connection");
			handleConnection(getServerSocket().accept());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleConnection(Socket socket) throws IOException {
		System.out.println("Received a connection");

		ConnectionHandler requestHandler = new ConnectionHandler(
			socket,
			this::handleInitialConnection,
			this::handleDisconnection);

		boolean success = requestHandler.initialConnection();

		if (!success) {
			socket.close();
		}
	}

	private boolean handleInitialConnection(UUID connectionId, String message, ConnectionHandler handler) {
		return isNotBlank(message) && getClientManager().registerClient(connectionId, message, handler) != null;
	}

	private void handleDisconnection(UUID connectionId, ConnectionHandler handler) {
		try {
			handler.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}

		getClientManager().unRegisterClient(connectionId);
	}
}