package com.alchemygames.engine.scene;

import org.joml.Math;
import org.joml.Matrix4f;

public class Projection {

    private static final float FOV = Math.toRadians(60.0f);
    private static final float Z_FAR = 1000.f;
    private static final float Z_NEAR = 0.01f;

    private Matrix4f projectionMatrix;

    public Projection(int width, int height) {
        projectionMatrix = new Matrix4f();
        updateProjectionMatrix(width, height);
    }

    public void updateProjectionMatrix(int width, int height) {
        projectionMatrix.setPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
