package lt.viko.eif.eulozas.rest.musicplatform;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {
    private final SongRepository songRepository;

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    // Get songs by name
    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getSongsByName(@RequestParam("name") String name) {
        List<Song> foundSongs = songRepository.findByName(name);
        if (!foundSongs.isEmpty()) {
            return ResponseEntity.ok(foundSongs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Get all songs
    @GetMapping("/allsongs")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> allSongs = songRepository.findAllSongs();
        if (!allSongs.isEmpty()) {
            return ResponseEntity.ok(allSongs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Add a new song
    @PostMapping("/songcreation")
    public ResponseEntity<String> createSong(@RequestBody Song song) {
        boolean created = songRepository.createSong(song);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Song created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Song already exists");
        }
    }

    // Get song by artist
    @GetMapping("/artists")
    public ResponseEntity<List<Song>> getSongsByArtist(@RequestParam("artist") String artist) {
        List<Song> foundSongs = songRepository.findByArtist(artist);
        if (!foundSongs.isEmpty()) {
            return ResponseEntity.ok(foundSongs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Update a song
    @PutMapping("songedit/{id}")
    public ResponseEntity<String> updateSong(@PathVariable("id") String id, @RequestBody Song updatedSong) {
        boolean updated = songRepository.updateSong(id, updatedSong);

        if (updated) {
            return ResponseEntity.ok("Song updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
        }
    }

    // Delete a song
    @DeleteMapping("songdelete/{id}")
    public String deleteSong(@PathVariable String id) {
        boolean deleted = songRepository.deleteSong(id);
        if (deleted) {
            return "Song deleted successfully";
        } else {
            throw new RuntimeException("Song not found");
        }
    }

}




