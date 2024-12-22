package net.ljw.backend.controller;

import net.ljw.backend.Dto.OrderDto;
import net.ljw.backend.entity.Cart;
import net.ljw.backend.entity.Order;
import net.ljw.backend.repository.CartRepository;
import net.ljw.backend.repository.ItemRepository;
import net.ljw.backend.repository.OrderRepository;
import net.ljw.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository OrderRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    @PostMapping(value = "/api/orders")
    public ResponseEntity<Object> pushOrder(
            @RequestBody OrderDto orderDto
            , @CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        Order newOrder = new Order();
        newOrder.setMemberId(memberId);
        newOrder.setName(orderDto.getName());
        newOrder.setAddress(orderDto.getAddress());
        newOrder.setPayment(orderDto.getPayment());
        newOrder.setCardNumber(orderDto.getCardNumber());
        newOrder.setItems(orderDto.getItems());

        orderRepository.save(newOrder);
        //장바구니 비우기
        cartRepository.deleteByMemberId(memberId);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/orders")
    public ResponseEntity<Object> getOrders(
            @CookieValue(value = "token", required = false) String token
    ){
        List<Order> orderList = orderRepository.findByMemberIdOrderByIdDesc(jwtService.getId(token));

        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
}
