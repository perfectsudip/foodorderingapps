package com.foodorderingapp.test;

import com.foodorderingapp.dao.OrderDAO;
import com.foodorderingapp.dto.FoodQuantity;
import com.foodorderingapp.dto.OrderDto;
import com.foodorderingapp.dto.UserListMapperDto;
import com.foodorderingapp.exception.DataNotFoundException;
import com.foodorderingapp.model.*;
import com.foodorderingapp.service.FoodService;
import com.foodorderingapp.service.OrderDetailService;
import com.foodorderingapp.service.UserService;
import com.foodorderingapp.serviceImpl.OrdersServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class OrderFailTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private FoodService foodService;

    @Mock
    private OrderDetailService orderDetailService;

    @Mock
    private UserService userService;

    @InjectMocks
    OrdersServiceImpl ordersService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = DataNotFoundException.class)
    public void testAdd(){

        User user=new User();

        Orders orders=new Orders();

        Food food=new Food("momo",100,new Restaurant());
        OrderDto orderDto=new OrderDto(1,new ArrayList<>());

        Restaurant restaurant=new Restaurant("f1soft","hattisar","9817651648",new ArrayList<>());

        OrderDetail orderDetail=new OrderDetail();
        FoodQuantity foodQuantity=new FoodQuantity(food.getName(),food.getPrice(),restaurant.getName(),1);

        when(userService.getUser(orderDto.getUserId())).thenReturn(null);
        when(orderDAO.add(orders)).thenReturn(orders);
        when(foodService.getFoodByResName(foodQuantity.getFoodName(),foodQuantity.getRestaurantName())).thenReturn(food);
        when(userService.update(user)).thenReturn(user);
        when(orderDetailService.add(orderDetail)).thenReturn(orderDetail);
        Assert.assertNull(ordersService.add(orderDto));
    }

    @Test(expected = DataNotFoundException.class)
    public void getByUserId(){
        UserListMapperDto userListMapperDto=new UserListMapperDto();
        userListMapperDto.setOrderId(1);
        User user=new User();
        user.setUserId(1);
        when(userService.getByUserId(user.getUserId())).thenReturn(null);
        when(orderDetailService.getOrderDetailByOrderId(userListMapperDto.getOrderId())).thenReturn(new ArrayList<>());
        Assert.assertNull(ordersService.getByUserId(user.getUserId()));
    }

    @Test(expected = DataNotFoundException.class)
    public void getOrder(){
        Orders orders=new Orders();
        orders.setOrderId(1);
        when(orderDAO.getOrders()).thenReturn(null);
        when(orderDetailService.getOrderDetailByOrderId(orders.getOrderId())).thenReturn(new ArrayList<>());
        Assert.assertNull(ordersService.getOrder());
    }
}
