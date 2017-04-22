package ma.example.themoviedb1.model;

public class Comments {

	private String authorName;
	private String comment;
	
	public Comments(String authorName, String comment) {
		super();
		this.authorName = authorName;
		this.comment = comment;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		
		return authorName + " " + comment + "\n";
	}
	
}
