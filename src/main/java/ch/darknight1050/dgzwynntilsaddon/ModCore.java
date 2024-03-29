package ch.darknight1050.dgzwynntilsaddon;

import com.wynntils.core.framework.FrameworkManager;
import com.wynntils.modules.map.configs.MapConfig;
import com.wynntils.modules.map.instances.WaypointProfile;
import com.wynntils.modules.map.instances.WaypointProfile.WaypointType;

import ch.darknight1050.dgzwynntilsaddon.commands.*;
import ch.darknight1050.dgzwynntilsaddon.events.WynntilsEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Reference.NAME, modid = Reference.MOD_ID, acceptedMinecraftVersions = "[" + Reference.MINECRAFT_VERSIONS
        + "]", dependencies = "required-after:wynntils;", clientSideOnly = true)
public class ModCore {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        Reference.VERSION = e.getModMetadata().version;
        String[] splitDescription = e.getModMetadata().description.split(" ");
        try {
            Reference.BUILD_NUMBER = Integer.parseInt(splitDescription[splitDescription.length - 1]);
        } catch (NumberFormatException ignored) {
        }
        FrameworkManager.getEventBus().register(new WynntilsEvents());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        ClientCommandHandler.instance.registerCommand(new UpdateChestsCommand());
        ClientCommandHandler.instance.registerCommand(new FixChestsCommand());
        ClientCommandHandler.instance.registerCommand(new FixDuplicatesCommand());
    }

    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public static boolean IsLootChest(WaypointProfile waypoint) {
        return waypoint.getType().equals(WaypointType.LOOTCHEST_T1)
                || waypoint.getType().equals(WaypointType.LOOTCHEST_T2)
                || waypoint.getType().equals(WaypointType.LOOTCHEST_T3)
                || waypoint.getType().equals(WaypointType.LOOTCHEST_T4);
    }
    
    public static int GetWaypointsCount() {
        int waypointsCount = 0;
        for(WaypointProfile waypoint : MapConfig.Waypoints.INSTANCE.waypoints) {
            if(ModCore.IsLootChest(waypoint))
                waypointsCount++;
        }
        return waypointsCount;
    }
    
}
