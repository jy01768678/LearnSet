/**
 * 
 */
package com.lorin.design.factory;

/**
 * @author mahaiyuan@55tuan.com
 * @since 2015年6月3日下午3:07:22
 */
public class RegistrySearchRepoData {

	private String name; // 仓库名称[含library]
	private String description; // 仓库描述

	public RegistrySearchRepoData() {
		super();
	}

	public RegistrySearchRepoData(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
