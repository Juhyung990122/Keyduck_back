package com.keyduck.member.controller;

import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.member.service.LikesService;
import com.keyduck.result.ResponseService;
import com.keyduck.result.SingleResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class LikesController {
    private final LikesService likesService;
    private final ResponseService responseService;

    public LikesController(LikesService likesService, ResponseService responseService) {
        this.likesService = likesService;
        this.responseService = responseService;
    }

    @GetMapping("/{memId}/likes")
    public ResponseEntity<SingleResult<List<SimpleKeyboardDto>>> getLikeKeboards(@PathVariable Long memId){
        List<SimpleKeyboardDto> result = likesService.getLikes(memId);
        return ResponseEntity
                .ok()
                .body(responseService.getSingleResult(result));
    }
}

