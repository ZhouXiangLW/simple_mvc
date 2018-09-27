package com.tw.relife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddMultiplyActionTest {

    @Test
    void should_add_multiply_actions() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path", RelifeMethod.GET,
                        request -> new RelifeResponse(200, "get action", "text/plain")
                ).addAction(
                        "/path", RelifeMethod.POST,
                        request -> new RelifeResponse(403, "post action", "text/plain")
                ).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse getResponse = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        RelifeResponse postResponse = app.process(new RelifeRequest("/path", RelifeMethod.POST));

        assertEquals(200, getResponse.getStatus());
        assertEquals("get action", getResponse.getContent());

        assertEquals(403, postResponse.getStatus());
        assertEquals("post action", postResponse.getContent());

    }

    @Test
    void should_remain_first_action_if_add_two_action_with_same_http_path() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path", RelifeMethod.GET,
                        request -> new RelifeResponse(200, "get action", "text/plain")
                ).addAction(
                        "/path", RelifeMethod.GET,
                        request -> new RelifeResponse(403, "get action 2", "text/plain")
                ).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("get action", response.getContent());
    }
}
