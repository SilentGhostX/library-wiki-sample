package com.challenge.library.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorVO implements Serializable {

    private static final long serialVersionUID = -355273332075472848L;
    private Integer status;
    private String error;
    private String message;

    public ErrorVO(String error) {
        this.error = error;
    }

    public ErrorVO(String error, String message) {
        this.error = error;
        this.message = message;
    }

}
