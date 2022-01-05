package com.keyduck.keyboard.controller;

import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.KeyboardSearchDto;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.keyboard.service.KeyboardService;
import com.keyduck.result.ListResult;
import com.keyduck.result.ResponseService;
import com.keyduck.result.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ListResult<SimpleKeyboardDto>> getAllKeyboards(){
        List<SimpleKeyboardDto> result = keyboardService.getAllKeyboards();
        return ResponseEntity
                .ok()
                .body(responseService.getListResult(result));
    }

    @ApiOperation(value = "키보드 디테일 조회", notes = "선택한 키보드의 상세정보를 조회합니다.")
    @GetMapping("/keyboards/{keyboardId}")
    public ResponseEntity<SingleResult<KeyboardGetDto>> getKeyboardDetail(@PathVariable Long keyboardId){
        KeyboardGetDto result =  keyboardService.getKeyboardDetail(keyboardId);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }

    @ApiOperation(value = "키보드 추가", notes = "새로운 키보드를 추가합니다.")
    @PostMapping("/keyboards/add")
    public ResponseEntity<SingleResult<KeyboardCreateDto>> addKeyboard(@RequestBody KeyboardCreateDto keyboard){
        System.out.println("post request");
        KeyboardCreateDto result = keyboardService.addKeyboard(keyboard);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }

    @ApiOperation(value = "키보드 삭제", notes = "선택한 키보드를 삭제합니다.")
    @DeleteMapping("/keyboards/delete/{keyboardId}")
    public ResponseEntity<SingleResult<String>> deleteKeyboard(@PathVariable Long keyboardId){
        String result = keyboardService.deleteKeyboard(keyboardId);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }

    @ApiOperation(value = "키보드 검색", notes = "전달된 조건을 통해 키보드를 검색합니다")
    @PostMapping("/keyboards")
    public ResponseEntity<SingleResult<List<SimpleKeyboardDto>>> searchKeyboard(@RequestBody KeyboardSearchDto searchKeyboards){
        List<SimpleKeyboardDto> result = keyboardService.searchKeyboard(searchKeyboards);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }


    @ApiOperation(value = "결과 나올때까지 키보드 필터", notes = "만드는 중")
    @PostMapping("/keyboards/filter")
    public ResponseEntity<ListResult<SimpleKeyboardDto>> searchKeyboardWhileResult(@RequestBody HashMap<String,String> searchKeywords){
        List<SimpleKeyboardDto> result = new ArrayList<>();
        List<String> priorityList = new ArrayList<>(Arrays.asList("arrangement", "price", "switchColor", "brand"));

        while(result.size() == 0){

            result = keyboardService.searchWhileResult(searchKeywords);
            String now = priorityList.get(priorityList.size() - 1);
            if(now.equals("price")){
                searchKeywords.replace("startPrice","-1");
                searchKeywords.replace("endPrice","-1");
            }
            else{
                searchKeywords.remove(now);
            }
            priorityList.remove(priorityList.size()-1);

            if(priorityList.size() == 0){
                result = keyboardService.getAllKeyboards();
                break;
            }
        }

        return ResponseEntity
                .ok()
                .body(responseService.getListResult(result));
    }
}
