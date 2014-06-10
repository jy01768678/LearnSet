package com.lorin.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcFramework {
	
	/**
	 * 暴露服务
	 * @param serivce
	 * @param port
	 * @throws Exception
	 */
	public static void export(final Object service,int port) throws Exception{
		if(service == null){
			throw new IllegalArgumentException("service interface == null");
		}
		if(port <=0 || port > 255){
			throw new IllegalArgumentException("Invalid port : "+port);
		}
		System.out.println("Export service " + service.getClass().getName() + " on port " + port); 
		
		ServerSocket server = new ServerSocket(port);
		while(true){
			try{
				final Socket socket = server.accept();
				new Thread(new Runnable(){
					@Override
					public void run() {
						try {
							ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
							String methodName = input.readUTF();
							Class<?>[] paramsTypes = (Class<?>[]) input.readObject();
							Object[] arguments = (Object[]) input.readObject();
							
							ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
							
							Method method = service.getClass().getMethod(methodName, paramsTypes);
							Object result = method.invoke(service, arguments);
							
							output.writeObject(result);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}finally{
							try {
								socket.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
			}catch (Exception e){
				
			}
		}
	}

	/** 
     * 引用服务 
     *  
     * @param <T> 接口泛型 
     * @param interfaceClass 接口类型 
     * @param host 服务器主机名 
     * @param port 服务器端口 
     * @return 远程服务 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked") 
	public static <T> T refer(final Class<T> interfaceClass,final String host,final int port) throws Exception{
    	if (interfaceClass == null)  
            throw new IllegalArgumentException("Interface class == null");  
        if (! interfaceClass.isInterface())  
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");  
        if (host == null || host.length() == 0)  
            throw new IllegalArgumentException("Host == null!");  
        if (port <= 0 || port > 65535)  
            throw new IllegalArgumentException("Invalid port " + port);  
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);
        Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[]{interfaceClass}, new InvocationHandler(){
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Socket socket = new Socket(host,port);
				try{
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					try{
						output.writeUTF(method.getName());
						output.writeObject(method.getParameterTypes());
						output.writeObject(args);
						
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
                        try {  
                            Object result = input.readObject();  
                            if (result instanceof Throwable) {  
                                throw (Throwable) result;  
                            }  
                            return result;  
                        } finally {  
                            input.close();  
                        }  
					}catch(Exception e){
					}finally{
						output.close();	
					}
				}catch(Exception e){
					
				}finally{
					socket.close();
				}
				return null;
			}
        });
        return null;
	}
}
