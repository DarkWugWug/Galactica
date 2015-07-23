package com.bixfordstudios.zorg;

public enum Server {
	MASSACRE("http://massacre.zorgempire.org/"),
	STANDARD("http://www.zorgempire.com/"),
	SPEED("http://www.zorgempire.org/"),
	X_TREME("http://xtreme.zorgempire.org/");
	
	public String url;
	
	Server(String url)
	{
		this.url = url;
	}
}
