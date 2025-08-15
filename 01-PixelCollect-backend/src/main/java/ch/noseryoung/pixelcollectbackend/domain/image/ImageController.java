package ch.noseryoung.pixelcollectbackend.domain.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    @Autowired private ImageService imageService;
    @Autowired private ImageRepository imageRepository;

    // because postman won't let me upload images, use this instead in the cmd:
    // curl -F "file=@C:/Users/lukag/Downloads/Cat03.jpg" http://localhost:8081/api/v1/images/upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(imageService.uploadImage(file));
    }

    @GetMapping("/view/{name}")
    public ResponseEntity<byte[]> viewImage(@PathVariable String name) {
        var image = imageRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); // todo: better exception handling

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(imageService.downloadImage(name));
    }

    // there is no download endpoint for this project, but if needed, you just add this to the response:
    // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
}