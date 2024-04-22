package com.pointedInquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pointedInquery.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Update("update `order` set state=#{newState} where order_id=#{orderId}")
    boolean updateOrderStatus(String newState, String orderId);

    @Update("UPDATE `order` SET state='已取消' WHERE order_id=#{orderId}")
    int cancleOrder(String orderId);

}
