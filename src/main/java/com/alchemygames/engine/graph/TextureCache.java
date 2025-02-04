package com.alchemygames.engine.graph;

import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TextureCache {

    public static final String DEFAULT_TEXTURE = "src/main/resources/textures/cube.png";

    private Map<String, Texture> textureMap;


    public TextureCache() {
        textureMap = new HashMap<>();
        textureMap.put(DEFAULT_TEXTURE, new Texture(DEFAULT_TEXTURE));
    }

    public Texture createTexture(String texturePath) {
        return textureMap.computeIfAbsent(texturePath, Texture::new);
    }

    public Texture getTexture(String texturePath) {
        if (texturePath != null) {
            return textureMap.get(texturePath);
        } else {
            Logger.warn("Couldn't load texture, texturePath is null, loading default texture.");
            return textureMap.get(DEFAULT_TEXTURE);
        }
    }

    public void cleanup() {
        textureMap.values().forEach(Texture::cleanup);
    }

}
