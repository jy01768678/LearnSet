package com.lorin.design.factory;

public abstract class AbstractRegistryFactory implements RegistryFactory{

//	public abstract RegistrySearchData search(RegistryDO registry);
//	
//	public abstract List<ImageDO> getTags(RegistryDO registry, String name);
//	
//	public abstract boolean deleteTag(RegistryDO registry, String repoName, String tag);
	
	public Registry getRegistry(String version){
		 
		 return createRegistry(version);
	}
	 
	public abstract Registry createRegistry(String version);
}
