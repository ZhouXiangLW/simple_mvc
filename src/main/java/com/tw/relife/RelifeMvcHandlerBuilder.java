package com.tw.relife;

import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RelifeMvcHandlerBuilder implements RelifeAppHandler {

    private Map<RelifeHttpPath, RelifeAppHandler> handlerMap = new HashMap<>();

    @Override
    public RelifeResponse process(RelifeRequest request) throws Exception {
        RelifeAppHandler handler = getHandlerFromMap(request);
        if (handler == null) {
            return new RelifeResponse(404);
        }
        RelifeResponse response = handler.process(request);
        return response == null ? new RelifeResponse(200) : response;
    }

    public RelifeAppHandler build() {
        return this;
    }

    public RelifeMvcHandlerBuilder addController(Class<?> controllerClass) {
        checkController(controllerClass);
        Arrays.stream(controllerClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RelifeRequestMapping.class))
                .forEach(method -> addMethodToAction(controllerClass, method));
        return this;
    }

    private void addMethodToAction(Class<?> controllerClass, Method method) {
        String path = method.getAnnotation(RelifeRequestMapping.class).value();
        RelifeMethod relifeMethod = method.getAnnotation(RelifeRequestMapping.class).method();
        RelifeAppHandler handler = request -> {
            try {
                method.setAccessible(true);
                return (RelifeResponse) method.invoke(controllerClass.newInstance(), request);
            } catch (InvocationTargetException e) {
                throw (Exception) e.getCause();
            }
        };
        addAction(path, relifeMethod, handler);
    }

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        if (path == null || method == null || handler == null) {
            throw new IllegalArgumentException();
        }
        if (handlerMap.containsKey(new RelifeHttpPath(path, method))) {
            return this;
        }
        handlerMap.put(new RelifeHttpPath(path, method), handler);
        return this;
    }

    private RelifeAppHandler getHandlerFromMap(RelifeRequest request) {
        RelifeHttpPath httpPath = new RelifeHttpPath(request.getPath(), request.getMethod());
        if (handlerMap.containsKey(httpPath)) {
            return handlerMap.get(httpPath);
        }
        return null;
    }

    private void checkController(Class<?> controllerClass) {
        if (controllerClass == null) {
            throw new IllegalArgumentException("controllerClass can not be null");
        }
        if (Modifier.isInterface(controllerClass.getModifiers())
                || Modifier.isAbstract(controllerClass.getModifiers())) {
            throw new IllegalArgumentException("controllerClass can not be interface or abstract");
        }
        if (!controllerClass.isAnnotationPresent(RelifeController.class)) {
            throw new IllegalArgumentException("can not found controller");
        }
        if (Arrays.stream(controllerClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RelifeRequestMapping.class))
                .anyMatch(method -> method.getParameterCount() != 1 || method.getParameterTypes()[0] != RelifeRequest.class)) {
            throw new IllegalArgumentException("action param must be RelifeRequest and count msut be 1");
        }
    }

}
