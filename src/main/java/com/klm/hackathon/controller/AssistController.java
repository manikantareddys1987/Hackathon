package com.klm.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klm.hackathon.model.model.model.Assist;
import com.klm.hackathon.service.AssistService;

import java.io.IOException;

/**
 * @author x085441 (Sathiesh Suyambudhasan)
 * @since Nov 01, 2019
 */
@RestController
@RequestMapping(value="/hack")
public class AssistController {

    private AssistService assistService;

    @Autowired
    public AssistController(final AssistService assistService){
        this.assistService=assistService;
    }

    @PostMapping(value="/saveupdatedetails",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveDetails(@RequestBody Assist assist){
        return assistService.saveAssist(assist);
    }

    @GetMapping(value="/getdetails/{pnr}")
    public Object getDetails(@PathVariable(value = "pnr", required = true) String pnr){
        return assistService.getDetails(pnr);
    }

    @GetMapping(value="/getassist/{pnr}")
    public Object getAssist(@PathVariable(value = "pnr", required = true) String pnr,@RequestParam(value="assisttype", required=false) String assistType){
        return assistService.getAssistDetails(pnr,assistType);
    }

    @PostMapping(value="/updateAdminAndDriver")
    public String updateAdminAndDriver() throws IOException {
        assistService.updateAdminAndDriver();
        return "Updated successfully";
    }


}
