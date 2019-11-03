
package com.klm.hackathon.model.model.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PaxDetails {

    public String firstName;
    public String lastName;
    public String contactNo;
    public String contactAddress;
    public String careTakerName;
    public String careTakerNumber;

}
