package com.keyduck.keyboard.controller;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import com.keyduck.keyboard.service.KeyboardService;
import com.keyduck.result.ResponseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @GetMapping("/keyboards/{keyboardId}")
    public ResponseEntity<?> getKeyboardDetail(@PathVariable Long keyboardId){
        KeyboardGetDto result =  keyboardService.getKeyboardDetail(keyboardId);
        return new ResponseEntity<>(responseService.getSingleResult(result), HttpStatus.OK);
    }

    @PostMapping("/keyboards/add")
    public ResponseEntity<?> addKeyboard(@RequestBody KeyboardCreateDto keyboard){
        KeyboardCreateDto result = keyboardService.addKeyboard(keyboard);
        return new ResponseEntity<>(responseService.getSingleResult(result), HttpStatus.OK);
    }

    @DeleteMapping("/keyboards/delete")
    public ResponseEntity<?> deleteKeyboard( @RequestBody String model){
        String result = keyboardService.deleteKeyboard(model);
        return new ResponseEntity<>(responseService.getSingleResult(result),HttpStatus.OK);
    }

    @PostMapping("/keyboards")
    public ResponseEntity<?> searchKeyboard(@RequestBody KeyboardSearchDto searchKeywords){
        List<Keyboard> result = keyboardService.searchKeyboard(searchKeywords);
        return new ResponseEntity<>(responseService.getSingleResult(result),HttpStatus.OK);
    }


}
