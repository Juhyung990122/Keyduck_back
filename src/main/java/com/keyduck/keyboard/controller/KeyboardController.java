package com.keyduck.keyboard.controller;

import com.keyduck.keyboard.KeyboardService;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.result.ResponseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@Api(tags = "Keyboard", value = "KeyduckController v1")
public class KeyboardController {

    private final KeyboardService keyboardService;
    private final ResponseService responseService;

    @GetMapping("/keyboards")
    public ResponseEntity<?> getAllKeyboards(){
        List<KeyboardGetDto> result = keyboardService.getAllKeyboards();
        return new ResponseEntity<>(responseService.getListResult(result),HttpStatus.OK);
    }

    @GetMapping("/keyboards/{model}")
    public ResponseEntity<?> getKeyboardDetail(@PathVariable String model){
        KeyboardGetDto result =  keyboardService.getKeyboardDetail(model);
        return new ResponseEntity<>(responseService.getSingleResult(result), HttpStatus.OK);
    }

    @PostMapping("/keyboards/add")
    public ResponseEntity<?> addKeyboard(@RequestBody KeyboardCreateDto keyboard){
        KeyboardCreateDto result = keyboardService.addKeyboard(keyboard);
        return new ResponseEntity<>(responseService.getSingleResult(result), HttpStatus.OK);
    }

    @DeleteMapping("/keyboards/{model}")
    public ResponseEntity<?> deleteKeyboard(@PathVariable String model){
        String result = keyboardService.deleteKeyboard(model);
        return new ResponseEntity<>(responseService.getSingleResult(result),HttpStatus.OK);
    }



}
