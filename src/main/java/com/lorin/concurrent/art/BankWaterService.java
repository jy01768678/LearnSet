package com.lorin.concurrent.art;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BankWaterService implements Runnable{

	private CyclicBarrier c = new CyclicBarrier(4,this);
	
	private Executor executor = Executors.newFixedThreadPool(4);
	
	private ConcurrentHashMap<String,Integer> sheetBankWaterCount = 
			new ConcurrentHashMap<String,Integer>();
	
	private void count(){
		for(int i=0;i<4;i++){
			executor.execute(new Runnable(){
				@Override
				public void run() {
					sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
					try{
						c.await();
					}catch(Exception ex){
						
					}
				}
			});
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int result = 0;
		for(Entry<String,Integer> sheet : sheetBankWaterCount.entrySet()){
			result += sheet.getValue();
		}
		sheetBankWaterCount.put("result", result);
	}

	public static void main(String[] args) {
		BankWaterService bankWaterService = new BankWaterService();
		bankWaterService.count();
	}
}
