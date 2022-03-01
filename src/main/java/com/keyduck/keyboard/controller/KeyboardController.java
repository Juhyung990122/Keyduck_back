package com.keyduck.keyboard.controller;

import com.keyduck.keyboard.domain.Tag;
import com.keyduck.keyboard.dto.*;
import com.keyduck.keyboard.service.KeyboardService;
import com.keyduck.result.ListResult;
import com.keyduck.result.ResponseService;
import com.keyduck.result.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    public ResponseEntity<SingleResult<KeyboardCreateDto>> addKeyboard(@RequestBody KeyboardCreateDto keyboard) throws ParseException {
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


    @ApiOperation(value = "결과 나올때까지 키보드 필터", notes = "조건을 소거해가며 결과가 하나라도 나올때까지 필터링합니다. 하나도 없을 시 전체 키보드를 반환합니다.")
    @PostMapping("/keyboards/filter")
    public ResponseEntity<ListResult<SimpleKeyboardDto>> filterKeyboardWhileResult(@RequestBody HashMap<String,String> filterKeywords){
        List<SimpleKeyboardDto> result = new ArrayList<>();
        List<String> priorityList = new ArrayList<>(Arrays.asList("switchColor", "connection", "price", "weight", "arrangement", "led"));

        while(result.size() == 0){

            result = keyboardService.filterWhileResult(filterKeywords);
            String now = priorityList.get(priorityList.size() - 1);
            if(now.equals("price")){
                filterKeywords.replace("startPrice","-1");
                filterKeywords.replace("endPrice","-1");
            }
            else{
                filterKeywords.replace(now,"");
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

    @ApiOperation(value = "최근 등록된 키보드 10개를 불러옵니다.")
    @GetMapping("/keyboards/recent")
    public ResponseEntity<List<SimpleKeyboardDto>> getRecentTenKeyboard(){
        List<SimpleKeyboardDto> result = keyboardService.findRecent();
        return ResponseEntity
                .ok()
                .body(result);
    }

    @ApiOperation(value = "최근 등록된 키보드 10개를 불러옵니다.")
    @GetMapping("/keyboards/recommend")
    public ResponseEntity<List<RecommendKeyboardDto>> getRecommandKeyboard(){
        List<RecommendKeyboardDto> result = keyboardService.getRecommend();
        return ResponseEntity
                .ok()
                .body(result);
    }

    @ApiOperation(value = "태그를 추가합니다(헤로쿠 서버에서만 사용)")
    @PostMapping("/keyboards/addTag")
    public List<Tag> addTag(@RequestBody HashMap<String,String> st){
        List<Tag> result = keyboardService.addTag(st);
        return result;
    }
}
