package com.example.board.web.service;

import com.example.board.domain.Post;
import com.example.board.domain.Member;
import com.example.board.domain.type.PostType;
import com.example.board.domain.type.SearchType;
import com.example.board.web.dto.post.PostDto;
import com.example.board.web.dto.post.PostSaveDto;
import com.example.board.web.repository.MemberRepository;
import com.example.board.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(Long id, PostSaveDto postSaveDto) {
        Member writerMember = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no exist Id = " + id));
        Post post = Post.createPost(writerMember, postSaveDto.getTitle(), postSaveDto.getContent(), postSaveDto.getType());
        postRepository.save(post);
    }

    public PostDto findByIdUsingUpdatePost(Long id) {
        Post findPost = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("no exist Id = " + id));

        return new PostDto(findPost);
    }


    @Transactional
    public PostDto findByIdUsingReadPost(Long id) {
        Post findPost = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("no exist Id = " + id));
        findPost.updateView();
        return new PostDto(findPost);
    }


    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postRepository.delete(post);
    }

    @Transactional
    public void update(Long id, String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        post.update(title, content);
    }

    public Page<PostDto> searchPosts(PostType postType, SearchType searchType, String searchValue, Pageable pageable) {
        if (postType == null) {
            if (searchType == null || searchValue.isBlank()) {
                return postRepository.findAllWithMemberUsingFetchJoin(pageable).map(PostDto::new);
            }
            switch (searchType) {
                case TITLE: {
                    return postRepository.findByTitleContaining(searchValue, pageable).map(PostDto::new);
                }
                case CONTENT: {
                    return postRepository.findByContentContaining(searchValue, pageable).map(PostDto::new);
                }
                case WRITER: {
                    return postRepository.findByMember_Name(searchValue, pageable).map(PostDto::new);
                }
            }
        }
        else {
            if (searchType == null || searchValue.isBlank()) {
                return postRepository.findAllWithMemberUsingFetchJoin(postType, pageable).map(PostDto::new);
            }
            switch (searchType) {
                case TITLE: {
                    return postRepository.findByTypeAndTitleContaining(postType, searchValue, pageable).map(PostDto::new);
                }
                case CONTENT: {
                    return postRepository.findByTypeAndContentContaining(postType, searchValue, pageable).map(PostDto::new);
                }
                case WRITER: {
                    return postRepository.findByTypeAndMember_Name(postType, searchValue, pageable).map(PostDto::new);
                }
            }
        }
        return null;
    }





//        switch (searchType) {
//            case TITLE: postRepository.findByTitleContaining(searchValue, pageable).map(PostDto::new);
//            case CONTENT: postRepository.findByContentContaining(searchValue, pageable).map(PostDto::new);
//            case WRITER: postRepository.findByMember_Name(searchValue, pageable).map(PostDto::new);
//        };
}

