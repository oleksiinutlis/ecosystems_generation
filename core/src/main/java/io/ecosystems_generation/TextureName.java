package io.ecosystems_generation;

public enum TextureName {
    WATER_DEFAULT,

    STONE_DEFAULT_1,
    STONE_DEFAULT_2,
    STONE_DEFAULT_3,
    STONE_DEFAULT_4,
    STONE_DEFAULT_5,

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
    GRASS_WATER_INNER_BOTTOM_RIGHT;

    public static TextureName grassFromInt(int value){
        switch (value){
            case 1:
                return GRASS_DEFAULT_1;
            case 2:
                return GRASS_DEFAULT_2;
            case 3:
                return GRASS_DEFAULT_3;
            case 4:
                return GRASS_DEFAULT_4;
            case 5:
                return GRASS_DEFAULT_5;
        }
        return GRASS_DEFAULT_1;
    }

    public static TextureName stoneFromInt(int value){
        switch (value){
            case 1:
                return STONE_DEFAULT_1;
            case 2:
                return STONE_DEFAULT_2;
            case 3:
                return STONE_DEFAULT_3;
            case 4:
                return STONE_DEFAULT_4;
            case 5:
                return STONE_DEFAULT_5;
        }
        return STONE_DEFAULT_1;
    }


}
