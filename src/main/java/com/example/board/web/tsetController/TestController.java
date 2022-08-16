package com.example.board.web.tsetController;


import com.example.board.domain.LikeId;
import com.example.board.domain.Likes;
import com.example.board.domain.Member;
import com.example.board.web.repository.LikesRepository;
import com.example.board.web.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestController {

    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;


    @GetMapping("/test/likes")
    @ResponseBody
    public String testLikes() {

        boolean b = likesRepository.existsByPost_IdAndMember_Id(1L, 1L);
        System.out.println("b = " + b);

        Long aLong = likesRepository.countByPost_Id(1L);
        System.out.println("i = " + aLong);
        return String.valueOf(aLong);
    }



    /**
     * http://localhost:8080/test/api/member
     * 페이징이 적용되지 않은 api 이다. 한 번에 모든 memberDto 를 조회한다.
     */
//    @ResponseBody
//    @GetMapping("/test/api/member")
//    public List<MemberDto> allMembers() {
//        return memberRepository.findAll()
//                .stream()
//                .map(m -> new MemberDto(m))
//                .collect(Collectors.toList());
//    }

    /**
     * http://localhost:8080/test/api/member-page
     * 페이징이 작동하는 api 이다. default 값이 20이기 때문에 20개씩 조회한다.
     */
    @ResponseBody
    @GetMapping("/test/api/member-page")
    public List<MemberDto> allMembersPageable(
            @PageableDefault(size = 3, sort = "age",  direction = Sort.Direction.DESC) Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        log.info("pageNumber=" + pageNumber);


        return memberRepository.findAll(pageable)
                .stream()
                .map(m -> new MemberDto(m))
                .collect(Collectors.toList());
    }

    /**
     * http://localhost:8080/test/api/page?name=1&page=2&size=5 -> 1이 붙은 Member 검색, 뒤에는 페이징 옵션
     */
    @ResponseBody
    @GetMapping("/test/api/page")
    public Page<MemberDto> findMemberByNameContaining(@RequestParam("name") String name, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        log.info("pageNumber=" + pageNumber);

        return memberRepository.findByNameContaining(name, pageable).map(MemberDto::new);
    }

    @ResponseBody
    @GetMapping("/test/api/pageContent")
    public List<MemberDto> findMemberByNameContainingContent(@RequestParam(name = "name") String name,  Pageable pageable) {
        return memberRepository.findByNameContaining(name, pageable).map(MemberDto::new).getContent();
    }

    @Getter
    static class MemberDto {
        private String name;
        private Integer age;
        public MemberDto(Member member) {
            name = member.getName();
            age = member.getAge();
        }
    }
}
