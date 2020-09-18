package com.gh.prudnikovv.mobnions.server;

import com.gh.prudnikovv.mobnions.server.server.Server;
import com.gh.prudnikovv.mobnions.server.server.SimpleSocketServer;

import static com.gh.prudnikovv.mobnions.common.StubVars.PORT;

public class ServerApplication {

	public static void main(String[] args) {
		Server server = new SimpleSocketServer(PORT);
		server.startServer();
		System.out.println("EXIT!!!");
	}
}