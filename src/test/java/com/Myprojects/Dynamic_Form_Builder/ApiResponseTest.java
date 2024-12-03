package com.Myprojects.Dynamic_Form_Builder;

import com.Myprojects.Dynamic_Form_Builder.model.ApiResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApiResponseTest {

    @Test
    public void testConstructorWithoutData() {
        String status = "success";
        String message = "Operation completed successfully";

        ApiResponse response = new ApiResponse(status, message);

        assertEquals(status, response.getStatus(), "Status should match the provided value.");
        assertEquals(message, response.getMessage(), "Message should match the provided value.");
        assertNull(response.getData(), "Data should be null when not provided.");
    }

    @Test
    public void testConstructorWithData() {
        String status = "error";
        String message = "An error occurred";
        Object data = "Error details";

        ApiResponse response = new ApiResponse(status, message, data);

        assertEquals(status, response.getStatus(), "Status should match the provided value.");
        assertEquals(message, response.getMessage(), "Message should match the provided value.");
        assertEquals(data, response.getData(), "Data should match the provided value.");
    }

    @Test
    public void testSettersAndGetters() {
        ApiResponse response = new ApiResponse(null, null);

        response.setStatus("pending");
        response.setMessage("Request is being processed");
        response.setData("Processing data");

        assertEquals("pending", response.getStatus(), "Status should match the updated value.");
        assertEquals("Request is being processed", response.getMessage(), "Message should match the updated value.");
        assertEquals("Processing data", response.getData(), "Data should match the updated value.");
    }
}
