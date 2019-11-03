
package com.klm.hackathon.model.model.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AssistDetails {

    public DepartureAssistDetails departureAssistDetails;
    public ArrivalAssistDetails arrivalAssistDetails;

}
