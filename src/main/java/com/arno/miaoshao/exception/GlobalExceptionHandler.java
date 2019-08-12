package com.arno.miaoshao.exception;

import com.arno.miaoshao.result.CodeMsg;
import com.arno.miaoshao.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author arno
 * @date 2019-08-12 21:37
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<CodeMsg> exception(Exception e) {
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException globalException = (GlobalException)e;
            return Result.error(globalException.getCodeMsg());
        } else  {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

}
