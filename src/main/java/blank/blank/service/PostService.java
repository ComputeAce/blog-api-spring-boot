package blank.blank.service;

import java.util.List;

import blank.blank.models.PostModel;

public interface PostService {

    PostModel createPost(PostModel postModel);
    List<PostModel> getAllPosts();
    PostModel getPostById(Long id);
    PostModel getPostByTitle(String title);
    void deletePostById(Long id);
}
