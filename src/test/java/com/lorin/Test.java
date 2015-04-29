package com.lorin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Test {

	public static void main(String[] args) {
//		SafeSingleton singleton = SafeSingleton.INSTANCE;
//		singleton.println();
		System.out.printf("%x\n", 31558);
		AtomicReference<Thread> owner =new AtomicReference<Thread>();
		AtomicInteger serviceNum = new AtomicInteger();
		List<String> tst = new ArrayList<String>();
		tst.add("1");
		tst.add("2");
		System.out.println(tst.toString());
	}
}
