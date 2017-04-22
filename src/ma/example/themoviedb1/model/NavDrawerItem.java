package ma.example.themoviedb1.model;

public class NavDrawerItem {
	private int icone;
	private String title;
	
	public NavDrawerItem(int icone, String title) {
		super();
		this.icone = icone;
		this.title = title;
	}
	
	public int getIcone() {
		return icone;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setIcone(int icone) {
		this.icone = icone;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
