package com.lorin.docker;

import java.util.ArrayList;
import java.util.List;


public class RegistrySearchRepoData {

	public List<String> repositories = new ArrayList<String>();

	private int num_results; // 仓库数量
	private String query; // 查询
	private List<String> results = new ArrayList<String>();
	
	public List<String> getRepositories() {
		return repositories;
	}

	public void setRepositories(List<String> repositories) {
		this.repositories = repositories;
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

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}
	
	
}
