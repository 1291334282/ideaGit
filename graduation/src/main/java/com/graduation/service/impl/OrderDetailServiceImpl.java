package com.graduation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.entity.OrderDetail;
import com.graduation.entity.Product;
import com.graduation.mapper.OrderDetailMapper;
import com.graduation.mapper.OrderMapper;
import com.graduation.mapper.ProductMapper;
import com.graduation.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.service.ProductService;
import com.graduation.vo.OrderByNameVO;
import com.graduation.vo.OrderDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 叼大
 * @since 2020-12-07
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductService productService;

    @Override
    public List<OrderDetailVO> selestOrderDetail(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_id", id);
        List<OrderDetail> orderDetail = orderDetailMapper.selectList(wrapper);
        List<OrderDetailVO> list = new ArrayList<>();
        for (OrderDetail orderDetail1 : orderDetail) {
            OrderDetailVO orderDetailVOS = new OrderDetailVO();
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", orderDetail1.getProductId());
            BeanUtils.copyProperties(orderDetail1, orderDetailVOS);
            orderDetailVOS.setProducts(productMapper.selectList(queryWrapper));
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("id", id);
            orderDetailVOS.setOrders(orderMapper.selectList(queryWrapper2));
            list.add(orderDetailVOS);

            System.out.println(orderDetailVOS);
            System.out.println(list);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<OrderByNameVO> selestOrderByName(String name, Integer id) {
        List<Product> products = productService.findProductByname(name);
        List<OrderByNameVO> list = new ArrayList<>();
        for (Product product : products) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("product_id", product.getId());
            List<OrderDetail> orderDetail = orderDetailMapper.selectList(wrapper);
            for (OrderDetail detail : orderDetail) {
                OrderByNameVO orderByNameVO = new OrderByNameVO();
                BeanUtils.copyProperties(detail, orderByNameVO);
                orderByNameVO.setProduct(product);
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("user_id", id);
                if (!orderMapper.selectList(queryWrapper).isEmpty())
                    list.add(orderByNameVO);
            }
        }
        return list;
    }
}
