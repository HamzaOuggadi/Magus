package com.alchemygames.engine.graph;

import com.alchemygames.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SceneRenderer {

    private ShaderProgram shaderProgram;

    public SceneRenderer() {
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/main/resources/shaders/scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/main/resources/shaders/scene.frag", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderModuleDataList);
    }

    public void render(Scene scene) {
        shaderProgram.bind();

        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoId());
            glDrawArrays(GL_TRIANGLES, 0, mesh.getNumVertices());
        });

        glBindVertexArray(0);
        shaderProgram.unbind();
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }
}
