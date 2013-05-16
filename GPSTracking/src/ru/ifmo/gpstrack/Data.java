package ru.ifmo.gpstrack;

public class Data {
	public String name, text;
	public double latitude, longitude, id;

	public Data() {

	}

	public Data(String name, String text, double latitude, double longtitude) {
		this.name = name;
		this.text = text;
		this.latitude = latitude;
		this.longitude = longtitude;
	}
}
