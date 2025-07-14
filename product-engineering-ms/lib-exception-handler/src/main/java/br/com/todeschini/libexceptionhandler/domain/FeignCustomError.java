package br.com.todeschini.libexceptionhandler.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeignCustomError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
    private Map<String, Object> responseBody;
}
