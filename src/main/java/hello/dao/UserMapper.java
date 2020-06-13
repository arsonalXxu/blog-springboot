package hello.dao;

import hello.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(@Param("id") long id);

    @Select("select id, username, encrypted_password, avatar, created_at, updated_at from user " +
            "where username=#{username}")
    User getUserByUsername(@Param("username") String username);

    @Insert("insert into user(username, encrypted_password, created_at, updated_at) " +
            "values(#{username}, #{encryptedPassword}, now(), now())")
    void save(@Param("username") String username, @Param("encryptedPassword") String encodedPassword);
}
