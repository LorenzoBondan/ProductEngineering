package br.com.todeschini.webapi.api.v1.rest.mdf.usedbacksheet.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedBackSheetDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "backCode", index = 2)
    Long getBackCode();
    @JsonProperty(value = "sheetCode", index = 3)
    Long getSheetCode();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
