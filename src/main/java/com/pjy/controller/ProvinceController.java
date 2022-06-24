package com.pjy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjy.model.entity.Province;
import com.pjy.model.vo.Result;
import com.pjy.service.ProvinceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@Api(tags = "省份服务")
@RequestMapping("province")
public class ProvinceController {


    @Resource
    private ProvinceService provinceService;

    @GetMapping("findOneProvince")
    @ApiOperation("通过id查询省份接口")
    public Province findOneProvince(Integer id) {
        log.info("查询省份id为: "+id);
        return provinceService.getById(id);
    }

    @SneakyThrows
    @PostMapping("update")
    @ApiOperation("修改省份接口")
    public Result update(@RequestBody Province province) {
        log.info("修改省份信息为: " + new ObjectMapper().writeValueAsString(province));
        provinceService.updateById(province);
        return new Result(true, "更新成功");
    }

    @GetMapping("delete")
    @ApiOperation("删除身份接口")
    public Result delete(Integer id) {
        //判断当前省份是否有景点
        if (provinceService.getById(id).getAttractionsCount() == 0) {
            provinceService.removeById(id);
            return new Result(true, "删除成功");
        } else
            return new Result(false, "请先删除该省份下的所有景点");
    }

    @PostMapping("save")
    @ApiOperation("添加省份接口")
    public Result save(@RequestBody Province province) {
        //填充数据
        province.setAttractionsCount(0);
        provinceService.save(province);
        return new Result(true, "保存成功");
    }

    @GetMapping("allProvince")
    @ApiOperation("分页查询所有省份接口")
    public Map<String, Object> allProvince(@RequestParam(name = "page", defaultValue = "1") Integer start,
                                           @RequestParam(name = "size", defaultValue = "5") Integer size) {
        log.info("当前页: " + start);
        log.info("每页条数: " + size);
        //分页查询
        Map<String, Object> map = new HashMap<>();
        Page<Province> page = new Page<>(start, size);
        page = provinceService.page(page);
        List<Province> records = page.getRecords();
        map.put("province", records);
        long total = page.getTotal();
        long totalPage = total % size == 0 ? total / size : total / size + 1;
        map.put("totalPage", totalPage);
        map.put("totals", total);
        return map;
    }


}
