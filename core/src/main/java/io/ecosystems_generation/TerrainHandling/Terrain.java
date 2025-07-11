package io.ecosystems_generation.TerrainHandling;

public class Terrain {
    private Material materialType;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // flag for terrain cleanup
    private boolean isChecked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terrain terrain = (Terrain) o;
        return materialType == terrain.materialType;
    }

    public boolean checked(){
        return isChecked;
    }

    public void markAsChecked(){
        isChecked = true;
    }

    public Material getMaterialType(){
        return this.materialType;
    }

    public void setMaterialType(Material material){
        this.materialType = material;
    }

    public Terrain(Material material, int x, int y){
        setMaterialType(material);
        this.x = x;
        this.y = y;
    }
}
