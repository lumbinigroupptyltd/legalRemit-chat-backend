package com.substring.chat.coresdk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectResponse {
    private Integer statuscode;
    private String message;
    private Boolean status;
    @Nullable
    private Object data;

    public ObjectResponse(Integer statuscode, Boolean status, String message, Object data) {
        this.statuscode = statuscode;
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
