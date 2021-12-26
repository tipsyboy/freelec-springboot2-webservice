package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);

        return id;

        /**
         *  12.23
            게시글 delete 구현시에 delete() 메소드를 IndexController에 넣는 바람에
            'java.lang.IllegalArgumentException: Unknown return value type: java.lang.Long' Error를 발생시킴.
            화면을 리턴하는 구조에서 ResponseBody를 리턴하지 않기 때문에 에러를 발생시킨것
            만약 @Controller에서도 Data를 반환하는 경우라면 @ResponseBody를 활용해야 함.

            keyword - @RestController와 @Controller의 차이
            참조 - https://mangkyu.tistory.com/49 , https://github.com/RumbleKAT/com.rumblekat.book/issues/2
         */
    }

}
