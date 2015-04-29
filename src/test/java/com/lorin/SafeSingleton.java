package com.lorin;

import java.io.Serializable;

public enum SafeSingleton implements Serializable {
	INSTANCE;

	public void println() {
		System.out.println("t");
	}

}
