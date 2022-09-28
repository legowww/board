package com.example.board.web.service;


import com.example.board.domain.Bookmark;
import com.example.board.domain.Member;
import com.example.board.domain.Post;
import com.example.board.web.dto.post.PostDto;
import com.example.board.web.repository.BookmarkRepository;
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
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;





    @Transactional
    public void addBookmark(Long postId, Long memberId) {
        boolean result = existBookmark(postId, memberId);
        Member member = memberRepository.findById(memberId).get();
        Post post = postRepository.findById(postId).get();
        // 이미 북마크에 있다면 북마크를 해제한다.
        if (result) {
            Bookmark bookmark = bookmarkRepository.findByPost_IdAndMember_Id(postId, memberId);
            bookmarkRepository.delete(bookmark);
        }
        // 북마크에 추가
        else {
            member.checkBookmark(post);
        }
    }

    public boolean existBookmark(Long postId, Long memberId)  {
        return bookmarkRepository.existsByPost_IdAndMember_Id(postId, memberId);
    }

    public Page<PostDto> searchBookmarks(Long memberId, Pageable pageable) {
        return postRepository.findByMember_Id(memberId, pageable).map(PostDto::new);
    }
}
