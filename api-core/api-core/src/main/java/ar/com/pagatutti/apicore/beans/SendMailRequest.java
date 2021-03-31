package ar.com.pagatutti.apicore.beans;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@ToString(includeFieldNames=true)
@Data
public class SendMailRequest {
	
	@NotNull
	@JsonProperty("to")
	private String to;
	
	@NotNull
	@JsonProperty("from")
	private String from;
	
	@NotNull
	@JsonProperty("subject")
	private String subject;
	
	@JsonProperty("cc")
	private List<String> cc;
	
	@JsonProperty("co")
	private List<String> co;
	
	@JsonProperty("attachments")
	private List<String> attachments;
	
	@NotNull
	@JsonProperty("template")
	private String template;
	
	@NotNull
	@JsonProperty("data")
	private Map<String, Object> data;
}
