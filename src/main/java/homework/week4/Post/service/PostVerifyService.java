package homework.week4.Post.service;

import homework.week4.Post.entity.Post;
import homework.week4.Post.repository.PostRepository;
import homework.week4.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostVerifyService {

    private final PostRepository postRepository;

    //게시글 확인 및 반환
    public Post getValidPost(Long postId){
        Post post = postRepository.findPost(postId).orElseThrow(
                () -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

        return post;
    }

    public void checkPost(Long postId){
        if(!(postRepository.existsPost(postId))){
            throw new NotFoundException("해당 게시글이 존재하지 않습니다.");
        }
    }


}
