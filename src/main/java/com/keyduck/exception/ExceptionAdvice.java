package com.keyduck.exception;

import com.keyduck.result.CommonResult;
import com.keyduck.result.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
   private final ResponseService responseService;
   //고정된 메세지를 가진 예외
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected  CommonResult ValidationException(HttpServletRequest request, MethodArgumentNotValidException e){
      return responseService.getFailResult(400,"올바르지 않은 형식입니다.");
   }

   @ExceptionHandler(RuntimeException.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   protected CommonResult RuntimeException(HttpServletRequest request, RuntimeException e){
      return responseService.getFailResult(500,e.getMessage());
   }

   // 메세지에 변화를 줄 예외
   @ExceptionHandler(NoSuchElementException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   protected CommonResult NotFoundException(HttpServletRequest request, NoSuchElementException e){
      return responseService.getFailResult(404,e.getMessage());
   }

   @ExceptionHandler(CustomException.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   protected CommonResult CustomException(HttpServletRequest request, CustomException e){
       return responseService.getFailResult(500,e.getMessage());
   }
}
