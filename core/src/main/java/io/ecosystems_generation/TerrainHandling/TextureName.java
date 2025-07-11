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
    RABBIT;

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

    private static final TextureName[] GRASS_DEFAULTS = {
        GRASS_DEFAULT_1,
        GRASS_DEFAULT_2,
        GRASS_DEFAULT_3,
        GRASS_DEFAULT_4,
        GRASS_DEFAULT_5,
    };

    public static TextureName[] getDecorations(){
        return DECORATION_DEFAULTS;
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


}
