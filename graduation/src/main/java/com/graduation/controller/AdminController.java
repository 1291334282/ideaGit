package com.graduation.controller;

import com.graduation.entity.*;
import com.graduation.enums.CodeEnum;
import com.graduation.handler.FileUtil;
import com.graduation.service.OrderService;
import com.graduation.service.ProductService;
import com.graduation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Api(tags = "管理员接口")
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @ApiOperation("功能：删除个人信息(备注：管理员使用，需要传入user的id)")
    @PostMapping("/deleteUserById")
    public ResultUtil deleteuser(@RequestParam(value = "id", required = true) Integer id) {
        if (userService.removeById(id))
            return ResultUtil.success(null, CodeEnum.DELETE_SUCCESS.msg(), CodeEnum.DELETE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.DELETE_FAIL.val(), CodeEnum.DELETE_FAIL.msg());
    }

    @ApiOperation("功能：查找全部个人信息(备注：管理员使用)")
    @GetMapping("/findAllUser")
    public ResultUtil selectuser() {
        return ResultUtil.success(userService.selectuser());
    }

    @ApiOperation("功能：修改订单状态,把未发货改成已发货(备注：管理员使用，需要传入orders的id")
    @PostMapping("/updateOrdersStatus")
    public ResultUtil updateAddress(@RequestParam(value = "id", required = true) Integer id) {
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus("已发货");
        if (orderService.updateById(orders))
            return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
    }

    @ApiOperation("功能：查找全部订单(备注：管理员使用)")
    @GetMapping("/findAllOrders")
    public ResultUtil selectorder() {
        return ResultUtil.success(orderService.selectorder());
    }

    @ApiOperation("功能：上架商品(备注：管理员使用,name,price,stcok,categoryleveloneId,categoryleveltwoId为必填项,图片必须上传，fileName不用填)")
    @PostMapping("/addProduct")
    public ResultUtil addProduct(Product product, @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();//获取文件名
        String filepath = FileUtil.getUploadPath();
        if (!file.isEmpty()) {
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filepath + File.separator + fileName)))) {
                out.write(file.getBytes());
                out.flush();
                product.setFileName(fileName);
                productService.save(product);
                return ResultUtil.success(fileName, CodeEnum.ADD_SUCCESS.msg(), CodeEnum.ADD_SUCCESS.val());
            } catch (FileNotFoundException e) {
                return ResultUtil.fail(CodeEnum.ADD_FAIL.val(), CodeEnum.ADD_FAIL.msg());
            } catch (IOException e) {
                return ResultUtil.fail(CodeEnum.ADD_FAIL.val(), CodeEnum.ADD_FAIL.msg());
            }
        } else {
            return ResultUtil.fail(CodeEnum.FILE_EMPTY.val(), CodeEnum.FILE_EMPTY.msg());
        }
    }

    @ApiOperation("功能：下架商品(备注：管理员使用，需要传入product的id)")
    @PostMapping("/deleteProductById")
    public ResultUtil deleteProduct(@RequestParam(value = "id", required = true) Integer id) {
        if (productService.removeById(id))
            return ResultUtil.success(null, CodeEnum.DELETE_SUCCESS.msg(), CodeEnum.DELETE_SUCCESS.val());
        return ResultUtil.fail(CodeEnum.DELETE_FAIL.val(), CodeEnum.DELETE_FAIL.msg());
    }

    @ApiOperation("功能：修改商品信息(备注：管理员使用，需要传入product的id,选择性传入其他信息")
    @PostMapping("/updateProduct")
    public ResultUtil updateAddress(Product product, MultipartFile file) {
        if (file == null) {
            productService.updateById(product);
            return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
        }
        if (!file.isEmpty()) {
            String filepath = FileUtil.getUploadPath();
            String fileName = file.getOriginalFilename();//获取文件名
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filepath + File.separator + fileName)))) {
                out.write(file.getBytes());
                out.flush();
                product.setFileName(fileName);
                productService.updateById(product);
                return ResultUtil.success(null, CodeEnum.UPDATE_SUCCESS.msg(), CodeEnum.UPDATE_SUCCESS.val());
            } catch (FileNotFoundException e) {
                return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
            } catch (IOException e) {
                return ResultUtil.fail(CodeEnum.UPDATE_FAIL.val(), CodeEnum.UPDATE_FAIL.msg());
            }
        }else {
            return ResultUtil.fail(CodeEnum.FILE_EMPTY.val(), CodeEnum.FILE_EMPTY.msg());
        }


    }
}
