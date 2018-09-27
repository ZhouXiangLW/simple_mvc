package com.tw.relife;

import com.tw.relife.exception.SimpleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddActionTest {
    @Test
    void should_add_action_and_deal_with_action() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET,
                        request -> new RelifeResponse(200,
                                "hello", "text/plain")).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("hello", response.getContent());
        assertEquals("text/plain", response.getContentType());
    }

    @Test
    void should_return_status_code_404_if_path_not_found() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET,
                        request -> new RelifeResponse(200,
                                "hello", "text/plain")).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/path2", RelifeMethod.GET));

        assertEquals(404, response.getStatus());
    }

    @Test
    void should_return_status_404_when_method_not_found() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET,
                        request -> new RelifeResponse(200,
                                "hello", "text/plain")).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/path2", RelifeMethod.POST));
        assertEquals(404, response.getStatus());
    }

    @Test
    void should_return_status_code_404_if_handler_throws_exception_present_by_relife_status_code() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET,
                        request -> {
                            throw new SimpleNotFoundException();
                        }).build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(404, response.getStatus());
    }

    @Test
    void should_throw_if_param_is_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RelifeMvcHandlerBuilder().addAction(null,
                    RelifeMethod.GET, request -> new RelifeResponse(200)).build();}
            );
        assertThrows(IllegalArgumentException.class, () -> {
            new RelifeMvcHandlerBuilder().addAction("/path",
                    null, request -> new RelifeResponse(200)).build();}
        );
        assertThrows(IllegalArgumentException.class, () -> {
            new RelifeMvcHandlerBuilder().addAction("/path",
                    RelifeMethod.GET, null).build();}
        );

    }

    @Test
    void should_return_status_200_without_content_if_handler_return_null() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET,
                        request -> null);

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(200, response.getStatus());
        assertNull(response.getContentType());
        assertNull(response.getContent());
    }
}
