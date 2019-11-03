
package com.klm.hackathon.model.model.model;

import lombok.Data;

@Data
public class ArrivalAssistDetails {

    public AssistAgentDetails_ assistAgentDetails;
    public PickUpDetails pickUpDetails;
    public ImmigrationCheckDetails immigrationCheckDetails;
    public LuggagePickUpDetails luggagePickUpDetails;
    public HomeDropDetails homeDropDetails;

}
