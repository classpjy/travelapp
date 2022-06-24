package com.pjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjy.model.entity.Attractions;
import com.pjy.mapper.AttractionsMapper;
import com.pjy.service.AttractionsService;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【t_attractions】的数据库操作Service实现
* @createDate 2022-06-23 01:10:05
*/
@Service
public class AttractionsServiceImpl extends ServiceImpl<AttractionsMapper, Attractions>
    implements AttractionsService{

}




