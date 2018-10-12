package com.gentics.diktyo.server;

public interface Server {

	void start() throws Exception;

	void stop();

	default void restart() throws Exception {
		stop();
		start();
	}

}
