package com.graduation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.ResultUtil;
import com.graduation.entity.User;
import com.graduation.enums.CodeEnum;
import com.graduation.handler.FileUtil;
import com.graduation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/user")
@Api(tags = "登陆注册接口")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    Logger log = LoggerFactory.getLogger(UserController.class);

    @ApiOperation("功能：登录(备注：传入用户名loginName，密码password)")
    @PostMapping(value = "/login")
    public ResultUtil login(@RequestParam(value = "loginName", required = true) String loginName, @RequestParam(value = "password", required = true) String password) {
        //构造登录令牌
//        try {
//            UsernamePasswordToken upToken = new UsernamePasswordToken(loginName, password);
//            //1.获取subject
//            Subject subject = SecurityUtils.getSubject();
//            //2.调用subject进行登录
//            subject.login(upToken);
//            //把用户信息全存到session
//            QueryWrapper queryWrapper = new QueryWrapper();
//            queryWrapper.eq("login_name", loginName);
//            User user = userService.getOne(queryWrapper);
//            HttpSession session = request.getSession();
//            session.setAttribute("user", user);
//            //只返回登录名
//            return ResultUtil.success(loginName, CodeEnum.LOGIN_SUCCESS.msg(), CodeEnum.LOGIN_SUCCESS.val());
//        } catch (UnknownAccountException e) {
//            return ResultUtil.fail(CodeEnum.USER_NOT_EXIST.val(), CodeEnum.USER_NOT_EXIST.msg());
//        } catch (IncorrectCredentialsException e) {
//            return ResultUtil.fail(CodeEnum.PASSWORD_FAIL.val(), CodeEnum.PASSWORD_FAIL.msg());
//        }
        log.info("进入登录接口");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_name", loginName);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            return ResultUtil.fail(CodeEnum.USER_NOT_EXIST.val(), CodeEnum.USER_NOT_EXIST.msg());
        } else if (!user.getPassword().equals(password)) {
            return ResultUtil.fail(CodeEnum.PASSWORD_FAIL.val(), CodeEnum.PASSWORD_FAIL.msg());
        } else {
            //生成token，并保存到数据库
            return ResultUtil.success(userService.createToken(user.getId()), "登陆成功", "200");
        }
    }

    @ApiOperation("功能：注销(备注：传入token)")
    @PostMapping("/loginout")
    public ResultUtil loginout(@RequestHeader("token") String token) {
        userService.logout(token);
        return ResultUtil.success(null, CodeEnum.LOGINOUT_SUCCESS.msg(), CodeEnum.LOGINOUT_SUCCESS.val());
    }

    @ApiOperation("功能：注册(备注：id,createTime,updateTime为自动生成,不用输入)")
    @PostMapping("/register")
    public ResultUtil register(User user) {
        boolean result = false;
        try {
            result = userService.save(user);
        } catch (Exception e) {
            return ResultUtil.fail(CodeEnum.USER_IS_EXISTS.val(), user.getLoginName() + CodeEnum.USER_IS_EXISTS.msg());
        }
        if (result) return ResultUtil.success(null, CodeEnum.REGISTER_SUCCESS.msg(), CodeEnum.REGISTER_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.REGISTER_FAIL.val(), CodeEnum.REGISTER_FAIL.msg());
    }

    @ApiOperation("功能：显示个人信息")
    @GetMapping("/findUserOne")
    public ResultUtil userInfo(@RequestHeader("token") String token) {
        User user = userService.getById(userService.findByToken(token).getUserId());
        return ResultUtil.success(user);
    }

    @ApiOperation("功能：修改个人信息（token一定输入，id，loginName，filename，updatetime，createtime无需输入，其他选择输入）")
    @PutMapping("/updateUser")
    public ResultUtil updateuser(@RequestHeader("token") String token, User user2) {
        User user = userService.getById(userService.findByToken(token).getUserId());
        user2.setId(user.getId());
        if (userService.updateById(user2))
            return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
    }

//    @ApiOperation("功能：没有登陆时返回信息")
//    @GetMapping("/nologin")
//    public ResultUtil nogin() {
//        return ResultUtil.fail(CodeEnum.NO_LOGIN.val(), CodeEnum.NO_LOGIN.msg());
//    }
//
//    @ApiOperation("功能：没有权限时返回信息")
//    @GetMapping("/noauth")
//    public ResultUtil noauth() {
//        return ResultUtil.fail(CodeEnum.NO_AUTH.val(), CodeEnum.NO_AUTH.msg());
//    }

    @ApiOperation("功能：上传头像，备注（token一定输入，文件一定上传）")
    @PostMapping("/upload")
    public ResultUtil Upload(@RequestParam("file") MultipartFile file, @RequestHeader("token") String token) throws IOException {
        User user = userService.getById(userService.findByToken(token).getUserId());
        String fileName = file.getOriginalFilename();//获取文件名
//        String filepath = FileUtil.getUploadPath();
        String filepath = FileUtil.getUploadPath();
        log.info(filepath + File.separator + fileName);

        if (!file.isEmpty()) {
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filepath + File.separator + fileName)))) {
                out.write(file.getBytes());
                out.flush();
                user.setFileName(fileName);
                userService.updateById(user);
                return ResultUtil.success(fileName, CodeEnum.UPLOAD_SUCCESS.msg(), CodeEnum.UPLOAD_SUCCESS.val());
            } catch (FileNotFoundException e) {
                return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
            } catch (IOException e) {
                return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
            }
        } else {
            return ResultUtil.fail(CodeEnum.FILE_EMPTY.val(), CodeEnum.FILE_EMPTY.msg());
        }
    }

}

