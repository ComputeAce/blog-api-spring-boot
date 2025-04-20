package blank.blank.service;
import blank.blank.models.PostModel;
import blank.blank.rep.PostRep;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRep postRep; 

    @Override
    public PostModel createPost(PostModel postModel) {
        return postRep.save(postModel); 
    }

    @Override
    public List<PostModel> getAllPosts()
    {
        return postRep.findAll();
    }

    @override
    public PostModel getPostById(Long id)
    {
        
        Optional<PostModel> optionalPostModel = postRep.findById(id);
        return optionalPostModel.orElse(null);
    }

    @Override
    public PostModel getPostByTitle(String title)
    {
        List<PostModel> posts = postRep.findByTitleContainingIgnoreCase(title);
        return posts.isEmpty() ? null : posts.get(0);
    }
}
