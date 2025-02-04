package com.alchemygames.engine.graph;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {

    private int textureId;
    private String texturePath;

    public Texture(int width, int height, ByteBuffer buff) {
        this.texturePath = "";

        generateTexture(width, height, buff);
    }

    public Texture(String texturePath) {

        try(MemoryStack stack = MemoryStack.stackPush()) {
            this.texturePath = texturePath;

            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer buff = stbi_load(texturePath, w, h, channels, 4);
            if (buff == null) {
                throw new RuntimeException("Couldn't load the image file [" + texturePath + "]");
            }

            int width = w.get();
            int height = h.get();

            generateTexture(width, height, buff);

            stbi_image_free(buff);
        }

    }

    private void generateTexture(int width, int height, ByteBuffer buff) {
        textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buff);
        glGenerateMipmap(GL_TEXTURE_2D);

    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void cleanup() {
        glDeleteTextures(textureId);
    }

    public int getTextureId() {
        return textureId;
    }

    public String getTexturePath() {
        return texturePath;
    }
}
