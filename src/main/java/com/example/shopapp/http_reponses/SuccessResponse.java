package com.example.shopapp.http_reponses;

import java.time.LocalDateTime;

public class SuccessResponse<T> extends BaseResponse{
    public SuccessResponse(T data) {
        super(LocalDateTime.now(),"success","",data);
    }
    public SuccessResponse(String message) {
            super(LocalDateTime.now(),"success",message,null);
        }

    public SuccessResponse(String message, T data) {
        super(LocalDateTime.now(),"success",message,data);
    }
}
