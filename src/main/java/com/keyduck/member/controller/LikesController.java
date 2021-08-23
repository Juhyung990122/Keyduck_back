package com.keyduck.member.controller;

import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.member.service.LikesService;
import com.keyduck.result.ResponseService;
import com.keyduck.result.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@Api(tags = "Likes", value = "KeyduckController v1")
public class LikesController {
    private final LikesService likesService;
    private final ResponseService responseService;

    public LikesController(LikesService likesService, ResponseService responseService) {
        this.likesService = likesService;
        this.responseService = responseService;
    }

    @GetMapping("/{memId}/likes")
    @ApiOperation(value = "멤버별 좋아요 조회", notes = "특정 멤버가 좋아요한 키보드들을 조회합니다.")
    public ResponseEntity<SingleResult<List<SimpleKeyboardDto>>> getLikeKeyboards(@PathVariable Long memId){
        List<SimpleKeyboardDto> result = likesService.getLikes(memId);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }

    @PostMapping("/{memId}/likes")
    @ApiOperation(value = "멤버별 좋아요 추가", notes = "특정 멤버가 좋아요한 키보드를 추가합니다.")
    public ResponseEntity<SingleResult<String>> addLikeKeyboards(@PathVariable Long memId, @RequestBody HashMap<String,Long> keyboardId){
        String result = likesService.addLikes(memId,keyboardId.get("keyboardId"));
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }

    @DeleteMapping("/{memId}/likes")
    @ApiOperation(value = "멤버별 좋아요 취소", notes = "특정 멤버가 좋아요한 키보드들을 삭제합니다.")
    public ResponseEntity<SingleResult<String>> deleteLikeKeyboards(@PathVariable Long memId, @RequestBody HashMap<String,Long> keyboardId){
        String result = likesService.deleteLikes(memId,keyboardId.get("keyboardId"));
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }
}

