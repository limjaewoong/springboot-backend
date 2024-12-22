package net.ljw.backend.controller;

import net.ljw.backend.entity.Items;
import net.ljw.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping(value = "/api/items")
    public List<Items> items(){
        return itemRepository.findAll();
    }
}
