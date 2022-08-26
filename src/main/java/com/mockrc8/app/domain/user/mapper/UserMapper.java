package com.mockrc8.app.domain.user.mapper;

import com.mockrc8.app.domain.user.dto.UserRegisterRequestDto;
import com.mockrc8.app.domain.user.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper
public interface UserMapper {
    public long registerUser(User userVO);
    public int checkEmail(String userEmail);
    public int checkPhoneNumber(String userPhoneNumber);
    public User findUserByEmail(String userEmail);
//    Integer checkUserById(Long userId);
    public int updateRefreshToken(@Param("userId") Long userId, @Param("refreshToken") String refreshToken);

    List<UserInterestTagVo> getUserInterestTagVoByUserId(Long userId, Integer maxCount);
    UserProfileVo getUserProfile(Long userId);
    List<UserEmploymentBookmarkVo> getUserEmploymentBookmarkVoList(Long userId, Integer maxCount);
    List<UserEmploymentLikeVo> getUserEmploymentLikeVoList(Long userId, Integer maxCount);
}
