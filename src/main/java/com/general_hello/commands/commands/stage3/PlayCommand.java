package com.general_hello.commands.commands.stage3;

import com.general_hello.commands.Objects.Map.Grid;
import com.general_hello.commands.Objects.Map.Map;
import com.general_hello.commands.Utils.EmbedUtil;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayCommand extends SlashCommand {
    public static HashMap<Grid, ArrayList<Long>> userLocations = new HashMap<>();
    public static HashMap<Long, Grid> userToLocations = new HashMap<>();
    public PlayCommand() {
        this.name = "play";
        this.help = "Shows the map interface and moving of the player";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        Grid gridFromLocation = Map.getGridFromLocation(8, 8);
        if (userToLocations.containsKey(event.getUser().getIdLong())) {
            Grid oldGrid = userToLocations.get(event.getUser().getIdLong());
            ArrayList<Long> userOld = userLocations.get(oldGrid);
            userOld.remove(event.getUser().getIdLong());
            userLocations.put(oldGrid, userOld);
        }
        ArrayList<Long> userFromGrids = new ArrayList<>();
        if (userLocations.containsKey(gridFromLocation)) {
            userFromGrids = userLocations.get(gridFromLocation);
        }
        userFromGrids.add(event.getUser().getIdLong());
        userLocations.put(gridFromLocation, userFromGrids);
        userToLocations.put(event.getUser().getIdLong(), gridFromLocation);
        MessageEmbed messageEmbed = EmbedUtil.defaultEmbed(Map.buildMap(gridFromLocation));
        String userid = event.getUser().getId();
        event.replyEmbeds(messageEmbed).addActionRows(
                ActionRow.of(
                    Button.secondary(userid + ":upleft", Emoji.fromMarkdown("↖")),
                    Button.secondary(userid + ":up", Emoji.fromMarkdown("⬆")),
                    Button.secondary(userid + ":upright", Emoji.fromMarkdown("↗"))
                ), ActionRow.of(
                    Button.secondary(userid + ":left", Emoji.fromMarkdown("⬅")),
                    Button.secondary("empty", "\u200E").asDisabled(),
                    Button.secondary(userid + ":right", Emoji.fromMarkdown("➡"))
                ), ActionRow.of(
                    Button.secondary(userid + ":downleft", Emoji.fromMarkdown("↙")),
                    Button.secondary(userid + ":down", Emoji.fromMarkdown("⬇")),
                    Button.secondary(userid + ":downright", Emoji.fromMarkdown("↘"))
                )
        ).queue();
    }
}
