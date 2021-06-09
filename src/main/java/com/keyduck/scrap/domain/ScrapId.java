package com.keyduck.scrap.domain;

import java.io.Serializable;

public class ScrapId implements Serializable {
    private Long memberId;
    private Long keyboardId;

    public ScrapId(){}
    public ScrapId(Long memberId, Long keyboardId){
        super();
        this.memberId = memberId;
        this.keyboardId = keyboardId;
    }
}
