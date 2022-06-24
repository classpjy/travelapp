package com.pjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pjy.model.entity.Attractions;
import org.apache.ibatis.annotations.CacheNamespaceRef;

/**
 * @author admin
 * @description 针对表【t_attractions】的数据库操作Mapper
 * @createDate 2022-06-23 01:10:05
 * @Entity com.pjy.model.entity.Attractions
 */
// 如果关联使用@CacheNamespaceRef
@CacheNamespaceRef(ProvinceMapper.class)
public interface AttractionsMapper extends BaseMapper<Attractions> {

}




