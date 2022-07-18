package com.example.board.web.service;

import com.example.board.domain.Post;
import com.example.board.domain.member.Member;
import com.example.board.web.dto.post.PostIndexDto;
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
    private final PostRepository postsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(Long id, PostSaveDto postSaveDto) {
        Member writerMember = memberRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("no exist Id = " + id));

        Post post = Post.createPost(writerMember, postSaveDto.getTitle(), postSaveDto.getContent());
        postsRepository.save(post);
    }

    //==using fetch join==
    public List<PostIndexDto> findAll() {
        List<Post> posts = postsRepository.findAllWithMemberByFetchJoin();
        List<PostIndexDto> collect = posts.stream()
                .map(p -> new PostIndexDto(p))
                .collect(Collectors.toList());
        return collect;
    }



}
