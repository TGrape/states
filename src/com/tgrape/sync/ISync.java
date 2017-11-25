package com.tgrape.sync;

public interface ISync {

	public void beginSync();
	public void sync(String url);
	public void endSync();
}
