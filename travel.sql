create database travels;
use travels;
# 用户表
create table t_user
(
    id       int primary key auto_increment comment '自增主键',
    username varchar(60) comment '用户名',
    password varchar(60) comment '密码',
    email    varchar(60) comment '邮箱号'
);

# 省份表
create table t_province
(
    id                int primary key auto_increment comment '自增主键',
    name              varchar(60) comment '省份名',
    tags              varchar(60) comment '景点标签',
    attractions_count int comment '景点个数'
);

# 景点表
create table t_attractions
(
    id          int primary key auto_increment comment '自增主键',
    `name`      varchar(60) comment '景点名',
    pic_path    varchar(120) comment '景点图片',
    hot_time    timestamp comment '景点旺季时间',
    hot_ticket  double comment '旺季票数',
    dim_ticket  double comment '淡季票数',
    `describe`  varchar(300) comment '景点描述',
    province_id int comment '连接省份表外键',
    constraint province_id
        foreign key (province_id) references t_province (id)
);
