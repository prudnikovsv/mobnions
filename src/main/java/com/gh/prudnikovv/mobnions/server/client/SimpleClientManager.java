package com.gh.prudnikovv.mobnions.server.client;

import com.gh.prudnikovv.mobnions.server.server.ConnectionHandler;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleClientManager implements ClientManager {

	private static final SimpleClientManager eagerInstance = new SimpleClientManager();

	private final ConcurrentHashMap<UUID, Client> connectedClientMap;


	private SimpleClientManager() {
		this.connectedClientMap = new ConcurrentHashMap<>();
	}

	@Override
	public synchronized void notifyClients(String msg) {
		connectedClientMap.values().forEach(c -> c.getHandler().send(msg));
	}

	@Override
	public Client registerClient(UUID connectionId, String clientName, ConnectionHandler handler) {
		return registerClient(new Client(connectionId, clientName, handler));
	}

	@Override
	public synchronized void unRegisterClient(UUID connectionId) {
		Client client = connectedClientMap.get(connectionId);

		if (client != null) {
			connectedClientMap.remove(connectionId);
		} else {
			throw new IllegalStateException("Opsss something gone worng!!!");
		}

		System.out.println("Un-Registering clients");
	}

	@Override
	public synchronized void unRegisterClients() {
		Iterator<UUID> ids = connectedClientMap.keys().asIterator();
		while (ids.hasNext()) {
			UUID id = ids.next();
			unRegisterClient(id);
		}
	}

	@Override
	public synchronized boolean hasAvailableClients() {
		return !connectedClientMap.isEmpty();
	}

	private synchronized Client registerClient(Client client) {
		System.out.println("Registered client: " + client.getClientName());
		connectedClientMap.put(client.getClientId(), client);
		return client;
	}

	public static SimpleClientManager getInstance() {
		return eagerInstance;
	}
}