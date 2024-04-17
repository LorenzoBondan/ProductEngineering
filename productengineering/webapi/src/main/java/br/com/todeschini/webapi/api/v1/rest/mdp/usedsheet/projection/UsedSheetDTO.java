package br.com.todeschini.webapi.api.v1.rest.mdp.usedsheet.projection;

import br.com.todeschini.webapi.api.v1.rest.mdp.mdpson.projection.MDPSonDTO;
import br.com.todeschini.webapi.api.v1.rest.mdp.sheet.projection.SheetDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedSheetDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "sheet", index = 2)
    SheetDTO getSheet();
    @JsonProperty(value = "son", index = 3)
    MDPSonDTO getSon();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
