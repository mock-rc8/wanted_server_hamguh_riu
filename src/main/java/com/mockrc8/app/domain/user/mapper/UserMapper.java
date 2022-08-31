package com.mockrc8.app.domain.user.mapper;

import com.github.pagehelper.Page;
import com.mockrc8.app.domain.employment.vo.EmploymentLikeInfoVo;
import com.mockrc8.app.domain.employment.vo.ReducedEmploymentVo;
import com.mockrc8.app.domain.user.dto.*;
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
    List<UserExcludedCompanyDto> getUserExcludedCompanyDtoList(Long userId);
    UserJobGroupVo getUserJobGroup(Long userId);
    List<UserDetailedJobGroupVo> getUserDetailedJobGroupList(Long userId, Long jobGroupId);

    Integer checkUserLiked(Long userId, Long employmentId);
    Integer checkUserBookmarked(Long userId, Long employmentId);
    Integer checkUserCompanyFollowed(Long userId, Long companyId);
    Integer checkUserInterested(Long userId, Long tagId);
    Integer checkCompanyExcluded(Long userId, Long companyId);
    Integer checkUserJobGroupExist(Long userId);
    Integer checkUserDetailedJobGroupExist(Long userId);

    void registerUserLike(@Param("userEmploymentLikeDto") UserEmploymentLikeDto userEmploymentLikeDto);
    void deleteUserLike(@Param("userEmploymentLikeDto") UserEmploymentLikeDto userEmploymentLikeDto);

    void registerUserBookmark(@Param("userEmploymentBookmarkDto") UserEmploymentBookmarkDto userEmploymentBookmarkDto);
    void deleteUserBookmark(@Param("userEmploymentBookmarkDto") UserEmploymentBookmarkDto userEmploymentBookmarkDto);

    void registerUserCompanyFollow(@Param("userCompanyFollowDto") UserCompanyFollowDto userCompanyFollowDto);
    void deleteUserCompanyFollow(@Param("userCompanyFollowDto") UserCompanyFollowDto userCompanyFollowDto);


    void registerUserInterestTag(@Param("userInterestTagDto") UserInterestTagDto userInterestTagDto);
    void deleteUserInterestTag(@Param("userInterestTagDto") UserInterestTagDto userInterestTagDto);

    void registerUserExcludedCompany(@Param("userExcludedCompanyDto") UserExcludedCompanyDto userExcludedCompanyDto);
    void deleteUserExcludedCompany(@Param("userExcludedCompanyDto") UserExcludedCompanyDto userExcludedCompanyDto);

    UserProfileVo getUserProfile(Long userId);
    Page<ReducedEmploymentVo> getUserEmploymentBookmarkVoList(Long userId, Integer maxCount);
    Page<ReducedEmploymentVo> getUserEmploymentLikeVoList(Long userId, Integer maxCount);
}
