package com.klm.hackathon.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.klm.hackathon.model.model.model.Assist;

/**
 * @author x085441 (Sathiesh Suyambudhasan)
 * @since Nov 02, 2019
 */
@Component
public class AssistService {

    private final RestClient restClient;

    private final ObjectMapper objectMapper;

    public AssistService(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public String saveAssist(Assist assist) {
        org.elasticsearch.client.Response esResponse = null;
        String message;
        try {
            Map<String, Object> contentMap = objectMapper.readValue(objectMapper.writeValueAsString(assist), Map.class);
            esResponse = restClient.performRequest(HttpMethod.POST.name(), "/hack/default/" + assist.bookingCode, Collections.emptyMap(), new NStringEntity(objectMapper.writeValueAsString(contentMap), ContentType.APPLICATION_JSON));

        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        if (esResponse.getStatusLine().getStatusCode() == 200) {
            message = "Content Created Successfully!";
        } else {
            message = "Content not Created Successfully!";
        }
        return message;
    }

    public Object getDetails(String pnr) {

        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            Response esResponse = restClient.performRequest(HttpMethod.GET.name(), "hack/default/"+pnr, Collections.emptyMap(), new NStringEntity(searchSourceBuilder.toString(), ContentType.APPLICATION_JSON));
            Map<String, Object> contentMap = objectMapper.readValue(EntityUtils.toString(esResponse.getEntity()), LinkedHashMap.class);
            Assist assist=objectMapper.convertValue((contentMap.get("_source")),Assist.class);
            return assist;

        }catch(Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public Object getAssistDetails(String pnr, String assistType) {

        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            Response esResponse = restClient.performRequest(HttpMethod.GET.name(), "hack/default/"+pnr, Collections.emptyMap(), new NStringEntity(searchSourceBuilder.toString(), ContentType.APPLICATION_JSON));
            Map<String, Object> contentMap = objectMapper.readValue(EntityUtils.toString(esResponse.getEntity()), LinkedHashMap.class);
            Assist assist=objectMapper.convertValue((contentMap.get("_source")),Assist.class);
            if (assistType!=null && assistType.equalsIgnoreCase("true")) {
                return assist.assistDetails.departureAssistDetails;
            } else if (assistType!=null && assistType.equalsIgnoreCase("false")) {
                return assist.assistDetails.arrivalAssistDetails;
            } else {
                return assist.assistDetails;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
