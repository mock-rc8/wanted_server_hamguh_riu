package com.mockrc8.app.domain.user.mapper;

import com.mockrc8.app.domain.user.dto.UserRegisterRequestDto;
import com.mockrc8.app.domain.user.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public long registerUser(User userVO);
    public int checkEmail(String userEmail);
    public int checkPhoneNumber(String userPhoneNumber);
}
