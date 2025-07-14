package br.com.todeschini.libvalidationhandler.validation;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataRange {

    private LocalDate startDate;
    private LocalDate finalDate;
}
