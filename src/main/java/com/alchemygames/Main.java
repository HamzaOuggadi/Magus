package com.alchemygames;

import com.alchemygames.engine.Engine;
import com.alchemygames.engine.IAppLogic;
import com.alchemygames.engine.Window;
import com.alchemygames.engine.graph.Render;
import com.alchemygames.engine.scene.Scene;
import org.lwjgl.Version;
import org.tinylog.Logger;

import static org.lwjgl.opengl.GL11.*;

public class Main implements IAppLogic {

    public static void main(String[] args) {
        System.out.println("Magus Engine");
        if (StartupHelper.startNewJvmIfRequired()) return;
        Main main = new Main();
        Engine gameEng = new Engine("Magus!", new Window.WindowOptions(), main);
        gameEng.start();
    }

    private void checkVersions() {
        Logger.info("LWJGL : {}", Version.getVersion());
        Logger.info("Vendor : {}", glGetString(GL_VENDOR));
        Logger.info("Renderer : {}", glGetString(GL_RENDERER));
        Logger.info("OpenGL version supported : {}", glGetString(GL_VERSION));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void init(Window window, Scene scene, Render render) {

    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {

    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {

    }
}