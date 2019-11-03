
package com.klm.hackathon.model.model.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Cab {

    public String type;
    public String no;
    public Driver driver;

}
