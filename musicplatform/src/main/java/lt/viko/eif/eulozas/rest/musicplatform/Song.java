package lt.viko.eif.eulozas.rest.musicplatform;

public class Song {


    private String name;
    private String artist;
    private int releaseYear;
    private Genre genre;
    private String id;


    public Song(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Song(String id, String name, String artist, int releaseYear, Genre genre) {

        this.id = id;
        this.name = name;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public enum Genre {
        Pop,
        Rock,
        Metal,
        Rap,
        Country
    }


}
