package com.mockrc8.app.global.util;

import javax.servlet.http.HttpServletRequest;

public class InfinityScroll {

    // 헤더에 값이 없으면 1, 있으면 그 값대로 스크롤 횟수 리턴
    public static Integer getScrollCount(HttpServletRequest request){
        String scroll = request.getHeader("scrollCount");
        Integer scrollCount = 1;
        if(scroll != null){
            scrollCount = Integer.valueOf(scroll);
        }

        return scrollCount;
    }
}
