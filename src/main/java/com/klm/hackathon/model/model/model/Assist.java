
package com.klm.hackathon.model.model.model;

import lombok.Data;

@Data
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
