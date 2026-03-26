package com.agaram.eln.exception;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
/**
 * 
 * @author ATE166 (Sathishkumar Chandrasekar)
 *
 * Added for handling error globaly in eln
 * 
 * Date added on 10-09-2025
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// Handle NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, HttpStatus.INTERNAL_SERVER_ERROR, "Null Pointer Exception");
    }

    // Handle IndexOutOfBoundsException
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<Map<String, Object>> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, HttpStatus.BAD_REQUEST, "Index Out of Bounds Exception");
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, HttpStatus.BAD_REQUEST, "Illegal Argument Exception");
    }

    // Fallback for ALL other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, HttpStatus.INTERNAL_SERVER_ERROR, "Unhandled Exception");
    }

   
//    public ResponseEntity<Map<String, Object>> buildResponse(Exception ex, WebRequest request, String errorType) {
    // Common method to build structured response
    private ResponseEntity<Map<String, Object>> buildResponse(Exception ex, WebRequest request, HttpStatus status, String errorType) {

        Map<String, Object> errorResponse = new HashMap<>();
        // Get HTTP details from WebRequest
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String requestUri = servletWebRequest.getRequest().getRequestURI();
        String httpMethod = servletWebRequest.getRequest().getMethod();
        
        // Exception type
        String exceptionType = ex.getClass().getSimpleName();
        
        // Find the first stack trace element from your project
        StackTraceElement targetElement = null;
        for (StackTraceElement element : ex.getStackTrace()) {
            if (element.getClassName().startsWith("com.agaram")) { // Filter by your base package
                targetElement = element;
                break;
            }
        }

        // If not found, fallback to first element
        if (targetElement == null) {
            targetElement = ex.getStackTrace()[0];
        }
        
        String fileName = targetElement.getFileName();
        String className = targetElement.getClassName();
        String methodName = targetElement.getMethodName();
        String message = ex.getMessage();
        String threadName = Thread.currentThread().getName();
        String timestamp = LocalDateTime.now().toString();
        
        System.out.println("======================================");
        System.out.println("<========= Exception Details =========>");
        System.out.println("Timestamp      : " + timestamp);
        System.out.println("Level          : ERROR");
        System.out.println("Thread         : " + threadName);
        System.out.println("Logger         : " + className);
        System.out.println("Message        : " + message);
        System.out.println("ExceptionFile  : " + fileName);
        System.out.println("MethodName     : " + methodName);
        System.out.println("Lineno         : " + targetElement.getLineNumber());
        System.out.println("HTTP Method    : " + httpMethod);
        System.out.println("HTTP Status    : " + status.value());
        System.out.println("Exception Type : " + exceptionType);
        System.out.println("Error Type     : " + errorType);
        System.out.println("Request URI    : " + requestUri);
        System.out.println("Full Trace     : ");
        for (StackTraceElement e : ex.getStackTrace()) {
        	 if (e.getClassName().startsWith("com.agaram")) {
        		 System.out.println("\tat " + e);
        	 }
        }
        System.out.println("======================================");

        return new ResponseEntity<>(errorResponse, status);
    }
}
