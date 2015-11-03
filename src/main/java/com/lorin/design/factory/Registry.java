package com.lorin.design.factory;

import java.util.List;

public interface Registry {

	public RegistrySearchData search(RegistryDO registry);
	
	public List<ImageDO> getTags(RegistryDO registry, String name);
	
	public boolean deleteTag(RegistryDO registry, String repoName, String tag);
}
