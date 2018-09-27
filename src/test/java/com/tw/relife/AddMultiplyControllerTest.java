package com.tw.relife;

import com.tw.relife.controller.TestController;
import com.tw.relife.controller.TestController2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddMultiplyControllerTest {
    @Test
    void should_call_add_controller_one_more_time() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(TestController.class)
                .addController(TestController2.class)
                .build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/private", RelifeMethod.GET));
        RelifeResponse response2 = app.process(new RelifeRequest("/test_form_2", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("private", response.getContent());

        assertEquals(201, response2.getStatus());
        assertEquals("from test controller 2", response2.getContent());
    }

    @Test
    void should_add_one_if_two_controller_has_action_with_same_path() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(TestController.class)
                .addController(TestController2.class)
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/test", RelifeMethod.GET));
        RelifeResponse response2 = app.process(new RelifeRequest("/test", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("test", response.getContent());
        assertEquals(response.getStatus(), response2.getStatus());
        assertEquals(response.getContent(), response2.getContent());
    }

    @Test
    void should_throw_if_add_same_controller_twice() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(TestController.class)
                        .addController(TestController.class)
                        .build());
    }
}
