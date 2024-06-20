package com.pointedInquery;

import com.pointedInquery.PointedInqueryApplication;
import com.pointedInquery.dto.OrderCreateDto;
import com.pointedInquery.dto.OrderDetailedInfoDto;
import com.pointedInquery.entity.Order;
import com.pointedInquery.mapper.OrderMapper;
import com.pointedInquery.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PointedInqueryApplication.class)
@Transactional
@Rollback
class OrderServiceTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createOrder() {
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        // Set properties for orderCreateDto
        orderCreateDto.setUserId("1");
        orderCreateDto.setProductId("101");
        orderCreateDto.setQuantity(2);

        Order order = orderService.createOrder(orderCreateDto);
        assertNotNull(order);
        assertEquals(orderCreateDto.getUserId(), order.getUserId());
        assertEquals(orderCreateDto.getProductId(), order.getProductId());
        assertEquals(orderCreateDto.getQuantity(), order.getQuantity());
    }

    @Test
    void createOrderWithNullUserId() {
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setUserId(null);
        orderCreateDto.setProductId("101");
        orderCreateDto.setQuantity(2);

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(orderCreateDto);
        });
    }

    @Test
    void createOrderWithEmptyProductId() {
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setUserId("1");
        orderCreateDto.setProductId("");
        orderCreateDto.setQuantity(2);

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(orderCreateDto);
        });
    }

    @Test
    void getOrderById() {
        String orderId = "1";
        OrderDetailedInfoDto order = orderService.getOrderById(orderId);
        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
    }

    @Test
    void getOrderByIdWithNonexistentId() {
        String orderId = "nonexistent";
        OrderDetailedInfoDto order = orderService.getOrderById(orderId);
        assertNull(order);
    }

    @Test
    void getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    void updateOrder() {
        String orderId = "1";
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        // Set new properties for orderCreateDto
        orderCreateDto.setProductId("102");
        orderCreateDto.setQuantity(3);

        Order order = orderService.updateOrder(orderId, orderCreateDto);
        assertNotNull(order);
        assertEquals(orderCreateDto.getProductId(), order.getProductId());
        assertEquals(orderCreateDto.getQuantity(), order.getQuantity());
    }

    @Test
    void updateOrderWithNullId() {
        String orderId = null;
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        // Set new properties for orderCreateDto
        orderCreateDto.setProductId("102");
        orderCreateDto.setQuantity(3);

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.updateOrder(orderId, orderCreateDto);
        });
    }

    @Test
    void deleteOrder() {
        String orderId = "1";
        boolean result = orderService.deleteOrder(orderId);
        assertTrue(result);
    }

    @Test
    void deleteOrderWithNonexistentId() {
        String orderId = "nonexistent";
        boolean result = orderService.deleteOrder(orderId);
        assertFalse(result);
    }
}
