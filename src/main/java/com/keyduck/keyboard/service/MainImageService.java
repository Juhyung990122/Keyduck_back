package com.keyduck.keyboard.service;

import com.keyduck.keyboard.domain.MainImage;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import com.keyduck.keyboard.repository.MainImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainImageService {
    private MainImageRepository mainImageRepository;

    public MainImageService(MainImageRepository mainImageRepository){
        this.mainImageRepository = mainImageRepository;
    }

    public List<MainImage> getAllMainImages() {
        List<MainImage> result = mainImageRepository.findAll();
        return result;
    }
}
