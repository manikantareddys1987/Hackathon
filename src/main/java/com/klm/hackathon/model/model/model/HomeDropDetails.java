
package com.klm.hackathon.model.model.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HomeDropDetails {

    public String status;
    public String closureTime;
    public String remarks;
    public RideDetails_ rideDetails;

}
