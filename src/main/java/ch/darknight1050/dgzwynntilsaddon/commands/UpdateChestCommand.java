package ch.darknight1050.dgzwynntilsaddon.commands;

import ch.darknight1050.dgzwynntilsaddon.utils.WebUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;

public class UpdateChestCommand extends CommandBase implements IClientCommand {

    @Override
    public String getName() {
        return "updateChests";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Updates Chests";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        WebUtils.DownloadWaypoints();
        WebUtils.UploadWaypoints();
        sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "Updated your Chests"));
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

}
