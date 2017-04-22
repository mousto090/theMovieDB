package ma.example.themoviedb1.model;

import java.util.Arrays;
import java.util.List;

import ma.example.themoviedb1.Constantes;

public class Movies {
	private int id;
	private String title;
	private String posterName;
	private String releaseDate;
	private int duree;
	private Double rating;
	private String overview;
	// Array for backdrops name
	private String[] backdrops;
	// Array for comments
	private Comments[] review;
	// Array for trailer
	private String[] trailer;
	// array for genre
	private String[] genres;
	// array cast
	private Actor[] actor;
	// array similar movie
	private Movies[] similarMovies;
	
	//movie is favorites
	private boolean favorite;
	private boolean watchlist;

	private int voteCount;

	private final String NOTHING_FOUND = "Nothing found";

	public Movies(int id, String title, String posterName) {
		this.id = id;
		this.title = title;
		this.posterName = posterName;
	}

	public Movies(int id, String title, String posterName, String releaseDate, Double rating, int voteCount) {
		this(id, title, posterName);
		this.releaseDate = releaseDate;
		this.rating = rating;
		this.voteCount = voteCount;
	}

	public Movies(int id, String title, String posterName, String releaseDate, int duree, Double rating,
			String overview) {
		this(id, title, posterName);
		this.releaseDate = releaseDate;
		this.duree = duree;
		this.rating = rating;
		this.overview = overview;
	}

	public Movies(int id, String title, String posterName, String releaseDate, int duree, Double rating,
			int voteCount, String overview, String[] backdrops, Comments[] review, String[] trailer, String[] genres, Actor[] actor,
			Movies[] similarMovies) {

		this(id, title, posterName, releaseDate, duree, rating, overview);
		this.voteCount = voteCount;
		this.backdrops = backdrops;
		this.review = review;
		this.trailer = trailer;
		this.genres = genres;
		this.actor = actor;
		this.similarMovies = similarMovies;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return isEmpty(title);
	}

	public String getReleaseDate() {
		return isEmpty(releaseDate);
	}

	public String getReleaseYear() {
		// extrait year from date string (e.g 2015-10-23)
		if(isEmpty(releaseDate) == NOTHING_FOUND){
			return NOTHING_FOUND;
		}
		String year = releaseDate.substring(0, releaseDate.indexOf('-'));
		return isEmpty(year);
	}

	public int getDuree() {
		return duree;
	}

	public Double getRating() {
		return rating;
	}

	public String getOverview() {
		return isEmpty(overview);
	}

	public String getPosterName() {
		return posterName;
	}

	public String[] getBackdrops() {		
		return backdrops;
	}

	public Comments[] getReview() {
		return review;
	}

	public String[] getTrailer() {
		return trailer;
	}

	public Movies[] getSimilarMovies() {
		return similarMovies;
	}

	public Actor[] getActor() {
		return actor;
	}

	public String getGenres() {
		String str = "";
		int n = genres.length;
		if(n == 0){
			return NOTHING_FOUND;
		}
		
		for (int i = 0; i < n; i++) {
			str += (i < n - 1) ? genres[i] + " / " : genres[i];
		}

		return str;
	}

	public int getVoteCount() {
		return voteCount;
	}
	
	public boolean isFavorite(){
		return favorite;
	}
	
	public boolean isWatchlist(){
		return watchlist;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setBackdrops(String[] backdrops) {
		this.backdrops = backdrops;
	}

	public void setReview(Comments[] review) {
		this.review = review;
	}

	public void setTrailer(String[] trailer) {
		this.trailer = trailer;
	}

	public void setActor(Actor[] actor) {
		this.actor = actor;
	}

	public void setSimilarMovies(Movies[] similarMovies) {
		this.similarMovies = similarMovies;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	public void setWatchlist(boolean watchlist) {
		this.watchlist = watchlist;
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
	
		
	public boolean containsMovie(List<Movies> listMovie){
		if(listMovie.size() == 0){
			return false;
		}
		for (Movies movies : listMovie) {
			if(movies.getId() == this.id ){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return id + " " + title + " " + posterName + "\n" + releaseDate + " " + duree + " " + " " + rating + " "
				+ voteCount + " \n" + overview + "\n" + Arrays.toString(backdrops) + "\n" + Arrays.toString(review)
				+ " \n" + favorite + " " + watchlist;
	}
}
