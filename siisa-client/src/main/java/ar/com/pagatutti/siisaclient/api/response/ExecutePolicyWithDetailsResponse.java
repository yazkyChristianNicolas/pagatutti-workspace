package ar.com.pagatutti.siisaclient.api.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ExecutePolicyWithDetailsResponse {
	
		
		@JsonProperty("variables")
	    private VarResponse variables;
		
		
		@Data
		@NoArgsConstructor
		@JsonIgnoreProperties(ignoreUnknown=true)
		public class VarResponse { 
			
			@JsonProperty("aprueba")
			private String aprueba;
			
			@JsonProperty("monto_max")
			private Double montoMax;
			
			@JsonProperty("motivo")
			private String motivo;
			
			@JsonProperty("currentExecId")
			private String currentExecId;
			
			@JsonProperty("decisionResult")
			private String decisionResult;
			
			public boolean isApproved() {
				return (null != this.aprueba && this.aprueba.equals("1"))? true:false;
			}
		}
		
}


