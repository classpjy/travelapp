package com.pjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pjy.cache.MybatisPlusRedisCache;
import com.pjy.model.entity.Province;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @author admin
 * @description 针对表【t_province】的数据库操作Mapper
 * @createDate 2022-06-23 01:10:05
 * @Entity com.pjy.model.entity.Province
 */

@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface ProvinceMapper extends BaseMapper<Province> {

}




