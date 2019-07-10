package logic.model;

public enum TypeEnemy {
	
	EGG("gui/resources/game/enemy_egg"),
	WRUSTEL("gui/resources/game/enemy_wrustel");

	
	private String urlEnemy;
	
	TypeEnemy(String urlString) {
		this.urlEnemy = urlString;
		
	}
	
	public String getUrl() {
		return urlEnemy;
	}
	
}
