package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // spring Test를 Random_port 환경에서 실행
public class PostsApiControllerTest {

    @LocalServerPort // 로컬 서버의 포트
    private int port;

    // WebMvcTest는 JPA 기능이 작동하지 않으므로 JPA를 사용하는 테스트의 경우 TestRestTemplate를 SpringBootTest와 함께 사용한다.
    @Autowired
    private TestRestTemplate restTemplate; // 서블릿 컨테이너를 사용해서 실제 서버가 돌아가는 환경을 만든다...?

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        // given으로 주어진 dto와 url을 사용해서 실제 post에 대한 응답을 받음 - 인자(url, 요청 객체, 반환형)
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);
//        System.out.println("##### START #####");
//        System.out.println(responseEntity);
//        // 1-1)여기서 Body값이 1이 나오는데 PostsService 구현시에 getId()를 해서 id값을 리턴하기 때문임. 즉, id값이라는 것
//        System.out.println(responseEntity.getBody());
//        System.out.println("##### END #####");

        // then
        // when에서 request를 통해 받은 response로 testing.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // 1-2) 따라서 여기서 0보다 크다는것은 DB에서 id가 1부터 차례로 잘 들어갔다는 것을 의미하는듯?
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }
}
