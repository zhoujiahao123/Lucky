package com.uestc.luckyuser;

import com.uestc.luckyuser.dao.UserMapper;
import com.uestc.luckyuser.model.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.Callable;

@SpringBootTest
@MapperScan("com.uestc.luckyuser.dao")
class LuckyUserApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    @Test
    void insertTest() {
        User user = new User();
        user.setName("LiuLiu");
        user.setGender(0);
        user.setPassword("liuliu");
        user.setMobilePhoneNumber("1234567891123456789112345678911234567891");
        userMapper.insert(user);

    }
    @Test
    void updateTest() {
        User user = new User();
        user.setId(8);
        user.setName("LiSi");
        userMapper.updateById(user);
    }
}
