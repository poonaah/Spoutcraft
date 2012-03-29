package reifnsk.minimap;

public enum EnumOptionValue
{
    ENABLE(0xa000ff00, "Enabled"),
    DISABLE(0xa0ff0000, "Disabled"),
    SURFACE(0xa000ff00),
    CAVE(0xa0ff0000),
    BIOME(0xa00080ff),
    SQUARE(0xa00000ff),
    ROUND(0xa000ff00),
    DYNAMIC(0xa000ff00),
    DAY_TIME(0xa000ffff),
    NIGHT_TIME(0xa0ff8000),
    NEW_LIGHTING(0xa000ff00),
    OLD_LIGHTING(0xa0ff0000),
    VERY_LOW(0xa00000ff),
    LOW(0xa000ffff),
    MIDDLE(0xa000ff00),
    HIGH(0xa0ff8000),
    VERY_HIGH(0xa0ff0000),
    SUB_OPTION(0xa0404040, "->"),
    UPPER_LEFT(0xa000ff00),
    LOWER_LEFT(0xa000ff00),
    UPPER_RIGHT(0xa000ff00),
    LOWER_RIGHT(0xa000ff00),
    TYPE1(0xa000ff00),
    TYPE2(0xa000ff00),
    TYPE3(0xa000ff00),
    TYPE4(0xa000ff00),
    TYPE5(0xa000ff00),
    AUTO(0xa00000ff),
    SMALL(0xa000ff00, "Small"),
    NORMAL(0xa000ff00, "Normal"),
    LARGE(0xa000ff00, "Large"),
    LARGER(0xa000ff00, "Larger"),
    GUI_SCALE(0xa00080ff),
    X0_5(0xa000ff00, "x0.5"),
    X1_0(0xa000ff00, "x1.0"),
    X1_5(0xa000ff00, "x1.5"),
    X2_0(0xa000ff00, "x2.0"),
    X4_0(0xa000ff00, "x4.0"),
    X8_0(0xa000ff00, "x8.0"),
    PERCENT25(0xa000ff00, "25%"),
    PERCENT50(0xa000ff00, "50%"),
    PERCENT75(0xa000ff00, "75%"),
    PERCENT100(0xa000ff00, "100%"),
    DEPTH(0xa0008080),
    STENCIL(0xa000ff00),
    EAST(0xa000ff00),
    NORTH(0xa00000ff),
    REI_MINIMAP(0xa000ff00),
    ZAN_MINIMAP(0xa00000ff),
    UPDATE_CHECK(0xa000ff00, "Check"),
    UPDATE_CHECKING(0xa0ff8000, "Checking..."),
    UPDATE_FOUND1(0xa000ffff, "Found!!"),
    UPDATE_FOUND2(0xa00000ff, "Found!"),
    UPDATE_NOT_FOUND(0xa0ff0000, "Not Found"),
    VERSION(0xa000ff00, "v3.0_03"),
    AUTHOR(0xa000ff00, "ReiFNSK");

    public final int color;
    private final String text;

    private EnumOptionValue(int j)
    {
        color = j;
        text = ReiMinimap.capitalize(name());
    }

    private EnumOptionValue(int j, String s1)
    {
        color = j;
        text = s1;
    }

    public String text()
    {
        return text;
    }

    public static EnumOptionValue bool(boolean flag)
    {
        return flag ? ENABLE : DISABLE;
    }

    public static boolean bool(EnumOptionValue enumoptionvalue)
    {
        return enumoptionvalue == ENABLE;
    }
}
