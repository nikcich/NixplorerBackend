package com.cichosz.explorer;

public class DriveContents {
	private String path;
	private String totalSpace;
	private String usedSpace;
	private double percentage;
	
	public DriveContents(String p, String t, String u, double d) {
		this.path = p;
		this.totalSpace = t;
		this.usedSpace = u;
		this.percentage = d;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(String totalSpace) {
		this.totalSpace = totalSpace;
	}

	public String getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(String usedSpace) {
		this.usedSpace = usedSpace;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
	
	
}
