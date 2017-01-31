package com.helloworld.lyz.allezmap.bean;

public class Results {

	private String vicinity;

	@Override
	public String toString() {
		return "Results{" +
				"vicinity='" + vicinity + '\'' +
				'}';
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}
}
