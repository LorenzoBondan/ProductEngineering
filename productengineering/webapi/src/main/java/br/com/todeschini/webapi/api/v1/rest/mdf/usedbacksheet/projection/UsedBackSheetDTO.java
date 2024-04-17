package br.com.todeschini.webapi.api.v1.rest.mdf.usedbacksheet.projection;

import br.com.todeschini.webapi.api.v1.rest.mdf.back.projection.BackDTO;
import br.com.todeschini.webapi.api.v1.rest.mdp.sheet.projection.SheetDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedBackSheetDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "back", index = 2)
    BackDTO getBack();
    @JsonProperty(value = "sheet", index = 3)
    SheetDTO getSheet();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
