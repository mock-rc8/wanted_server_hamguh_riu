create table user
(
    user_id       bigint auto_increment comment '유저 id'
        primary key,
    name          varchar(45)  null comment '유저 이름',
    email         varchar(45)  not null comment '유저 이메일',
    phone_number  varchar(45)  not null comment '유저 핸드폰번호',
    profile_image varchar(200) null comment '유저 프로필이미지',
    referral_id   bigint       null comment '추천인 id',
    career_year   int          null comment '경력',
    salary        int          null comment '연봉',
    provider      varchar(45)  null comment '소셜 로그인시 제공자',
    refresh_token varchar(255) null comment '소셜 로그인시 리프레시 토큰',
    password      varchar(255) not null
)
    comment '유저 도메인';

