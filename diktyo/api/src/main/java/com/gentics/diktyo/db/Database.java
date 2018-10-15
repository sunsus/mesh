package com.gentics.diktyo.db;

import com.gentics.diktyo.index.IndexManager;
import com.gentics.diktyo.tx.Tx;
import com.gentics.diktyo.tx.TxAction;
import com.gentics.diktyo.tx.TxAction0;
import com.gentics.diktyo.tx.TxAction1;
import com.gentics.diktyo.tx.TxAction2;

public interface Database extends AutoCloseable {

	/**
	 * Close the db.
	 */
	void close();

	/**
	 * Return the index management.
	 * 
	 * @return
	 */
	IndexManager index();

	/**
	 * Return a new transaction.
	 * 
	 * @return
	 */
	Tx tx();

	/**
	 * Use a new transaction and execute the given handler inside it.
	 * 
	 * @param txHandler
	 * @return
	 */
	<T> T tx(TxAction<T> txHandler);

	/**
	 * Use a new transaction and execute the given handler inside it.
	 * 
	 * @param txHandler
	 */
	default void tx(TxAction0 txHandler) {
		tx((tx) -> {
			txHandler.handle();
		});
	}

	/**
	 * Use a new transaction and execute the given handler inside it.
	 * 
	 * @param txHandler
	 * @return
	 */
	default <T> T tx(TxAction1<T> txHandler) {
		return tx((tx) -> {
			return txHandler.handle();
		});
	}

	/**
	 * Use a new transaction and execute the given handler inside it.
	 * 
	 * @param txHandler
	 */
	default void tx(TxAction2 txHandler) {
		tx((tx) -> {
			txHandler.handle(tx);
			return null;
		});
	}
}
