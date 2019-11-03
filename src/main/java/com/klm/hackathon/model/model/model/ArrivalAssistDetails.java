
package com.klm.hackathon.model.model.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ArrivalAssistDetails {

    public AssistAgentDetails_ assistAgentDetails;
    public PickUpDetails pickUpDetails;
    public ImmigrationCheckDetails immigrationCheckDetails;
    public LuggagePickUpDetails luggagePickUpDetails;
    public HomeDropDetails homeDropDetails;

}
