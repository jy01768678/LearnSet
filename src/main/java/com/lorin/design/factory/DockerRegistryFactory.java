package com.lorin.design.factory;


public class DockerRegistryFactory extends AbstractRegistryFactory{

	@Override
	public Registry createRegistry(String version) {
		Registry registry = null;
		if("v1".equals(version)){
			registry = new V1DockerRegistry();
		}else if("v2".equals(version)){
			
		}
		return registry;
	}

}
