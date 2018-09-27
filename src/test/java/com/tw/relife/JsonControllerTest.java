package com.tw.relife;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.relife.controller.RestController;
import com.tw.relife.entity.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonControllerTest {
    @Test
    void should_return_json_string_if_return_type_is_not_relife_response() throws JsonProcessingException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(RestController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/json", RelifeMethod.GET));

        String expectedJson = new ObjectMapper().writeValueAsString(new Student("ZhouXiang", 181));
        assertEquals(200, response.getStatus());
        assertEquals(expectedJson, response.getContent());
        assertEquals("application/json", response.getContentType());
    }
}
