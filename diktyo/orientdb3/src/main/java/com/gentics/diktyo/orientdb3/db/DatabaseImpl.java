package com.gentics.diktyo.orientdb3.db;

import javax.inject.Inject;
import javax.inject.Provider;

import com.gentics.diktyo.db.Database;
import com.gentics.diktyo.index.IndexManager;
import com.gentics.diktyo.orientdb3.tx.TxImpl;
import com.gentics.diktyo.tx.Tx;
import com.gentics.diktyo.tx.TxAction;

public class DatabaseImpl implements Database {

	private final IndexManager indexManager;

	private final Provider<TxImpl> txProvider;

	@Inject
	public DatabaseImpl(IndexManager indexManager, Provider<TxImpl> txProvider) {
		this.indexManager = indexManager;
		this.txProvider = txProvider;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public IndexManager index() {
		return indexManager;
	}

	@Override
	public Tx tx() {
		TxImpl tx = txProvider.get();
		// Add graph reference
		return tx;
	}

	@Override
	public <T> T tx(TxAction<T> txHandler) {
		return null;
	}

}
