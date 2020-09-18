package com.gh.prudnikovv.mobnions.server.server;

import com.gh.prudnikovv.mobnions.server.client.SimpleClientManager;
import com.gh.prudnikovv.mobnions.server.client.time.Sync;

import java.io.IOException;
import java.net.ServerSocket;

abstract class AbstractSocketServer extends Thread implements Server {

	private final int port;
	private final SimpleClientManager clientManager;

	private ServerSocket serverSocket;
	private boolean isRunning;


	public AbstractSocketServer(int port) {
		this.port = port;
		clientManager = SimpleClientManager.getInstance();
		isRunning = false;
	}

	@Override
	public final void run() {
		runServer();
	}

	@Override
	public void startServer() {
		System.out.println("Server starting on port: " + port);

		try (ServerSocket sc = new ServerSocket(port)) {
			// TODO  Move out sync
			Sync sync = new Sync();
			sync.start();
			serverSocket = sc;
			isRunning = true;
			this.start();
			this.join();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			stopServer();
		}
	}

	protected abstract void runServer();

	private boolean stopServer() {
		clientManager.unRegisterClients();
		isRunning = false;
		this.interrupt();

		return true;
	}

	protected final SimpleClientManager getClientManager() {
		return clientManager;
	}

	protected final ServerSocket getServerSocket() {
		return serverSocket;
	}

	protected final boolean isRunning() {
		return isRunning;
	}
}