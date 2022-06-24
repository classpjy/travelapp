package com.pjy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Producer;
import com.pjy.model.entity.User;
import com.pjy.model.vo.Result;
import com.pjy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
//允许跨域
@CrossOrigin
@Slf4j
@Api(tags = "用户服务")
public class UserController {

    @Resource
    private Producer kaptchaProducer;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userService;

    @GetMapping("checkToken")
    @ApiOperation("验证用户是否登录接口")
    public Result checkToken(String token) {
        log.info("用户令牌为: " + token);
        if (redisTemplate.hasKey(token)) {
            return new Result(true, "该用户存在");
        } else
            return new Result(false, "请重新登录");
    }

    @SneakyThrows
    @PostMapping("login")
    @ApiOperation("用户登录接口")
    public Result login(@RequestBody User user, HttpSession session) {
        log.info("用户信息为 : " + new ObjectMapper().writeValueAsString(user));
        //查询用户是否存在
        User userInfo = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (ObjectUtils.isEmpty(userInfo)) return new Result(false, "用户不存在请先去注册");
        if (!user.getPassword().equals(userInfo.getPassword())) return new Result(false, "用户密码错误请联系管理员找回密码");
        //生成用户令牌
        String token = "token_" + session.getId();
        redisTemplate.opsForValue().set(token, userInfo, Duration.ofMinutes(30));
        return new Result(true, "登录成功", token, userInfo.getUsername());
    }


    @SneakyThrows
    @PostMapping("register")
    @ApiOperation("保存用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "用户输入的验证码"),
    })
    public Result register(String code, @RequestBody User user) {
        log.info("code为: " + code);
        log.info("用户信息为 : " + new ObjectMapper().writeValueAsString(user));
        Map<String, Object> map = new HashMap<>();
        //效验验证码
        String kaptcha = stringRedisTemplate.opsForValue().get("kaptcha");
        if (StringUtils.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
            return new Result(false, "验证码错误请刷新验证码重试");
        }
        //查询数据库中是否有该用户
        User username = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (!ObjectUtils.isEmpty(username)) {
            return new Result(false, "该用户名以存在");
        }
        //保存用户信息
        userService.save(user);
        return new Result(true, "保存用户成功");
    }


    @SneakyThrows
    @GetMapping("/getImage")
    @ApiOperation(value = "设置验证码接口")
    public String getKaptcha() {
        //调用KaptchaConfig配置类里的kaptchaProducer的Producer接口中的生成干扰后的文本方法
        String text = kaptchaProducer.createText();
        log.info("验证码为: " + text);
        //将文本转变为图片
        BufferedImage image = kaptchaProducer.createImage(text);
        //存放验证码到redis中 3分钟自动过期
        stringRedisTemplate.opsForValue().set("kaptcha", text, Duration.ofMinutes(3));
        //对图片进行bast64
        ByteArrayOutputStream bufferedImage = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bufferedImage);
        String str = Base64.getEncoder().encodeToString(bufferedImage.toByteArray());
        //释放资源
        bufferedImage.close();
        return str;
    }
}
