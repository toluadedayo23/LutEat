package com.practise.luteat.controller;

import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.service.MenuOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/menuOrders")
public class MenuOrdersController {

    private final MenuOrderService menuOrderService;

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<MenuOrderDto>> getAllMenu(){
        return ResponseEntity.status(HttpStatus.OK).body(menuOrderService.getAllMenu());
    }

    @PostMapping("/update-menu")
    public ResponseEntity<MenuOrderDto> updateMenu(@Valid @RequestBody MenuOrderDto menuOrderDto){
        return ResponseEntity.status(HttpStatus.OK).body(menuOrderService.updateMenu(menuOrderDto));
    }

    @PostMapping("/delete-menu")
    public ResponseEntity<String>deleteMenu(@Valid @RequestBody MenuOrderDto menuOrderDto){
        menuOrderService.deleteMenu(menuOrderDto);
        return new ResponseEntity<>("Menu has been successfully deleted", HttpStatus.OK);
    }

    @PostMapping("/create-menu")
    public ResponseEntity<MenuOrderDto> createMenu(@Valid @RequestBody MenuOrderDto menuOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(menuOrderService.createMenu(menuOrderDto));
    }
}
