package com.alchemygames.engine;

import com.alchemygames.engine.graph.Render;
import com.alchemygames.engine.scene.Scene;

public class Engine {

    public static final int TARGET_UPS = 30;
    private final IAppLogic appLogic;
    private final Window window;
    private Render render;
    private Scene scene;
    private boolean isRunning;
    private int targetFPS;
    private int targetUPS;

    public Engine(String windowTitle, Window.WindowOptions options, IAppLogic appLogic) {
        window = new Window(windowTitle, options, () -> {
            resize();
            return null;
        });
        targetFPS = options.fps;
        targetUPS = options.ups;;
        this.appLogic = appLogic;
        render = new Render();
        scene = new Scene();
        appLogic.init(window, scene, render);
        isRunning = true;
    }

    public void cleanup() {
        render.cleanup();
        scene.cleanup();
        window.cleanup();
    }

    public void start() {
        isRunning = true;
        run();
    }

    public void stop() {
        isRunning = false;
    }

    private void resize() {

    }

    private void run() {
        long initialTime = System.currentTimeMillis();
        float timePerUpdate = 1000f / targetUPS;
        float timePerFrame = 1000f / targetFPS;
        float deltaUpdate = 0f;
        float deltaFPS = 0f;

        long updateTime = initialTime;
        while (isRunning && !window.windowShouldClose()) {
            window.pollEvents();

            long now = System.currentTimeMillis();
            deltaUpdate += (now - initialTime) / timePerUpdate;
            deltaFPS += targetFPS > 0 ? (now - initialTime) / timePerFrame : 0;

            if (targetFPS <= 0 || deltaFPS >= 1) {
                appLogic.input(window, scene, now - initialTime);
            }

            if (deltaUpdate >= 1) {
                long diffTimeMillis = now - updateTime;
                appLogic.update(window, scene, diffTimeMillis);
                updateTime = now;
                deltaUpdate--;
            }

            if (targetFPS <= 0 || deltaFPS >= 1) {
                render.render(window, scene);
                deltaFPS--;
                window.update();
            }

            initialTime = now;
        }
        cleanup();
    }
}
