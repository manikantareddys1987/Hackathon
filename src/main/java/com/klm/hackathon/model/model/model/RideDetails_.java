
package com.klm.hackathon.model.model.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RideDetails_ {

    public String rideStatus;
    public String scheduledTime;
    public String pickUpTime;
    public String dropTime;
    public String remarks;
    public Cab_ cab;

}
