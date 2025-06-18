package io.ecosystems_generation;

public class Terrain {
    private Material materialType;

    public Material getMaterialType(){
        return this.materialType;
    }

    public void setMaterialType(Material material){
        this.materialType = material;
    }

    public Terrain(Material material){
        setMaterialType(material);
    }
}
