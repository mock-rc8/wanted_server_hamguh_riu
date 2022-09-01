package com.mockrc8.app.domain.user.mapper;
import com.mockrc8.app.domain.user.vo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @DisplayName("UserMapper register 테스트")
    @Test
    public void user_register(){
        final User user = User.builder()
                .name("test1")
                .password("testPassword")
                .email("test@naver.com")
                .phone_number("010-1234-5678")
                .build();
        final long l = userMapper.registerUser(user);

    }



}