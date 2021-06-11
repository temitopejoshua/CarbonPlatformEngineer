package com.carbon.hospitalitysystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse<T> {
    private String message;
    private T data;


    public APIResponse(String message){
        this.message=message;
    }
}
