package com.practise.luteat.controller;

import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.service.MenuOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/menu-orders")
public class MenuOrdersController {

    private final MenuOrderService menuOrderService;

    @GetMapping
    public ResponseEntity<List<MenuOrderDto>> getAllMenu(){
        return ResponseEntity.status(HttpStatus.OK).body(menuOrderService.getAllMenu());
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuOrderDto> updateMenuPrice(@Valid @RequestBody MenuOrderDto menuOrderDto){
        return ResponseEntity.status(HttpStatus.OK).body(menuOrderService.updateMenuPrice(menuOrderDto));
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>deleteMenu(@PathVariable("name") String name){
        menuOrderService.deleteMenu(name);
        return new ResponseEntity<>("Menu has been successfully deleted", HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuOrderDto> createMenu(@Valid @RequestBody MenuOrderDto menuOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(menuOrderService.createMenu(menuOrderDto));
    }
}
