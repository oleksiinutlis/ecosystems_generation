package io.ecosystems_generation.TerrainHandling;

public enum TextureName {
    WATER_DEFAULT,

    TREE_PLACEHOLDER,

    DECORATION_DEFAULT_0,
    DECORATION_DEFAULT_1,
    DECORATION_DEFAULT_2,
    DECORATION_DEFAULT_3,
    DECORATION_DEFAULT_4,
    DECORATION_DEFAULT_5,
    DECORATION_DEFAULT_6,

    DECORATION_LILY_PAD_0,
    DECORATION_LILY_PAD_1,
    DECORATION_LILY_PAD_2,
    DECORATION_LILY_PAD_3,

    BUSH_0,
    BUSH_1,

    GRASS_DEFAULT_1,
    GRASS_DEFAULT_2,
    GRASS_DEFAULT_3,
    GRASS_DEFAULT_4,
    GRASS_DEFAULT_5,

    GRASS_WATER_EDGE_LEFT,
    GRASS_WATER_EDGE_RIGHT,
    GRASS_WATER_EDGE_TOP,
    GRASS_WATER_EDGE_BOTTOM,
    GRASS_WATER_OUTER_TOP_LEFT,
    GRASS_WATER_OUTER_TOP_RIGHT,
    GRASS_WATER_OUTER_BOTTOM_LEFT,
    GRASS_WATER_OUTER_BOTTOM_RIGHT,
    GRASS_WATER_INNER_TOP_LEFT,
    GRASS_WATER_INNER_TOP_RIGHT,
    GRASS_WATER_INNER_BOTTOM_LEFT,
    GRASS_WATER_INNER_BOTTOM_RIGHT,

    CARROT,

    BOAR,
    CHICKEN_0,
    CHICKEN_1,
    CHICKEN_2,
    CHICKEN_3;

    private static final TextureName[] DECORATION_DEFAULTS = {
        DECORATION_DEFAULT_0,
        DECORATION_DEFAULT_1,
        DECORATION_DEFAULT_2,
        DECORATION_DEFAULT_3,
        DECORATION_DEFAULT_4,
        DECORATION_DEFAULT_5,
        DECORATION_DEFAULT_6,
        BUSH_0,
        BUSH_1,
    };

    private static final TextureName[] DECORATION_LILY_PADS = new TextureName[]{
            DECORATION_LILY_PAD_0,
            DECORATION_LILY_PAD_1,
            DECORATION_LILY_PAD_2,
            DECORATION_LILY_PAD_3,
    };

    private static final TextureName[] GRASS_DEFAULTS = {
        GRASS_DEFAULT_1,
        GRASS_DEFAULT_2,
        GRASS_DEFAULT_3,
        GRASS_DEFAULT_4,
        GRASS_DEFAULT_5,
    };

    private static final TextureName[] CHICKEN_TEXTURES = {
            CHICKEN_0,
            CHICKEN_1,
            CHICKEN_2,
            CHICKEN_3,
    };

    public static TextureName[] getDecorations(){
        return DECORATION_DEFAULTS;
    }

    public static TextureName[] getWaterDecoration(){
        return DECORATION_LILY_PADS;
    }

    public static TextureName[] getChickenTextures(){
        return CHICKEN_TEXTURES;
    }

    public static int getWaterDecorationSize(){
        return DECORATION_LILY_PADS.length;
    }

    public static int getDecorationSize(){
        return DECORATION_DEFAULTS.length;
    }

    public static TextureName grassFromInt(int value){
        if (value >= 0 && value <= 4) return GRASS_DEFAULTS[value];
        return GRASS_DEFAULT_1;
    }

    public static TextureName decorationFromInt(int value){
        if (value >= 0 && value < getDecorationSize()) return DECORATION_DEFAULTS[value];
        return DECORATION_DEFAULT_1;
    }

    public static TextureName waterDecorationFromInt(int value){
        if (value >= 0 && value < getWaterDecorationSize()) return DECORATION_LILY_PADS[value];
        return DECORATION_LILY_PAD_0;
    }
}
