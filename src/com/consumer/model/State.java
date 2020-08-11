package com.consumer.model;

public class State {

	private String name;
	private String capital;
	private int population;
	private String region;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "State [name=" + name + ", capital=" + capital + ", population=" + population + ", region=" + region
				+ "]";
	}
	
	
}
