package com.mockrc8.app.domain.community.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommunityMapper {
    void getCommunityPosts();

}
