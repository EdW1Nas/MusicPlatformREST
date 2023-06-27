package lt.viko.eif.eulozas.rest.musicplatform;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SongRepository {
    private MongoCollection<Document> songCollection;

    public SongRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("musicplatform");
        songCollection = database.getCollection("songs");
    }

    // Add a new song
    public boolean createSong(Song song) {
        Document existingSong = songCollection.find(Filters.and(
                Filters.eq("name", song.getName()),
                Filters.eq("artist", song.getArtist())
        )).first();

        if (existingSong != null) {
            return false; // Song with the same name and artist already exists
        }

        Document songDocument = new Document()
                .append("name", song.getName())
                .append("artist", song.getArtist())
                .append("releaseYear", song.getReleaseYear())
                .append("genre", song.getGenre().toString());

        songCollection.insertOne(songDocument);
        return true; // Song created successfully
    }


    // Get song by name
    public List<Song> findByName(String name) {
        List<Song> foundSongs = new ArrayList<>();

        List<Document> songDocuments = songCollection.find(Filters.eq("name", name)).into(new ArrayList<>());
        for (Document songDocument : songDocuments) {
            ObjectId id = songDocument.getObjectId("_id"); // Retrieve the automatically generated _id as ObjectId
            Song song = new Song(
                    id.toString(), // Convert ObjectId to string if necessary
                    songDocument.getString("name"),
                    songDocument.getString("artist"),
                    songDocument.getInteger("releaseYear"),
                    Song.Genre.valueOf(songDocument.getString("genre"))
            );
            foundSongs.add(song);
        }

        return foundSongs;
    }


    // Get song by artist
    public List<Song> findByArtist(String artist) {
        List<Song> foundSongs = new ArrayList<>();

        List<Document> songDocuments = songCollection.find(Filters.eq("artist", artist)).into(new ArrayList<>());
        for (Document songDocument : songDocuments) {
            ObjectId id = songDocument.getObjectId("_id"); // Retrieve the automatically generated _id as ObjectId
            Song song = new Song(
                    id.toString(), // Convert ObjectId to string if necessary
                    songDocument.getString("name"),
                    songDocument.getString("artist"),
                    songDocument.getInteger("releaseYear"),
                    Song.Genre.valueOf(songDocument.getString("genre"))
            );
            foundSongs.add(song);
        }

        return foundSongs;
    }

    // Get all songs
    public List<Song> findAllSongs() {
        List<Song> allSongs = new ArrayList<>();

        List<Document> songDocuments = songCollection.find().into(new ArrayList<>());
        for (Document songDocument : songDocuments) {
            ObjectId id = songDocument.getObjectId("_id"); // Retrieve the automatically generated _id as ObjectId
            Song song = new Song(
                    id.toString(), // Convert ObjectId to string if necessary
                    songDocument.getString("name"),
                    songDocument.getString("artist"),
                    songDocument.getInteger("releaseYear"),
                    Song.Genre.valueOf(songDocument.getString("genre"))
            );
            allSongs.add(song);
        }

        return allSongs;
    }

    // Update a song
    public boolean updateSong(String id, Song updatedSong) {
        Document query = new Document("_id", new ObjectId(id));
        Document update = new Document("$set", new Document()
                .append("name", updatedSong.getName())
                .append("artist", updatedSong.getArtist())
                .append("releaseYear", updatedSong.getReleaseYear())
                .append("genre", updatedSong.getGenre().toString()));

        UpdateResult result = songCollection.updateOne(query, update);

        return result.getModifiedCount() > 0;
    }

    // Delete a song
    public boolean deleteSong(String id) {
        Document query = new Document("_id", new ObjectId(id));
        DeleteResult result = songCollection.deleteOne(query);
        return result.getDeletedCount() > 0;
    }

}
