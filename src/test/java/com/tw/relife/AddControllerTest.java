package com.tw.relife;

import com.tw.relife.controller.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddControllerTest {

    @Test
    void should_throw_if_controller_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(null).build());
    }

    @Test
    void should_throws_if_controller_is_interface_or_abstract() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(AbstractController.class).build());
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(InterfaceController.class).build());
    }

    @Test
    void should_throw_if_controller_is_not_present_by_relife_controller() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(FakeController.class).build());
    }

    @Test
    void should_throw_if_action_has_one_more_params() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(ControllerWithTwoParams.class).build());
    }

    @Test
    void should_throw_if_controller_with_action_has_param_orther_type() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(ControllerWithIntegerType.class).build());
    }

    @Test
    void should_add_action_through_controller() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(TestController.class).build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/test", RelifeMethod.GET));
        assertEquals(200, response.getStatus());
        assertEquals("test", response.getContent());
    }

    @Test
    void should_add_multiply_actions_in_one_controller() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ControllerWithTwoActions.class).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/test", RelifeMethod.GET));
        assertEquals(200, response.getStatus());
        assertEquals("test", response.getContent());

        RelifeResponse response_403 = app.process(new RelifeRequest("/test_403", RelifeMethod.GET));
        assertEquals(403, response_403.getStatus());
        assertEquals("403", response_403.getContent());

    }

    @Test
    void should_return_one_action_if_have_multiply_action_with_same_http_path() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ControllerWithTwoActions.class)
                .addAction("/test", RelifeMethod.GET, request -> new RelifeResponse(404))
                .build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response1 = app.process(new RelifeRequest("/test", RelifeMethod.GET));
        RelifeResponse response2 = app.process(new RelifeRequest("/test", RelifeMethod.GET));

        assertEquals(response1.getStatus(), response2.getStatus());
        assertEquals(response1.getContent(), response2.getContent());
    }

    @Test
    void should_return_status_code_as_expect_if_action_throws() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ControllerWithException.class).build();

        RelifeApp app = new RelifeApp(handler);

        assertEquals(409, app.process(new RelifeRequest("/test", RelifeMethod.GET)).getStatus());
    }

    @Test
    void should_return_200_without_content_if_controller_return_null() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(TestController.class).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/null", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertNull(response.getContent());
        assertNull(response.getContentType());
    }

    @Test
    void should_return_status_code_500_if_action_throw_common_exception() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(TestController.class).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/common_exception", RelifeMethod.GET));

        assertEquals(500, response.getStatus());
    }

    @Test
    void should_add_action_if_method_is_private() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(TestController.class).build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/private", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("private", response.getContent());
    }
}
