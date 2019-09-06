package com.room.booking.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@JsonInclude(Include.NON_NULL)
public class ApiErrorResponse {
    private Date timestamp;
    private HttpStatus status;
    private String errorCode;
    private String message;
    private String detail;

    //Builder
    public static final class ApiErrorResponseBuilder {
        private HttpStatus status;
        private String errorCode;
        private String message;
        private String detail;

        public ApiErrorResponseBuilder() {
        }

        public ApiErrorResponseBuilder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ApiErrorResponseBuilder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ApiErrorResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiErrorResponseBuilder withDetail(String detail) {
            this.detail = detail;
            return this;
        }

        public ApiErrorResponse build() {
            ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
            apiErrorResponse.timestamp = new Date();
            apiErrorResponse.status = this.status;
            apiErrorResponse.errorCode = this.errorCode;
            apiErrorResponse.detail = this.detail;
            apiErrorResponse.message = this.message;

            return apiErrorResponse;
        }
    }
}
