package reifnsk.minimap;

class ReiEnumHelper {

        static final int RTintType[];
        static final int REnumOptionValue[];
        static final int REnumOption[];

        static
        {
            REnumOption = new int[EnumOption.values().length];

            try
            {
                REnumOption[EnumOption.MINIMAP.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) { }

            try
            {
                REnumOption[EnumOption.SHOW_MENU_KEY.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) { }

            try
            {
                REnumOption[EnumOption.MASK_TYPE.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) { }

            try
            {
                REnumOption[EnumOption.DIRECTION_TYPE.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) { }

            try
            {
                REnumOption[EnumOption.MAP_SHAPE.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror4) { }

            try
            {
                REnumOption[EnumOption.TEXTURE.ordinal()] = 6;
            }
            catch (NoSuchFieldError nosuchfielderror5) { }

            try
            {
                REnumOption[EnumOption.MAP_POSITION.ordinal()] = 7;
            }
            catch (NoSuchFieldError nosuchfielderror6) { }

            try
            {
                REnumOption[EnumOption.MAP_SCALE.ordinal()] = 8;
            }
            catch (NoSuchFieldError nosuchfielderror7) { }

            try
            {
                REnumOption[EnumOption.MAP_OPACITY.ordinal()] = 9;
            }
            catch (NoSuchFieldError nosuchfielderror8) { }

            try
            {
                REnumOption[EnumOption.LARGE_MAP_SCALE.ordinal()] = 10;
            }
            catch (NoSuchFieldError nosuchfielderror9) { }

            try
            {
                REnumOption[EnumOption.LARGE_MAP_OPACITY.ordinal()] = 11;
            }
            catch (NoSuchFieldError nosuchfielderror10) { }

            try
            {
                REnumOption[EnumOption.LARGE_MAP_LABEL.ordinal()] = 12;
            }
            catch (NoSuchFieldError nosuchfielderror11) { }

            try
            {
                REnumOption[EnumOption.FILTERING.ordinal()] = 13;
            }
            catch (NoSuchFieldError nosuchfielderror12) { }

            try
            {
                REnumOption[EnumOption.SHOW_COORDINATES.ordinal()] = 14;
            }
            catch (NoSuchFieldError nosuchfielderror13) { }

            try
            {
                REnumOption[EnumOption.FONT_SCALE.ordinal()] = 15;
            }
            catch (NoSuchFieldError nosuchfielderror14) { }

            try
            {
                REnumOption[EnumOption.UPDATE_FREQUENCY.ordinal()] = 16;
            }
            catch (NoSuchFieldError nosuchfielderror15) { }

            try
            {
                REnumOption[EnumOption.THREADING.ordinal()] = 17;
            }
            catch (NoSuchFieldError nosuchfielderror16) { }

            try
            {
                REnumOption[EnumOption.THREAD_PRIORITY.ordinal()] = 18;
            }
            catch (NoSuchFieldError nosuchfielderror17) { }

            try
            {
                REnumOption[EnumOption.LIGHTING.ordinal()] = 19;
            }
            catch (NoSuchFieldError nosuchfielderror18) { }

            try
            {
                REnumOption[EnumOption.LIGHTING_TYPE.ordinal()] = 20;
            }
            catch (NoSuchFieldError nosuchfielderror19) { }

            try
            {
                REnumOption[EnumOption.TERRAIN_UNDULATE.ordinal()] = 21;
            }
            catch (NoSuchFieldError nosuchfielderror20) { }

            try
            {
                REnumOption[EnumOption.TERRAIN_DEPTH.ordinal()] = 22;
            }
            catch (NoSuchFieldError nosuchfielderror21) { }

            try
            {
                REnumOption[EnumOption.TRANSPARENCY.ordinal()] = 23;
            }
            catch (NoSuchFieldError nosuchfielderror22) { }

            try
            {
                REnumOption[EnumOption.ENVIRONMENT_COLOR.ordinal()] = 24;
            }
            catch (NoSuchFieldError nosuchfielderror23) { }

            try
            {
                REnumOption[EnumOption.OMIT_HEIGHT_CALC.ordinal()] = 25;
            }
            catch (NoSuchFieldError nosuchfielderror24) { }

            try
            {
                REnumOption[EnumOption.HIDE_SNOW.ordinal()] = 26;
            }
            catch (NoSuchFieldError nosuchfielderror25) { }

            try
            {
                REnumOption[EnumOption.SHOW_CHUNK_GRID.ordinal()] = 27;
            }
            catch (NoSuchFieldError nosuchfielderror26) { }

            try
            {
                REnumOption[EnumOption.SHOW_SLIME_CHUNK.ordinal()] = 28;
            }
            catch (NoSuchFieldError nosuchfielderror27) { }

            try
            {
                REnumOption[EnumOption.RENDER_TYPE.ordinal()] = 29;
            }
            catch (NoSuchFieldError nosuchfielderror28) { }

            try
            {
                REnumOption[EnumOption.ENTITIES_RADAR.ordinal()] = 30;
            }
            catch (NoSuchFieldError nosuchfielderror29) { }

            try
            {
                REnumOption[EnumOption.MINIMAP_OPTION.ordinal()] = 31;
            }
            catch (NoSuchFieldError nosuchfielderror30) { }

            try
            {
                REnumOption[EnumOption.SURFACE_MAP_OPTION.ordinal()] = 32;
            }
            catch (NoSuchFieldError nosuchfielderror31) { }

            try
            {
                REnumOption[EnumOption.ABOUT_MINIMAP.ordinal()] = 33;
            }
            catch (NoSuchFieldError nosuchfielderror32) { }

            try
            {
                REnumOption[EnumOption.ENTITIES_RADAR_OPTION.ordinal()] = 34;
            }
            catch (NoSuchFieldError nosuchfielderror33) { }

            try
            {
                REnumOption[EnumOption.ENG_FORUM.ordinal()] = 35;
            }
            catch (NoSuchFieldError nosuchfielderror34) { }

            try
            {
                REnumOption[EnumOption.JP_FORUM.ordinal()] = 36;
            }
            catch (NoSuchFieldError nosuchfielderror35) { }

            try
            {
                REnumOption[EnumOption.DEATH_POINT.ordinal()] = 37;
            }
            catch (NoSuchFieldError nosuchfielderror36) { }

            try
            {
                REnumOption[EnumOption.ENTITY_PLAYER.ordinal()] = 38;
            }
            catch (NoSuchFieldError nosuchfielderror37) { }

            try
            {
                REnumOption[EnumOption.ENTITY_ANIMAL.ordinal()] = 39;
            }
            catch (NoSuchFieldError nosuchfielderror38) { }

            try
            {
                REnumOption[EnumOption.ENTITY_MOB.ordinal()] = 40;
            }
            catch (NoSuchFieldError nosuchfielderror39) { }

            try
            {
                REnumOption[EnumOption.ENTITY_SLIME.ordinal()] = 41;
            }
            catch (NoSuchFieldError nosuchfielderror40) { }

            try
            {
                REnumOption[EnumOption.ENTITY_SQUID.ordinal()] = 42;
            }
            catch (NoSuchFieldError nosuchfielderror41) { }

            try
            {
                REnumOption[EnumOption.ENTITY_LIVING.ordinal()] = 43;
            }
            catch (NoSuchFieldError nosuchfielderror42) { }

            try
            {
                REnumOption[EnumOption.ENTITY_LIGHTNING.ordinal()] = 44;
            }
            catch (NoSuchFieldError nosuchfielderror43) { }

            try
            {
                REnumOption[EnumOption.ENTITY_DIRECTION.ordinal()] = 45;
            }
            catch (NoSuchFieldError nosuchfielderror44) { }

            try
            {
                REnumOption[EnumOption.MARKER_OPTION.ordinal()] = 46;
            }
            catch (NoSuchFieldError nosuchfielderror45) { }

            try
            {
                REnumOption[EnumOption.MARKER.ordinal()] = 47;
            }
            catch (NoSuchFieldError nosuchfielderror46) { }

            try
            {
                REnumOption[EnumOption.MARKER_ICON.ordinal()] = 48;
            }
            catch (NoSuchFieldError nosuchfielderror47) { }

            try
            {
                REnumOption[EnumOption.MARKER_LABEL.ordinal()] = 49;
            }
            catch (NoSuchFieldError nosuchfielderror48) { }

            try
            {
                REnumOption[EnumOption.MARKER_DISTANCE.ordinal()] = 50;
            }
            catch (NoSuchFieldError nosuchfielderror49) { }

            try
            {
                REnumOption[EnumOption.DEFAULT_ZOOM.ordinal()] = 51;
            }
            catch (NoSuchFieldError nosuchfielderror50) { }

            try
            {
                REnumOption[EnumOption.AUTO_UPDATE_CHECK.ordinal()] = 52;
            }
            catch (NoSuchFieldError nosuchfielderror51) { }

            try
            {
                REnumOption[EnumOption.UPDATE_CHECK.ordinal()] = 53;
            }
            catch (NoSuchFieldError nosuchfielderror52) { }

            REnumOptionValue = new int[EnumOptionValue.values().length];

            try
            {
                REnumOptionValue[EnumOptionValue.PERCENT100.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror53) { }

            try
            {
                REnumOptionValue[EnumOptionValue.PERCENT75.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror54) { }

            try
            {
                REnumOptionValue[EnumOptionValue.PERCENT50.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror55) { }

            try
            {
                REnumOptionValue[EnumOptionValue.PERCENT25.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror56) { }

            RTintType = new int[TintType.values().length];

            try
            {
                RTintType[TintType.GRASS.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror57) { }

            try
            {
                RTintType[TintType.TALL_GRASS.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror58) { }

            try
            {
                RTintType[TintType.FOLIAGE.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror59) { }

            try
            {
                RTintType[TintType.WATER.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror60) { }
        }
    }
