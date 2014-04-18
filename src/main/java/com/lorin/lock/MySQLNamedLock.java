package com.lorin.lock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.sql.DataSource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.exception.NestableRuntimeException;

public class MySQLNamedLock extends AbstractDistributedLock{
	private String name;  
    private DataSource dataSource;  
    private long validationInterval = 1000L;  
    private ScheduledExecutorService scheduler;  
    private final AtomicReference<Connection> connection;  
    private final AtomicReference<ScheduledFuture<?>> future;
    
    public MySQLNamedLock() {  
        this(null, null, null);  
    }  
    
    public MySQLNamedLock(String name, DataSource dataSource, ScheduledExecutorService scheduler) {  
        this.name = name;  
        this.scheduler = scheduler;  
        this.dataSource = dataSource;  
        this.connection = new AtomicReference<Connection>();  
        this.future = new AtomicReference<ScheduledFuture<?>>();  
    }  
    
    @Override  
    public String toString() {  
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)  
        .append("name", this.name).toString();  
    }  
      
    /** 
     *  
     */  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public long getValidationInterval() {  
        return validationInterval;  
    }  
  
    public void setValidationInterval(long interval) {  
        this.validationInterval = interval;  
    }  
      
    public DataSource getDataSource() {  
        return dataSource;  
    }  
  
    public void setDataSource(DataSource dataSource) {  
        this.dataSource = dataSource;  
    }  
      
    public ScheduledExecutorService getScheduler() {  
        return scheduler;  
    }  
  
    public void setScheduler(ScheduledExecutorService scheduler) {  
        this.scheduler = scheduler;  
    }  
    
	@Override
	protected void doLock() {
		doTryLock(Integer.MAX_VALUE, TimeUnit.SECONDS);  
	}

	@Override
	protected void doLockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		doTryLock(Integer.MAX_VALUE, TimeUnit.SECONDS);  
	}

	@Override
	protected boolean doTryLock() {
		return  doTryLock(0, TimeUnit.SECONDS);
	}

	@Override
	protected boolean doTryLock(long timeout, TimeUnit unit) {
		 Integer r = null;  
	        ResultSet rs = null;  
	        PreparedStatement ps = null;  
	        try {  
	            this.connection.set(this.dataSource.getConnection());  
	            ps = this.connection.get().prepareStatement("SELECT GET_LOCK(?, ?)");  
	            ps.setString(1, this.name);  
	            ps.setInt(2, (int)TimeUnit.SECONDS.convert(timeout, unit));  
	            rs = ps.executeQuery();  
	            if(rs.next()) {  
	                r = rs.getInt(1);  
	                if(rs.wasNull()) r = null;  
	            }  
	        } catch(Exception e) {  
	            throw new NestableRuntimeException("failed to lock, name: " + this.name, e);  
	        } finally {  
	        	try{
	        		if(rs != null){
		            	rs.close();
		            	rs = null;
		            }
		            if(ps != null){
		            	ps.close();
		            	ps = null;
		            }
	        	}catch(Exception ex){
	        	}
	        }  
	          
	        //  
	        final boolean succeed = (r != null && r == 1);  
	        if(succeed && this.listener != null) {  
	            final long interval = this.validationInterval;  
	            this.future.set(this.scheduler.scheduleWithFixedDelay(new ValidationTask(), interval, interval, TimeUnit.MILLISECONDS));  
	        }  
	          
	        //  
	        return succeed;  
	}

	@Override
	protected void doUnlock() {
		// TODO Auto-generated method stub
		 final ScheduledFuture<?> f = this.future.getAndSet(null);  
	        if(f != null) f.cancel(true);  
	          
	        //  
	        Integer r = null;  
	        ResultSet rs = null;  
	        PreparedStatement ps = null;  
	        try {  
	            //  
	            ps = this.connection.get().prepareStatement("SELECT RELEASE_LOCK(?)");  
	            ps.setString(1, this.name);  
	            rs = ps.executeQuery();  
	            if(rs.next()) {  
	                r = rs.getInt(1);  
	                if(rs.wasNull()) r = null;  
	            }  
	              
	            //  
	            if(r == null) {  
	               // LOGGER.warn("lock does NOT exist, name: {}", this.name);  
	            } else if(r == 0) {  
//	                LOGGER.warn("lock was NOT accquired by current thread, name: {}", this.name);  
	            } else {  
//	                LOGGER.warn("failed to unlock, name: {}, result: {}", this.name, r);  
	            }  
	        } catch(Exception e) {  
	            throw new NestableRuntimeException("failed to unlock, name: " + this.name, e);  
	        } finally {  
	        	try{
	        		if(rs != null){
		            	rs.close();
		            	rs = null;
		            }
		            if(ps != null){
		            	ps.close();
		            	ps = null;
		            }
	        	}catch(Exception ex){
	        	}
	        }  
	}
	
	  private class ValidationTask implements Runnable {  
		  
	        @Override  
	        public void run() {  
	            try {  
	                ((com.mysql.jdbc.Connection)connection.get()).ping();  
	            } catch(Exception e) {  
	                if(isLocked() && listener != null && connection.get() != null) {  
	                    listener.onAbort(MySQLNamedLock.this, e);  
	                }  
	                throw new NestableRuntimeException(e); // Note: suppress subsequent executions   
	            }  
	        }  
	    }  

}
