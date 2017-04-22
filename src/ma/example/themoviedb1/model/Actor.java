package ma.example.themoviedb1.model;

public class Actor {

	private int id;
	private String character;
	private String name;
	private String profilePath;
	private String birthday;
	private String birthplace;
	private String biography;

	private final String NOTHING_FOUND = "Nothing found";

	public Actor(int id, String name, String profilePath) {
		this.id = id;
		this.name = name;
		this.profilePath = profilePath;
	}

	public Actor(int id, String character, String name, String profilePath) {
		this.id = id;
		this.character = character;
		this.name = name;
		this.profilePath = profilePath;
	}

	public Actor(int id, String name, String profilePath, String birthday, String birthPlace, String biography) {
		this.id = id;
		this.name = name;
		this.profilePath = profilePath;
		this.birthday = birthday;
		this.birthplace = birthPlace;
		this.biography = biography;
	}

	public int getId() {
		return id;
	}

	public String getCharacter() {
		return isEmpty(character);
	}

	public String getName() {
		return isEmpty(name);
	}

	public String getProfilePath() {
		return profilePath;
	}

	public String getBirthday() {
		return isEmpty(birthday);
	}

	public String getBirthplace() {
		return isEmpty(birthplace);
	}

	public String getBiography() {
		return isEmpty(biography);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setBirthplace(String birthPlace) {
		this.birthplace = birthPlace;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	private String isEmpty(String str) {
		if (str == null) {
			return NOTHING_FOUND;
		}
		if (str.length() == 0) {
			return NOTHING_FOUND;
		}
		if (str == "null") {
			return NOTHING_FOUND;
		}

		return str;
	}

	@Override
	public String toString() {
		return id + " " + character + " " + name + " \n" + profilePath + " " + birthday + " " + birthplace + " \n"
				+ biography;
	}

}
