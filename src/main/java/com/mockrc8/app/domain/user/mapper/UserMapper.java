package com.mockrc8.app.domain.user.mapper;

import com.github.pagehelper.Page;
import com.mockrc8.app.domain.employment.vo.EmploymentLikeInfoVo;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.domain.user.dto.UserEmploymentBookmarkDto;
import com.mockrc8.app.domain.user.dto.UserEmploymentLikeDto;
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
    Integer checkUserLiked(Long userId, Long employmentId);
    Integer checkUserBookmarked(Long userId, Long employmentId);
    void registerUserLike(@Param("userEmploymentLikeDto") UserEmploymentLikeDto userEmploymentLikeDto);
    void deleteUserLike(@Param("userEmploymentLikeDto") UserEmploymentLikeDto userEmploymentLikeDto);
    void registerUserBookmark(@Param("userEmploymentBookmarkDto") UserEmploymentBookmarkDto userEmploymentBookmarkDto);
    void deleteUserBookmark(@Param("userEmploymentBookmarkDto") UserEmploymentBookmarkDto userEmploymentBookmarkDto);
    UserProfileVo getUserProfile(Long userId);
    Page<ReducedEmploymentVo> getUserEmploymentBookmarkVoList(Long userId, Integer maxCount);
    Page<ReducedEmploymentVo> getUserEmploymentLikeVoList(Long userId, Integer maxCount);
}
