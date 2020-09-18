package com.gh.prudnikovv.mobnions.server.client.time;

import com.gh.prudnikovv.mobnions.server.client.SimpleClientManager;

import java.time.LocalDateTime;

public class Sync extends Thread {

	private final SimpleClientManager clientManager;
	private boolean isRunning;


	public Sync() {
		this.clientManager = SimpleClientManager.getInstance();
		this.isRunning = false;
	}

	@Override
	public void run() {
		isRunning = true;
		while (isRunning) {
			try {
				Thread.sleep(2000);

				if (clientManager.hasAvailableClients()) {
					System.out.println("Do NotifyClients");
					clientManager.notifyClients("Updated: " + LocalDateTime.now().toString());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}