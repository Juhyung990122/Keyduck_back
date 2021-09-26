package com.keyduck.keyboard.controller;


import com.keyduck.keyboard.domain.MainImage;
import com.keyduck.keyboard.service.MainImageService;
import com.keyduck.result.ListResult;
import com.keyduck.result.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@Api(tags = "MainImage", value = "KeyduckController v1")
public class MainImageController {
    private final MainImageService mainImageService;
    private final ResponseService responseService;

    @ApiOperation(value = "메인 이미지 3장 불러오기", notes = "메인이미지를 불러옵니다.")
    @GetMapping("/mains")
    public ResponseEntity<ListResult<MainImage>> getAllMainImage(){
        List<MainImage> result = mainImageService.getAllMainImages();
        return ResponseEntity
                .ok()
                .body(responseService.getListResult(result));
    }
}
