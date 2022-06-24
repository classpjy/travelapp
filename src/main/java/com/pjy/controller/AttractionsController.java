package com.pjy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjy.model.entity.Attractions;
import com.pjy.model.entity.Province;
import com.pjy.model.vo.Result;
import com.pjy.service.AttractionsService;
import com.pjy.service.ProvinceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "景点服务")
@RequestMapping("place")
public class AttractionsController {
    @Resource
    private AttractionsService attractionsService;
    @Resource
    private ProvinceService provinceService;
    @Value("${upload.dir}")
    private String realPath;

    @SneakyThrows
    @PostMapping("update")
    public Result update(MultipartFile pic, Attractions place, String onlProvinceId) {
        log.info("修改景点详情: " + new ObjectMapper().writeValueAsString(place));
        //是否需要上传文件
        if (!ObjectUtils.isEmpty(pic)) {
            place.setPicPath(Base64Utils.encodeToString(pic.getBytes()));
            log.info("旧省份id为: " + onlProvinceId);
            //文件上传
            String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + FilenameUtils.getExtension(pic.getOriginalFilename());
            log.info("文件名为: " + newFileName);
            log.info("上传路径为: " + realPath);
            pic.transferTo(new File(realPath, newFileName));
        }
        //是否需要修改省份
        if (!Objects.equals(place.getProvinceId(), onlProvinceId)) {
            //旧省份-1
            Province onlProvince = provinceService.getById(onlProvinceId);
            onlProvince.setAttractionsCount(onlProvince.getAttractionsCount() - 1);
            provinceService.updateById(onlProvince);
            //新省份+1
            Province newProvince = provinceService.getById(place.getProvinceId());
            newProvince.setAttractionsCount(newProvince.getAttractionsCount() + 1);
            provinceService.updateById(newProvince);
        }
        //修改景点信息
        attractionsService.updateById(place);
        return new Result(true, "修改成功");
    }

    @GetMapping("findOne")
    @ApiOperation("通过景点id查询景点接口")
    public Attractions findOne(Integer id) {
        log.info("查询景点id为: " + id);
        return attractionsService.getById(id);
    }


    @GetMapping("delete")
    @ApiOperation("删除景点接口")
    public Result delete(Integer id) {
        log.info("删除景点id为: " + id);
        //身份景点数量-1
        Province province = provinceService.getOne(new QueryWrapper<Province>().eq("id", attractionsService.getById(id).getProvinceId()));
        province.setAttractionsCount(province.getAttractionsCount() - 1);
        provinceService.updateById(province);
        attractionsService.removeById(id);
        return new Result(true, "删除成功");
    }


    @SneakyThrows
    @PostMapping("save")
    @ApiOperation("保存景点接口")
    public Result save(MultipartFile pic, Attractions attractions) {
        //设置属性
        attractions.setPicPath(Base64Utils.encodeToString(pic.getBytes()));
        //文件上传
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + FilenameUtils.getExtension(pic.getOriginalFilename());
        log.info("文件名为: " + newFileName);
        log.info("上传路径为: " + realPath);
        pic.transferTo(new File(realPath, newFileName));
        //保存景点
        attractionsService.save(attractions);
        //省份景点个数+1
        Province province = provinceService.getOne(new QueryWrapper<Province>().eq("id", attractions.getProvinceId()));
        province.setAttractionsCount(province.getAttractionsCount() + 1);
        provinceService.updateById(province);
        return new Result(true, "保存成功");
    }


    @ApiOperation("根据省份id分页查询景点接口")
    @GetMapping("allPlace")
    public Map<String, Object> allPlace(@RequestParam(name = "page", defaultValue = "1") Integer start,
                                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                                        @RequestParam(name = "provinceid") Integer id) {
        log.info("当前页: " + start);
        log.info("每页条数: " + size);
        log.info("省份id为: " + id);
        Map<String, Object> map = new HashMap<>();
        Page<Attractions> page = new Page<>(start, size);
        page = attractionsService.page(page, new QueryWrapper<Attractions>().eq("province_id", id).orderBy(true, true, "hot_time"));
        List<Attractions> records = page.getRecords();
        long total = page.getTotal();
        long totalPage = total % size == 0 ? total / size : total / size + 1;
        map.put("places", records);
        map.put("totalPage", totalPage);
        map.put("totals", total);
        return map;
    }
}
