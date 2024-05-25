package com.youkeda.dewu.util;

import com.youkeda.dewu.dataobject.OrderDO;
import com.youkeda.dewu.model.Order;
import org.springframework.beans.BeanUtils;

public class OrderDOUtil {

    /**
     *
     * 模型 转换为 DO
     *
     * @param order
     * @return
     */
    public static OrderDO toDO(Order order) {
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(order, orderDO);
        return orderDO;
    }

    /**
     *
     * DO 转换为模型
     *
     * @param orderDO
     * @return
     */
    public static Order toModel(OrderDO orderDO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDO, order);
        return order;
    }
}
