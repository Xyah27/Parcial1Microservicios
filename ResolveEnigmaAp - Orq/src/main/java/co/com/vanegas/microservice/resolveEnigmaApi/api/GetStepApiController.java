package co.com.vanegas.microservice.resolveEnigmaApi.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.vanegas.microservice.resolveEnigmaApi.model.JsonApiBodyRequest;
import co.com.vanegas.microservice.resolveEnigmaApi.model.JsonApiBodyResponseSuccess;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Parameter;

import org.apache.camel.EndpointInject;
import org.apache.camel.FluentProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-02-27T19:20:23.716-05:00[America/Bogota]")
@RestController
public class GetStepApiController implements GetStepApi {

   private static final Logger log = LoggerFactory.getLogger(GetStepApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    private List listResponse = new ArrayList<JsonApiBodyResponseSuccess>();
    @EndpointInject(uri="direc:get-step-one")
    private FluentProducerTemplate producerTemplateResolveEnigma;
    @org.springframework.beans.factory.annotation.Autowired
    public GetStepApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<JsonApiBodyResponseSuccess> getStep(@ApiParam(value = "request body get enigma step" ,required=true )  @Valid @RequestBody JsonApiBodyRequest body) {
        try{
            producerTemplateResolveEnigma.request();
            return new ResponseEntity<JsonApiBodyResponseSuccess>(objectMapper.readValue("{ \"data\": [{ \"answer\": \"answer\",    \"header\": { }}]}"));
        }catch (IOException e){
            log.error("Could't serialize response for content type application/json", e);
            return new ResponseEntity<JsonApiBodyResponseSuccess>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
