package com.keyduck.result;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    public enum CommonResponse{
        SUCCESS(0,"성공했습니다."),
        FAIL(-1,"실패했습니다.");

        int code;
        String msg;

        CommonResponse(int code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode(){
            return code;
        }

        public String getMsg(){
            return msg;
        }
    }

    //결과 세팅용 메서드
    private void setSuccessResult(CommonResult result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    // 단일결과 처리
    public <T> SingleResult<T> getSingleResult(T data){
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    //리스트 결과 처리
    public <T> ListResult<T> getListResult(List<T> list){
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    //데이터없이 결과만 처리(성공시)
    public CommonResult getSuccessResult(){
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }
    //데이터없이 결과만 처리(실패시)
    public CommonResult getFailResult(String msg){
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(msg);
        return result;
    }



}
