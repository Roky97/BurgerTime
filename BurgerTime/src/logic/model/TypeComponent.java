package logic.model;

public enum TypeComponent {

	DOWN_BREAD("gui/resources/game/down_bread_0"),
	SALAD("gui/resources/game/salad_0"),
	HAMBUGER("gui/resources/game/hamburger_0"),
	UP_BREAD("gui/resources/game/up_bread_0");
	
	private String urlComponent;
	
	TypeComponent(String urlString) {
		this.urlComponent = urlString;
		
	}
	
	public String getUrl() {
		return urlComponent;
	}
	
	
}
