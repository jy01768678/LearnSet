package com.lorin.design.factory;

import java.util.List;

public abstract class AbstractRegistry implements Registry {

	@Override
	public RegistrySearchData search(RegistryDO registry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImageDO> getTags(RegistryDO registry, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTag(RegistryDO registry, String repoName, String tag) {
		// TODO Auto-generated method stub
		return false;
	}

	//----模板方法
	
	protected abstract RegistrySearchData doSeartch(RegistryDO registry);
	protected abstract List<ImageDO> doGetTags(RegistryDO registry,String name);
	protected abstract boolean DoDelete(RegistryDO registry, String repoName, String tag);
}
