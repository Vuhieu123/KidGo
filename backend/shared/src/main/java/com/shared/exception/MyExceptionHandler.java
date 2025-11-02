package com.shared.exception;

import com.shared.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    // 🔐 Không đủ quyền truy cập
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("Access denied: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(CommonResponse.forbidden(e.getMessage()));
    }

    // 🧩 Exception custom của bạn
    @ExceptionHandler(MyException.class)
    public ResponseEntity<CommonResponse<String>> handleMyException(MyException e) {
        log.error("MyException: {}", e.getMessage(), e);
        return ResponseEntity.status(e.getHttpStatus()).body(e.toMyCommonResponse());
    }

    // 🚫 Thiếu header trong request
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingHeaderException(MissingRequestHeaderException e) {
        log.warn("Missing header: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(CommonResponse.badRequest(e.getMessage()));
    }

    // 🧾 JSON body sai format
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("Invalid JSON body: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(CommonResponse.badRequest("Invalid request body"));
    }

    // ❗️Sai argument (Enum, kiểu dữ liệu...)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Illegal argument: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(CommonResponse.badRequest(e.getMessage()));
    }

    // ⚙️ Validate fail: @Valid trên @RequestBody (JSON)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return buildValidationErrorResponse(e.getBindingResult(), e);
    }

    // ⚙️ Validate fail: @Valid trên @ModelAttribute / @RequestParam (form-data)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException e) {
        return buildValidationErrorResponse(e.getBindingResult(), e);
    }

    // 🔑 Lỗi xác thực đăng nhập
    @ExceptionHandler({InvalidParamsSendException.class, BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentials(RuntimeException e) {
        log.warn("Bad credentials: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(CommonResponse.badRequest(e.getMessage()));
    }

    // 💥 Lỗi hệ thống không xác định
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.error("Unexpected error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.internalError());
    }

    // 🔧 Hàm phụ để xử lý lỗi validation
    private ResponseEntity<Object> buildValidationErrorResponse(BindingResult bindingResult, Exception e) {
        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            if (objectError instanceof FieldError fieldError) {
                errorMessage.append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            } else {
                errorMessage.append(objectError.getDefaultMessage())
                        .append("; ");
            }
        }

        log.warn("Validation failed: {}", errorMessage);
        return ResponseEntity.badRequest()
                .body(CommonResponse.badRequest(errorMessage.toString(), ErrorCodeList.INVALID_PARAMETER));
    }

}
