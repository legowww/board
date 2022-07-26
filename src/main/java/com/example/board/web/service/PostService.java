package com.example.board.web.service;

import com.example.board.domain.Post;
import com.example.board.domain.member.Member;
import com.example.board.web.dto.post.PostIndexDto;
import com.example.board.web.dto.post.PostPrintDto;
import com.example.board.web.dto.post.PostSaveDto;
import com.example.board.web.repository.MemberRepository;
import com.example.board.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(Long id, PostSaveDto postSaveDto) {
        Member writerMember = memberRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("no exist Id = " + id));

        Post post = Post.createPost(writerMember, postSaveDto.getTitle(), postSaveDto.getContent());
        postRepository.save(post);
    }

    //==find post==
    public PostPrintDto findById(Long id) {
        Post findPost = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("no exist Id = " + id));
        return new PostPrintDto(findPost);
    }

    //==all posts==
    public List<PostIndexDto> findAll() {
        List<Post> posts = postRepository.findAllWithMemberByFetchJoin();
        List<PostIndexDto> collect = posts.stream()
                .map(p -> new PostIndexDto(p))
                .collect(Collectors.toList());
        return collect;
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
}
