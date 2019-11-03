
package com.klm.hackathon.model.model.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Assist {

    public String bookingCode;
    public String origin;
    public String destination;
    public String flightDate;
    public Boolean assistRequired;
    public AssistPackDetails assistPackDetails;
    public PaxDetails paxDetails;
    public AssistDetails assistDetails;

}
