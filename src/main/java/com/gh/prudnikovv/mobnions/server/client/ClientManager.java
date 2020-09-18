package com.gh.prudnikovv.mobnions.server.client;

import com.gh.prudnikovv.mobnions.server.server.ConnectionHandler;

import java.util.UUID;

public interface ClientManager {

	void notifyClients(String msg);

	Client registerClient(UUID connectionId, String clientName, ConnectionHandler handler);

	void unRegisterClient(UUID connectionId);

	void unRegisterClients();

	boolean hasAvailableClients();
}