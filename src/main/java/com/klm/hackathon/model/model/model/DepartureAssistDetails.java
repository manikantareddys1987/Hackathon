
package com.klm.hackathon.model.model.model;

import lombok.Data;

@Data
public class DepartureAssistDetails {

    public AssistAgentDetails assistAgentDetails;
    public HomePickUpDetails homePickUpDetails;
    public CheckInDetails checkInDetails;
    public SecurityCheckDetails securityCheckDetails;
    public BoardingDetails boardingDetails;

}
