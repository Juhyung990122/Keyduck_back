package com.keyduck.keyboard.controller;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import com.keyduck.keyboard.service.KeyboardService;
import com.keyduck.result.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@Api(tags = "Keyboard", value = "KeyduckController v1")
public class KeyboardController {

    private final KeyboardService keyboardService;
    private final ResponseService responseService;

    @ApiOperation(value = "키보드 전체 조회", notes = "현재 있는 키보드 전체를 조회합니다.")
    @GetMapping("/keyboards")
    public ResponseEntity<?> getAllKeyboards(){
        List<KeyboardGetDto> result = keyboardService.getAllKeyboards();
        return new ResponseEntity<>(responseService.getListResult(result),HttpStatus.OK);
    }

    @ApiOperation(value = "키보드 디테일 조회", notes = "선택한 키보드의 상세정보를 조회합니다.")
    @GetMapping("/keyboards/{keyboardId}")
    public ResponseEntity<?> getKeyboardDetail(@PathVariable Long keyboardId){
        KeyboardGetDto result =  keyboardService.getKeyboardDetail(keyboardId);
        return new ResponseEntity<>(responseService.getSingleResult(result), HttpStatus.OK);
    }

    @ApiOperation(value = "키보드 추가", notes = "새로운 키보드를 추가합니다.")
    @PostMapping("/keyboards/add")
    public ResponseEntity<?> addKeyboard(@RequestBody KeyboardCreateDto keyboard){
        KeyboardCreateDto result = keyboardService.addKeyboard(keyboard);
        return new ResponseEntity<>(responseService.getSingleResult(result), HttpStatus.OK);
    }

    @ApiOperation(value = "키보드 삭제", notes = "선택한 키보드를 삭제합니다.")
    @DeleteMapping("/keyboards/delete/{keyboardId}")
    public ResponseEntity<?> deleteKeyboard(@PathVariable Long keyboardId){
        String result = keyboardService.deleteKeyboard(keyboardId);
        return new ResponseEntity<>(responseService.getSingleResult(result),HttpStatus.OK);
    }

    @ApiOperation(value = "키보드 검색", notes = "전달된 조건을 통해 키보드를 검색합니다")
    @PostMapping("/keyboards")
    public ResponseEntity<?> searchKeyboard(@RequestBody KeyboardSearchDto searchKeyboards){
        List<KeyboardGetDto> result = keyboardService.searchKeyboard(searchKeyboards);
        return new ResponseEntity<>(responseService.getSingleResult(result),HttpStatus.OK);
    }

    @ApiOperation(value = "키보드 필터", notes = "키워드(#달린것들) 통해 키보드를 필터링합니다.")
    @PostMapping("/keyboards/filter")
    public ResponseEntity<?> filterKeyboard(@RequestBody HashMap<String,String> keywords){
        List<Keyboard> result = keyboardService.filterByCategoryKeyboard(keywords);
        return new ResponseEntity<>(responseService.getListResult(result),HttpStatus.OK);
    }

    @ApiOperation(value = "키보드 필터", notes = "키워드(#달린것들) 통해 키보드를 필터링합니다.")
    @PostMapping("/keyboards/filter/23")
    public ResponseEntity<?> searchKeyboardWhileResult(@RequestBody HashMap<String,String> searchKeywords){
        List<List<KeyboardGetDto>> result = null;
        String[] priority = {"arrangement","price","switchColor","brand"};
        List<String> priorityList =  Arrays.asList(priority);

        while(result == null || priorityList.size() > 0){
            // 하나라도 비면 안됨. 숫자는 0 문자는  null 로 바꿔줘야함.
            KeyboardSearchDto search = KeyboardSearchDto.KeyboardSearchDtoBuilder()
                    .arrangement(Integer.parseInt(searchKeywords.get("arrangement")))
                    .startPrice(Integer.parseInt(searchKeywords.get("startPrice")))
                    .endPrice(Integer.parseInt(searchKeywords.get("endPrice")))
                    .switchColor(new String[]{searchKeywords.get("switchColor")})
                    .brand(searchKeywords.get("brand"))
                    .build();
        }

        return new ResponseEntity<>(responseService.getListResult(result),HttpStatus.OK);
    }
}
