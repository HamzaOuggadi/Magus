package com.alchemygames.engine.graph;

import com.alchemygames.engine.Window;
import com.alchemygames.engine.scene.Scene;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Render {

    private String vertexShaderCode = """
            #version 330 core
                        
            layout (location = 0) in vec3 aPos;
                        
            void main() {
                gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
            }
            """;

    private String fragShaderCode = """
            #version 330 core
                        
            out vec4 FragColor;
                        
            void main() {
                FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);
            }
            """;

    private float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f,  0.5f, 0.0f
    };

    private float[] quadVertices = {
            0.5f,  0.5f, 0.0f,  // top right
            0.5f, -0.5f, 0.0f,  // bottom right
            -0.5f, -0.5f, 0.0f,  // bottom left
            -0.5f,  0.5f, 0.0f   // top left
    };

    private int[] indices = {
            0, 1, 3,
            1, 2, 3
    };

    private int vbo;
    private int vao;
    private int ebo;

    private int vertexShader;
    private int fragShader;
    private int shaderProgram;

    public Render() {
        GL.createCapabilities();
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // Setting the VBO and passing the position data
//        FloatBuffer verticesBuffer = MemoryUtil.memCallocFloat(vertices.length);
//        verticesBuffer.put(0, vertices);
//
//        vbo = glGenBuffers();
//        glBindBuffer(GL_ARRAY_BUFFER, vbo);
//        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        FloatBuffer quadVerticesBuffer = MemoryUtil.memCallocFloat(quadVertices.length);
        quadVerticesBuffer.put(0, quadVertices);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, quadVerticesBuffer, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);


        IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
        indicesBuffer.put(0, indices);
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        // Creating the Vertex Shader program
        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderCode);
        glCompileShader(vertexShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling the Vertex Shader.");
        }

        // Creating the Fragment Shader
        fragShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShader, fragShaderCode);
        glCompileShader(fragShader);

        if (glGetShaderi(fragShader, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling the Fragment Shader.");
        }

        // Creating the Shader Program and linking the shaders
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragShader);
        glLinkProgram(shaderProgram);

        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking Shaders.");
        }

        // Creating the VAO

        // Using the program
        glUseProgram(shaderProgram);

        // Binding VAO
        glBindVertexArray(vao);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        MemoryUtil.memFree(quadVerticesBuffer);
        MemoryUtil.memFree(indicesBuffer);

        //glBindVertexArray(0);
    }

    public void cleanup() {

    }
}
