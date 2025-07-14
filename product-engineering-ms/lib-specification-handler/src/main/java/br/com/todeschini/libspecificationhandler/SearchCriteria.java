package br.com.todeschini.libspecificationhandler;

import lombok.*;

/**
 * findAll method
 * Class that represents the attributes to be requested on request
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}
