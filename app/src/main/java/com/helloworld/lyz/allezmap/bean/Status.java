package com.helloworld.lyz.allezmap.bean;

import java.util.List;


public class Status {

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Status{" +
				"status='" + status + '\'' +
				", results=" + results +
				'}';
	}


	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

	private List<Results> results;
}
