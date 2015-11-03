package com.lorin.design.factory;

import java.util.List;

public class V1DockerRegistry extends AbstractRegistry {

	@Override
	protected RegistrySearchData doSeartch(RegistryDO registry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<ImageDO> doGetTags(RegistryDO registry, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean DoDelete(RegistryDO registry, String repoName, String tag) {
		// TODO Auto-generated method stub
		return false;
	}

}
