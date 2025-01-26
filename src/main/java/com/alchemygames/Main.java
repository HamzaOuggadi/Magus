package com.alchemygames;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    private long window;

    public static void main(String[] args) {
        System.out.println("Magus Engine");
        if (StartupHelper.startNewJvmIfRequired()) return;

        new Main().run();

    }

    public void run() {
        init();
        loop();

        // Free the GLFW Window callbacks and destroy window at the end
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup error callback (default implementation)
        // writes errors in System.err
        GLFWErrorCallback.createPrint(System.err).set();

        // Initializing GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configuring the GLFW Window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "Hello Magus!", NULL, NULL);

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
           if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
               glfwSetWindowShouldClose(window, true);
           }
        });

        try(MemoryStack stack = MemoryStack.stackPush()) {

            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(window,
                    (vidMode.width() - pWidth.get(0))/2,
                    (vidMode.height() - pHeight.get(0))/2);
        }
        // Making the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enabling V-Sync
        glfwSwapInterval(1);

        // Showing the window
        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        checkVersions();

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(window);

            glfwPollEvents();
        }
    }

    private void checkVersions() {
        System.out.println("LWJGL : " + Version.getVersion());
        System.out.println("Vendor : " + glGetString(GL_VENDOR));
        System.out.println("Renderer : " + glGetString(GL_RENDERER));
        System.out.println("OpenGL version supported : " + glGetString(GL_VERSION));
    }
}