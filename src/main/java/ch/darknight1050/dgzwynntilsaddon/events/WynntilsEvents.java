package ch.darknight1050.dgzwynntilsaddon.events;

import com.wynntils.core.events.custom.WynnWorldEvent;

import ch.darknight1050.dgzwynntilsaddon.utils.WebUtils;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WynntilsEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void joinWorldEvent(WynnWorldEvent.Join e) {
        WebUtils.DownloadWaypoints();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void leaveWorldEvent(WynnWorldEvent.Leave e) {
        WebUtils.UploadWaypoints();
    }

}
