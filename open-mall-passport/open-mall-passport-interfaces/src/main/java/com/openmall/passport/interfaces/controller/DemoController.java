package com.openmall.passport.interfaces.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxuan
 * @since 2024/7/3 22:00:32
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/get")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("hello world");
    }
}
