package br.com.todeschini.libvalidationhandler.pageable;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PageableRequest {
    private Integer page;
    private Integer pageSize;
    private String[] sort;

    private List<String> columns;
    private List<String> operations;
    private List<String> values;

    private Map<String, String> columnMap;
}
