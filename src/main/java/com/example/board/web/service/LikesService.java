package com.example.board.web.service;

import com.example.board.domain.Likes;
import com.example.board.domain.Post;
import com.example.board.web.repository.LikesRepository;
import com.example.board.web.repository.MemberRepository;
import com.example.board.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public Long getLikes(Long postId) {
        return likesRepository.countByPost_Id(postId);
    }

    @Transactional
    public boolean clickLikes(Long postId, Long memberId) {
        boolean checkLikeOnThisPost = likesRepository.existsByPost_IdAndMember_Id(postId, memberId);
        if (!checkLikeOnThisPost) {
            Post post = postRepository.findById(postId).get();
            post.updateLikes();
            Likes like = Likes.createLike(post, memberRepository.findById(memberId).get());
            likesRepository.save(like);
            return true;
        }
        return false;
    }
}
