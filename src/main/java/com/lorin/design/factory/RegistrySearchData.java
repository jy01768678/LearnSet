/**
 * Registry1.0 search 结果
 * 直接解析registry api结果，属性命名不再遵循驼峰命名规则
 */
package com.lorin.design.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mahaiyuan@55tuan.com
 * @since 2015年6月3日下午3:04:41
 */
public class RegistrySearchData {

	private int num_pages;//总页数
	private int num_results; // 仓库数量
	private String query; // 查询
	private List<RegistrySearchRepoData> results = new ArrayList<RegistrySearchRepoData>();

	//v2版本镜像信息放在这里了；
	private List<String> repositories = new ArrayList<String>();
	
	public RegistrySearchData() {
		super();
	}

	public RegistrySearchData(int num_results, String query, List<RegistrySearchRepoData> results) {
		super();
		this.num_results = num_results;
		this.query = query;
		this.results = results;
	}

	public int getNum_results() {
		return num_results;
	}

	public void setNum_results(int num_results) {
		this.num_results = num_results;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<RegistrySearchRepoData> getResults() {
		return results;
	}

	public void setResults(List<RegistrySearchRepoData> results) {
		this.results = results;
	}

	public List<String> getRepositories() {
		return repositories;
	}

	public void setRepositories(List<String> repositories) {
		this.repositories = repositories;
	}

	public int getNum_pages() {
		return num_pages;
	}

	public void setNum_pages(int num_pages) {
		this.num_pages = num_pages;
	}

}
