package co.com.vanegas.microservice.resolveEnigmaApi.routes;

import co.com.vanegas.microservice.resolveEnigmaApi.model.client.ClientJsonApiBodyResponseSuccess;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

@Component
public class GetStepOneClientRoute extends RouteBuilder{
	
	@Override
	public void configure() throws Exception{
		from("direct:get-step-one")
        .setHeader(Exchange.HTTP_METHOD ,constant("POST"))
        .setHeader(Exchange.CONTENT_TYPE,constant("application/json"))
        .to("freemaker:template/GetStepOneClientTemplate.ftl")
        .log("Request microservice step one ${body}")
        .to("http4://localhost:8080/getStep")
				.convertBodyTo(String.class)
        .log("String reponse microservice step one ${body}")
				.unmarshal().json(JsonLibrary.Jackson, ClientJsonApiBodyResponseSuccess.class)
				.log("Java Response microservice step one ${body}")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						ClientJsonApiBodyResponseSuccess stepOneResponse = (ClientJsonApiBodyResponseSuccess) exchange.getIn().getBody()
								if (stepOneResponse.getData().get(0).getStep().equalsIgnoreCase("1"))
								{
									exchange.setProperty("Step1", stepOneResponse.getData().get(0).getStepDescription());
									exchange.setProperty("Error", "0000");
									exchange.setProperty("descError","No error");
								}
								else
								{
									exchange.setProperty("Error","0001");
									exchange.setProperty("descError","Error consulting the step one");
								}
					}
				})
				.log("Response code ${exchangeProperty[Error]}");
	}
}
