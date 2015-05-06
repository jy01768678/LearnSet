package com.lorin.pool;

import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		Pool<Connection> pool = new BoundedBlockingPool<Connection>(10,
				new JDBCConnectionValidator(), new JDBCConnectionFactory("",
						"", "", ""));
		// do whatever you like
		
		Pool < Connection > pool1 =
		        PoolFactory.newBoundedBlockingPool(
		        10,
		        new JDBCConnectionFactory("","", "",""),
		        new JDBCConnectionValidator());
		        //do whatever you like
	}
}
