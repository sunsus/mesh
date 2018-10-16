package com.gentics.diktyo.orientdb.db;

import javax.inject.Inject;
import javax.inject.Provider;

import com.gentics.diktyo.db.Database;
import com.gentics.diktyo.db.DatabaseType;
import com.gentics.diktyo.index.IndexManager;
import com.gentics.diktyo.orientdb.wrapper.factory.WrapperFactory;
import com.gentics.diktyo.tx.Tx;
import com.gentics.diktyo.tx.TxAction;
import com.gentics.diktyo.wrapper.element.WrappedVertex;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

public class DatabaseImpl implements Database<Vertex> {

	private final IndexManager indexManager;
	private final Provider<Tx> txProvider;

	private OrientGraphFactory factory;

	@Inject
	public DatabaseImpl(IndexManager indexManager, Provider<Tx> txProvider) {
		this.indexManager = indexManager;
		this.txProvider = txProvider;
	}

	@Override
	public void open(String name, DatabaseType type) {
		if (factory == null) {
			factory = new OrientGraphFactory("memory:" + name).setupPool(16, 100);
		} else {
			throw new RuntimeException("Database already opened.");
		}

	}

	@Override
	public boolean isOpen() {
		return factory != null;
	}

	@Override
	public void close() {
		factory.close();
		factory = null;
	}

	@Override
	public IndexManager index() {
		return indexManager;
	}

	@Override
	public Tx tx() {
		return txProvider.get();
	}

	@Override
	public <T> T tx(TxAction<T> txHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <R extends WrappedVertex<Vertex>> R createVertex(Class<? extends R> clazzOfR) {
		Vertex vertex = factory.getNoTx().addVertex(null);
		return WrapperFactory.frameVertex(vertex, clazzOfR);
	}

}
