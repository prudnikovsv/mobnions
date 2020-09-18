package com.gh.prudnikovv.mobnions.server.client;

import com.gh.prudnikovv.mobnions.server.server.ConnectionHandler;

import java.util.UUID;

public class Client {

	private final UUID clientId;
	private final String clientName;
	private final ConnectionHandler handler;


	Client(UUID clientId, String clientName, ConnectionHandler handler) {
		this.clientId = clientId;
		this.clientName = clientName;
		this.handler = handler;
	}

	public UUID getClientId() {
		return clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public ConnectionHandler getHandler() {
		return handler;
	}
}