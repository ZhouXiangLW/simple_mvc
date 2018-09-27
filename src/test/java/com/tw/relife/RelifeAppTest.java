package com.tw.relife;

import com.tw.relife.exception.SimpleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelifeAppTest {
    @Test
    void should_create_response() {
        RelifeApp app = new RelifeApp(request -> new RelifeResponse(200));

        RelifeRequest whateverRequest = new RelifeRequest("/any/path", RelifeMethod.GET);
        RelifeResponse response = app.process(whateverRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void should_throw_for_null_handler() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new RelifeApp(null)
        );
    }

    @Test
    void should_catch_unhandled_exception_as_internal_server_error() {
        RelifeApp app = new RelifeApp(request -> {
            throw new RuntimeException("error occurred");
        });

        RelifeRequest whateverRequest = new RelifeRequest("/any/path", RelifeMethod.GET);
        RelifeResponse response = app.process(whateverRequest);

        assertEquals(500, response.getStatus());
    }

    @Test
    void should_create_response2() {
        RelifeApp app = new RelifeApp(request -> new RelifeResponse(300));

        RelifeRequest whateverRequest = new RelifeRequest("/any/path", RelifeMethod.GET);
        RelifeResponse response = app.process(whateverRequest);

        assertNotNull(response);
        assertEquals(300, response.getStatus());
    }

    @Test
    void should_return_status_code_404_when_handler_throw_exception() {
        RelifeApp app = new RelifeApp(request -> {
            throw new SimpleNotFoundException();
        });

        RelifeRequest whateverRequest = new RelifeRequest("/any/path", RelifeMethod.GET);
        RelifeResponse response = app.process(whateverRequest);

        assertNotNull(response);
        assertEquals(404, response.getStatus());
    }
}
