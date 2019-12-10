package rainbow.entity;
import java.util.Date;

public class Movie {
    private int movieId;
    private String name;
    private String enName;
    private Date releaseYear;
    private String img;
    private String country;
    private int scene;
    private String description;
    private Date uploadYear;
    private String video;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Date getUploadYear() {
        return uploadYear;
    }

    public void setUploadYear(Date uploadYear) {
        this.uploadYear = uploadYear;
    }
    
   
    
    public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Movie() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", name=" + name + ", enName=" + enName + ", releaseYear=" + releaseYear
				+ ", img=" + img + ", country=" + country + ", scene=" + scene + ", description=" + description
				+ ", uploadYear=" + uploadYear + ", video=" + video + "]";
	}

	public Movie(int movieId, String name, String enName, Date releaseYear, String img, String country, int scene,
			String description, Date uploadYear, String video) {
		this.movieId = movieId;
		this.name = name;
		this.enName = enName;
		this.releaseYear = releaseYear;
		this.img = img;
		this.country = country;
		this.scene = scene;
		this.description = description;
		this.uploadYear = uploadYear;
		this.video = video;
	}
	
	

   
}
