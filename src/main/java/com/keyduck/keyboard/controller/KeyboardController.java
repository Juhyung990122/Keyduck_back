package com.keyduck.keyboard.controller;

import com.keyduck.keyboard.service.KeyboardService;
import com.keyduck.result.ResponseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@Api(tags = "Keyboard", value = "KeyduckController v1")
public class KeyboardController {

    private final KeyboardService keyboardService;
    private final ResponseService responseService;

    @GetMapping("/keyboard")
    public ResponseEntity<?> searchKeyboard(){

        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }


}
