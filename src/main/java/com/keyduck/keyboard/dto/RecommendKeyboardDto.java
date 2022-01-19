package com.keyduck.keyboard.dto;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendKeyboardDto {
    private Tag tag;
    private List<SimpleKeyboardDto> keyboards = new ArrayList<>();

    public void setTag(Tag tag){
        this.tag = tag;
    }
    public void addKeyboard(Keyboard keyboard){
        keyboards.add(new SimpleKeyboardDto().toDto(keyboard));
    }
}

