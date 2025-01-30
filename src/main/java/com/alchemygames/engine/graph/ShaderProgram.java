package com.alchemygames.engine.graph;

import com.alchemygames.utils.FileUtils;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private int programId;

    public record ShaderModuleData(String shaderFile, int shaderType) {

    }

    public ShaderProgram(List<ShaderModuleData> shaderModuleDataList) {

        programId = glCreateProgram();

        if (programId == 0) {
            throw new RuntimeException("Error Creating Shader Program");
        }

        List<Integer> shaderModules = new ArrayList<>();
        shaderModuleDataList.forEach(shaderModuleData -> {
            shaderModules.add(createShader(FileUtils.readFile(shaderModuleData.shaderFile), shaderModuleData.shaderType));
        });

        link(shaderModules);

    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    public void validateProgram() {
        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            throw new RuntimeException("Error validating Shader Program : " + glGetProgramInfoLog(programId));
        }
    }

    public int getProgramId() {
        return programId;
    }

    protected int createShader(String shaderCode, int shaderType) {

        int shaderId = glCreateShader(shaderType);

        if (shaderId == 0) {
            throw new RuntimeException("Error creating Shader, type : " + shaderId);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling Shader code : " + glGetShaderInfoLog(shaderId));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    private void link(List<Integer> shaderModules) {
        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking Shaders : " + glGetProgramInfoLog(programId));
        }

        shaderModules.forEach(shaderModule -> glDetachShader(programId, shaderModule));
        shaderModules.forEach(GL20::glDeleteShader);
    }
}
