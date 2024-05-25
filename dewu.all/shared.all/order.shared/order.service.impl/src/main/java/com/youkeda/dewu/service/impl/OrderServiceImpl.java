package com.youkeda.dewu.service.impl;

import com.youkeda.comment.model.Paging;
import com.youkeda.comment.model.User;
import com.youkeda.dewu.dao.OrderDAO;
import com.youkeda.dewu.dataobject.OrderDO;
import com.youkeda.dewu.model.Order;
import com.youkeda.dewu.model.OrderStatus;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.param.QueryOrderParam;
import com.youkeda.dewu.service.OrderService;
import com.youkeda.dewu.service.ProductDetailService;
import com.youkeda.dewu.service.ProductService;
import com.youkeda.comment.service.UserService;
import com.youkeda.dewu.util.OrderDOUtil;
import com.youkeda.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private RedissonClient redisson;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ProductDetailService productDetailService;
    @DubboReference(version = "${user.service.version}")
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Override
    public Order add(Order order) {

        if (order == null) {
            return null;
        }

        order.setId(UUIDUtils.getUUID());
        order.setStatus(OrderStatus.WAIT_BUYER_PAY);
        //生成唯一订单号
        order.setOrderNumber(createOrderNumber());
        int insert = orderDAO.insert(OrderDOUtil.toDO(order));
        if (insert == 1) {
            return order;
        }
        return null;
    }

    @Override
    public Paging<Order> queryRecentPaySuccess(QueryOrderParam queryOrderParam) {
        Paging<Order> result = new Paging<>();

        if (queryOrderParam == null) {
            queryOrderParam = new QueryOrderParam();
        }

        int counts = orderDAO.selectCounts(queryOrderParam);
        if (counts < 1) {
            result.setTotalCount(0);
            return result;
        }
        List<OrderDO> orderDOS = orderDAO.pageQuery(queryOrderParam);
        List<Order> orders = new ArrayList<>();
        List<String> productDetailIds = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();

        orderDOS.forEach(orderDO -> {
            orders.add(OrderDOUtil.toModel(orderDO));
            productDetailIds.add(orderDO.getProductDetailId());
            userIds.add(Long.parseLong(orderDO.getUserId()));
        });

        List<User> users = userService.findByIds(userIds);
        List<ProductDetail> productDetails = productDetailService.queryProductDetail(productDetailIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, t -> t));
        Map<String, ProductDetail> productDetailMap = productDetails.stream().collect(
                Collectors.toMap(ProductDetail::getId, t -> t));

        orders.forEach(order -> {
            order.setProductDetail(productDetailMap.get(order.getProductDetailId()));
            order.setUser(userMap.get(Long.parseLong(order.getUserId())));
        });
        result.setData(orders);
        result.setTotalCount(counts);
        result.setPageNum(orders.size());
        result.setPageSize(queryOrderParam.getPageSize());
        result.setTotalPage(orders.size() / queryOrderParam.getPageNum());
        return result;
    }

    @Override
    public Order getByOrderNumber(String orderNumber) {
        if (StringUtils.isBlank(orderNumber)) {
            return null;
        }
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        return orderDO != null ? OrderDOUtil.toModel(orderDO) : null;
    }

    @Override
    public Order updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);

        if (orderDO == null) {
            return null;
        }

        orderDO.setStatus(orderStatus.toString());
        orderDO.setStatus(String.valueOf(orderStatus));
        orderDAO.update(orderDO);
        return OrderDOUtil.toModel(orderDO);
    }

    @Override
    public Order updateProductPersonNumber(String orderNumber) {
        if (StringUtils.isBlank(orderNumber)) {
            return null;
        }

        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        if (orderDO == null) {
            return null;
        }
        // 获取分布式锁
        RLock lock = redisson.getLock("PURCHASE");
        lock.lock();
        try {
            ProductDetail productDetail = productDetailService.get(orderDO.getProductDetailId());
            if (productDetail == null) {
                return null;
            }
            productDetail.setStock(productDetail.getStock() - 1);
            productDetailService.save(productDetail);
            Product product = productService.get(productDetail.getProductId());
            product.setPurchaseNum(product.getPurchaseNum() + 1);
            productService.save(product);
        }catch (Exception e) {
            LOG.error("",e);
        }finally {
            lock.unlock();
        }
        return OrderDOUtil.toModel(orderDO);
    }

    /**
     * 生成唯一订单号
     *
     * @return String
     */
    private String createOrderNumber() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String date = dtf2.format(localDateTime);
        RAtomicLong aLong = redisson.getAtomicLong("myOrderPartNum" + date);
        aLong.expire(10, TimeUnit.SECONDS);
        long count = aLong.incrementAndGet();
        String orderId = date + count;
        return orderId;
    }
}
