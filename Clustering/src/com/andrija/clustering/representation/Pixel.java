package com.andrija.clustering.representation;

public class Pixel {
	
	public static final int[][] COLOR = {
			{0,0,0},
			{255,0,0},
			{0,0,255},
			{0,255,0},
			{255,130,60},
			{0,255,255},
			{130,110,60},
			{0,162,232},
			{40,155,20},
			{165,75,170},
			{215,215,0},
			{255,175,200},
			{80,80,80},
			{145,145,145,},
			{200,200,200}
			};
	
	private int x;
	private int y;
	
	private int red;
	private int green;
	private int blue;
	
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
		this.red = 255;
		this.green = 255;
		this.blue = 255;
	}
	
	public Pixel(int x, int y, int red, int green, int blue) {
		this.x = x;
		this.y = y;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
}
