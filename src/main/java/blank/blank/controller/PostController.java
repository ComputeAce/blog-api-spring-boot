package blank.blank.controller;

import blank.blank.models.PostModel;
import blank.blank.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostModel>> getAllPosts()
    { 
        List<PostModel> postModel = postService.getAllPosts();
        return new ResponseEntity<>(postModel, HttpStatus.OK);
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<PostModel> getPostById(@PathVariable("id") Long id) {
        PostModel post = postService.getPostById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(post, HttpStatus.OK); 
        }
    }

    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable("id") Long id)
    {   
        if(id == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PostModel post = postService.getPostById(id); 
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postService.deletePostById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("search-post/{title}")
    public ResponseEntity<PostModel> getPostByTitle(@PathVariable("title") String title) {
        PostModel post = postService.getPostByTitle(title);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
    }
    

    @PostMapping("/create")
    public ResponseEntity<PostModel> createPost(
        @RequestParam("title") String title,
        @RequestParam("category") String category,
        @RequestParam("contents") String contents,
        @RequestParam("image") MultipartFile image)
         
        throws IOException 
        {
  
            String imageUrl = saveImage(image); 

            PostModel postModel = new PostModel();
            postModel.setTitle(title);
            postModel.setCategory(category);
            postModel.setContents(contents);
            postModel.setImage(imageUrl);
            postModel.setDate(LocalDate.now());

            PostModel savedPost = postService.createPost(postModel);

            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        }

        private String saveImage(MultipartFile image) throws IOException {
            String originalFilename = image.getOriginalFilename();
            String uniqueFileName = System.currentTimeMillis() + "_" + originalFilename;

            Path imagePath = Path.of("src/main/resources/static/images/", uniqueFileName);
            Files.createDirectories(imagePath.getParent());
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            return "/images/" + uniqueFileName;
        }
    
}
