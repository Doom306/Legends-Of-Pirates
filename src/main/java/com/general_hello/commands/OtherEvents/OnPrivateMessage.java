package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Config;
import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.Others.UpdateIgniteCoinsCommand;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.UserPhoneUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OnPrivateMessage extends ListenerAdapter {
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(InfoUserCommand.randomColor());
        if (Data.progress.containsKey(event.getAuthor())) {
            Integer progress = Data.progress.get(event.getAuthor());
            if (progress == 1) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(event.getMessage().getContentRaw());
                Data.answers.put(event.getAuthor(), arrayList);
                Data.progress.put(event.getAuthor(), 2);

                ArrayList<String> oldAnswers = Data.answers.get(event.getAuthor());

                embedBuilder.setTitle("Account Summary");
                embedBuilder.setDescription("Name: ***" + oldAnswers.get(0) + "***\n" +
                        "Discord name: " + event.getAuthor().getAsMention() + "\n" +
                        "Account creation date: " + DateTimeFormatter.ofPattern("M/d/u h:m:s a").format(event.getAuthor().getTimeCreated()) + "\n" +
                        "Agreed to all rules: ✅").setFooter("Thank you for registering and 1000 credits have been added to your account!!");
                embedBuilder.setColor(InfoUserCommand.randomColor());
                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

                UserPhoneUser user = new UserPhoneUser(oldAnswers.get(0), event.getAuthor(), 1000);
                DatabaseManager.INSTANCE.setCredits(event.getAuthor().getIdLong(), 1000);
                Data.userUserPhoneUserHashMap.put(event.getAuthor(), user);
                Data.userPhoneUsers.add(user);

                DatabaseManager.INSTANCE.newInfo(event.getAuthor().getIdLong(), oldAnswers.get(0));
                Guild guildById = event.getJDA().getGuildById(843769353040298011L);
                try {
                    guildById.addRoleToMember(guildById.getMember(event.getAuthor()), guildById.getRolesByName("member", true).get(0)).queue();
                } catch (Exception ignored) {}

                try {
                    UpdateIgniteCoinsCommand.loadData();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Data.realNameUserPhoneUserHashMap.put(oldAnswers.get(0), user);
            }
            return;
        }

        if (event.getMessage().getContentRaw().startsWith("delete") && event.getAuthor().getId().equals(Config.get("owner_id"))) {
            String[] split = event.getMessage().getContentRaw().split("\\s++");
            String messageId = split[2];
            String channelId = split[1];
            event.getJDA().getTextChannelById(channelId).retrieveMessageById(messageId).queue((message -> {
                if (message != null) {
                    event.getChannel().sendMessage("Deleting this message now.....\n" + message.getContentDisplay()).queue();
                    message.delete().queue();
                    event.getChannel().sendMessage("Successfully deleted it!").queue();
                    return;
                }
                event.getChannel().sendMessage("The message was not found!").queue();
            }));
        }
    }
}