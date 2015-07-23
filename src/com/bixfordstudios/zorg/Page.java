package com.bixfordstudios.zorg;

public enum Page {
	LOGIN("login.php"),
	OVERVIEW("overview.php"),
	RESOURCES("resources.php"),
	BUILDINGS("buildings.php"),
	RESEARCH("buildings.php?mode=research"),
	SHIPYARD("buildings.php?mode=fleet"),
	DEFENSE("buildings.php?mode=defense"),
	FLEET("fleet.php"),
	MERCHANTS("marchand.php"),
	EMPIRE("imperium.php"),
	ALLIANCE("alliance.php"),
	GALAXY("galaxy.php?mode=0"),
	TECHNOLOGIES("techtree.php"),
	MESSAGES ("messages.php");
	
	public String url;
	Page(String url)
	{
		this.url = url;
	}
}
