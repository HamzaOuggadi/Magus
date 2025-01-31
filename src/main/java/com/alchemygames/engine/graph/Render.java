package com.alchemygames.engine.graph;

import com.alchemygames.engine.Window;
import com.alchemygames.engine.scene.Scene;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    private SceneRenderer sceneRenderer;

    public Render() {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        sceneRenderer = new SceneRenderer();
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());
        sceneRenderer.render(scene);
    }

    public void cleanup() {
        sceneRenderer.cleanup();
    }
}
