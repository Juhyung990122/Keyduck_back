package com.keyduck.keyboard.controller;

import com.keyduck.keyboard.KeyboardService;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.result.ResponseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@Api(tags = "Keyboard", value = "KeyduckController v1")
public class KeyboardController {

    private final KeyboardService keyboardService;
    private final ResponseService responseService;

    @GetMapping("/keyboards")
    public ResponseEntity<?> getAllKeyboards(){
        return new ResponseEntity<>(responseService.getSuccessResult(),HttpStatus.OK);
    }

    @GetMapping("/keyboards/{model}")
    public ResponseEntity<?> searchKeyboard(@PathVariable String model){
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }

    @PostMapping("/keyboards/add")
    public ResponseEntity<?> addKeyboard(@RequestBody KeyboardCreateDto keyboard){
        keyboardService.addKeyboards(keyboard);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }

    @DeleteMapping("/keyboards/{model}")
    public ResponseEntity<?> deleteKeyboard(@PathVariable String model){
        return new ResponseEntity<>(responseService.getSuccessResult(),HttpStatus.OK);
    }



}
