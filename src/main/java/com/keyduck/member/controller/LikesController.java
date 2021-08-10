package com.keyduck.member.controller;

import com.keyduck.member.dto.LikesCreateInfo;
import com.keyduck.member.service.LikesService;
import com.keyduck.result.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class LikesController {
    private final LikesService likesService;
    private final ResponseService responseService;

    public LikesController(LikesService likesService, ResponseService responseService) {
        this.likesService = likesService;
        this.responseService = responseService;
    }


}

