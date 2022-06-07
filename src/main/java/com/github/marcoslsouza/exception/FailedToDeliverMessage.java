package com.github.marcoslsouza.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.apache.camel.RuntimeCamelException;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class FailedToDeliverMessage extends RuntimeCamelException {
	
	private static final long serialVersionUID = -7050013666215306659L;
	
	@JsonIgnore
    private final HttpStatus httpStatusCode;
    private final String code;
    private final String message;
    private final String description;
    
    public FailedToDeliverMessageExceptionBody getOnlyBody() {

        return FailedToDeliverMessageExceptionBody.builder()
        		.httpStatusCode(this.httpStatusCode)
                .code(this.code)
                .message(this.message)
                .description(this.description)
                .build();
    }

	@Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FailedToDeliverMessageExceptionBody {

		private HttpStatus httpStatusCode;
        private String code;
        private String message;
        private String description;
    }
}
