package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트본문";

        // Spring-data-JPA를 통해서 DB에 접근 - PK인 id값을 조회해서 있으면 update를 없으면 insert를 수행한다.
        // builder를 통해서 data값을 추가하는 과정
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("kimguickguick@gmail.com")
                .build());

        // when
        // Test를 위해서 DB값을 가져옴
        List<Posts> postsList = postsRepository.findAll(); // List<>로 받음

        // then
        Posts posts = postsList.get(0); // 가져온 DB값 List<>의 0번째로 테스팅
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {

        // given
        LocalDateTime now = LocalDateTime.of(2021, 12, 22,0, 0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>> createDate= " + posts.getCreatedDate()+", modifiedDate= " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }

}
