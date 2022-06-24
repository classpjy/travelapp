package com.pjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pjy.cache.MybatisPlusRedisCache;
import com.pjy.model.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @author admin
 * @description 针对表【t_user】的数据库操作Mapper
 * @createDate 2022-06-23 01:10:05
 * @Entity com.pjy.model.entity.User
 */
@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface UserMapper extends BaseMapper<User> {
}




