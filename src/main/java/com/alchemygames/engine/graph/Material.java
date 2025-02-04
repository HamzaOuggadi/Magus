package com.alchemygames.engine.graph;

import java.util.ArrayList;
import java.util.List;

public class Material {

    private List<Mesh> meshList;
    private String texturePath;

    public Material() {
        meshList = new ArrayList<>();
    }

    public void cleanup() {
        meshList.forEach(Mesh::cleanup);
    }

    public String getTexturePath() {
        return texturePath;
    }

    public List<Mesh> getMeshList() {
        return meshList;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }
}
