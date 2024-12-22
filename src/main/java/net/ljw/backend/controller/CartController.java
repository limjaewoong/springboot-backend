package net.ljw.backend.controller;

import net.ljw.backend.entity.Cart;
import net.ljw.backend.entity.Items;
import net.ljw.backend.repository.CartRepository;
import net.ljw.backend.repository.ItemRepository;
import net.ljw.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/api/cart/items/{itemId}")
    public ResponseEntity<Object> insCartItem(
            @PathVariable(name = "itemId",required = true) int itemId
            ,@CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
        if(cart == null) {
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping(value = "/api/cart/items/cartItemList")
    public ResponseEntity<Object> cartItemList(
            @CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);

        List<Cart> cartList = cartRepository.findByMemberId(memberId);
        List<Integer> itemsList = cartList.stream().map(Cart::getItemId).toList();
        return new ResponseEntity<>(itemRepository.findByIdIn(itemsList), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/cart/delete/{itemId}")
    public ResponseEntity<Object> delCartItem(
            @PathVariable(name = "itemId",required = true) int itemId
            ,@CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
        cartRepository.delete(cart);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
