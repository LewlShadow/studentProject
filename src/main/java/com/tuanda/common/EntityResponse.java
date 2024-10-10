package com.tuanda.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

/**
 * @prOjEct studentProject-main
 * @DAtE 4/2/2024
 * @tImE 4:43 PM
 * @AUthOr tuanda52
 */
public class EntityResponse {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {

        Map<String, Object> map = new HashMap<>();
        map.put("TimeStamp", new Date());
        map.put("Message", message);
        map.put("Status", status.value());
        if (Objects.nonNull(responseObj)) map.put("Data", responseObj);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }

    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }

    public static ResponseEntity<Object> generateSuccessResponse(Object data) {
        return generateResponse("Success", HttpStatus.OK, data);
    }
}
