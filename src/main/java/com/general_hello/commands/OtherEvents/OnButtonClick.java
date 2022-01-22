package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Config;
import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.RPG.Items.Initializer;
import com.general_hello.commands.RPG.Objects.Chest;
import com.general_hello.commands.RPG.Objects.RPGEmojis;
import com.general_hello.commands.RPG.RpgUser.RPGDataUtils;
import com.general_hello.commands.RPG.RpgUser.RPGUser;
import com.general_hello.commands.commands.Currency.BalanceCommand;
import com.general_hello.commands.commands.Currency.DropCommand;
import com.general_hello.commands.commands.DefaultCommands.HelpCommand;
import com.general_hello.commands.commands.Emoji.Emojis;
import com.general_hello.commands.commands.Giveaway.DataGiveaway;
import com.general_hello.commands.commands.Giveaway.Giveaway;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.BlackjackGame;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.GameHandler;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.MessageIdToReport;
import com.general_hello.commands.commands.User.Report;
import com.general_hello.commands.commands.User.UserPhoneUser;
import com.general_hello.commands.commands.Utils.UtilNum;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class OnButtonClick extends ListenerAdapter {
    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        // users can spoof this id so be careful what you do with this
        String[] id = event.getComponentId().split(":"); // this is the custom id we specified in our button
        String authorId = id[0];

        if (id.length == 1) {
            return;
        }

        String type = id[1];
        // When storing state like this is it is highly recommended to do some kind of verification that it was generated by you, for instance a signature or local cache

        if (!authorId.equals("0000") && !authorId.equals(event.getUser().getId())) {
            return;
        }

        boolean disableOrEnable = false;
        try {
            disableOrEnable = !event.getMember().hasPermission(Permission.MANAGE_SERVER) && !event.getMember().getRoles().contains(event.getGuild().getRoleById(888627140046749697L));
        } catch (Exception ignored) {
        }

        switch (type)
        {
            case "marrydono":
                String s = id[2];
                String a = id[3];
                if (s.equals(event.getUser().getId()) || a.equals(event.getUser().getId())) {
                    event.reply("You cannot donate to yourself!").setEphemeral(true).queue();
                    return;
                }
                Member receiver = event.getGuild().getMemberById(s);
                Member receiver1 = event.getGuild().getMemberById(a);
                DatabaseManager.INSTANCE.setCredits(Long.parseLong(s), 50000);
                DatabaseManager.INSTANCE.setCredits(Long.parseLong(a), 50000);
                DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), -100000);
                event.reply("Thank you for giving 50,000 to " + receiver.getAsMention() + " and " + receiver1.getAsMention()).setEphemeral(true).queue();
                receiver1.getUser().openPrivateChannel().queue((privateChannel -> {
                    privateChannel.sendMessage(event.getUser().getName() + " gave you " + RPGEmojis.credits + "50,000 as a marriage gift!").queue();
                }));
                receiver.getUser().openPrivateChannel().queue((privateChannel -> {
                    privateChannel.sendMessage(event.getUser().getName() + " gave you " + RPGEmojis.credits + "50,000 as a marriage gift!").queue();
                }));
                break;
            case "join":
                Giveaway giveaway = DataGiveaway.giveawayHashMap.get(event.getMessage());

                if (giveaway.getUsers().contains(event.getUser())) return;

                if (!event.getMember().getRoles().contains(giveaway.getRequirement())) return;

                giveaway.addUser(event.getUser());
                DataGiveaway.giveawayHashMap.put(event.getMessage(), giveaway);
                event.reply("Successfully joined the giveaway!").setEphemeral(true).queue();
                giveaway.getChannel().sendMessage(event.getUser().getName() + " joined!").queue((message -> {
                    message.delete().queueAfter(1, TimeUnit.MINUTES);
                }));
                break;
            case "claim":
                if (!DropCommand.isClaimed.get(event.getMessageIdLong())) {
                    int randomNum = UtilNum.randomNum(-100000, 1000000);
                    DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), randomNum);
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("New Chest Drop!!!").setTimestamp(OffsetDateTime.now()).setColor(InfoUserCommand.randomColor());
                    embedBuilder.setDescription("A santa's bag has been found! And " + event.getMember().getAsMention() + " was the first one to open it!\n" +
                            "\n" +
                            "Rewards:\n" +
                            "```java\n" +
                            randomNum + " credits" +
                            "\n```");
                    embedBuilder.setThumbnail("https://images-ext-2.discordapp.net/external/tgSIaqsZt7UsINNUUvja3uKGGiBPg08cfQDSYDVBDNs/https/cdn.discordapp.com/emojis/862415055622635560.png");
                    event.getMessage().editMessageEmbeds(embedBuilder.build()).setActionRow(Button.of(ButtonStyle.SUCCESS, "OPENEDCHEST", "Grabbed by " + event.getMember().getEffectiveName()).asDisabled(),
                            DropCommand.button.get(event.getMessageIdLong())).queue();
                    DropCommand.isClaimed.put(event.getMessageIdLong(), true);
                }
                break;
            case "claimdaily":
                if (!DropCommand.isClaimed.get(event.getMessageIdLong())) {
                    if (!RPGDataUtils.isRPGUser(event.getUser())) {
                        return;
                    }
                    Chest chest = Initializer.chestToId.get("commonchest");
                    RPGUser.addItem(event.getUser().getIdLong(), 1, "commonchest");
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("Common Chest found!").setTimestamp(OffsetDateTime.now()).setColor(InfoUserCommand.randomColor());
                    embedBuilder.setDescription("A new common chest " + RPGEmojis.common_chest + " has been found! And " + event.getMember().getAsMention() + " was the first one to open it!\n" +
                            "\n" +
                            "Rewards: " +
                            chest.getEmojiOfItem() + " **" + chest.getName() + "**");
                    embedBuilder.setThumbnail("https://cdn.discordapp.com/emojis/861390923640471572.gif");
                    event.getMessage().editMessageEmbeds(embedBuilder.build()).setActionRow(Button.of(ButtonStyle.SUCCESS, "OPENEDCHEST", "Opened by " + event.getMember().getEffectiveName()).asDisabled(),
                            Button.secondary("IGNOREMEEEE", "Dropped by Someone").asDisabled()).queue();
                    DropCommand.isClaimed.put(event.getMessageIdLong(), true);
                }
                break;
            case "nope":
            case "end":
                event.getMessage().delete().queue();
                break;
            case "user":
                event.getMessage().delete().queue();
                HelpCommand.updateDefaultActionRows(event);
                ArrayList<ActionRow> actionRows = HelpCommand.actionRows;
                event.getChannel().sendMessageEmbeds(helpCrap(4, event).build()).setActionRows(
                        actionRows
                ).queue();
                return;
            case "bot":
                event.getMessage().delete().queue();
                HelpCommand.updateDefaultActionRows(event);
                actionRows = HelpCommand.actionRows;
                event.getChannel().sendMessageEmbeds(helpCrap(2, event).build()).setActionRows(
                        actionRows
                ).queue();
                return;
            case "currency":
                event.getMessage().delete().queue();
                HelpCommand.updateDefaultActionRows(event);
                actionRows = HelpCommand.actionRows;
                event.getChannel().sendMessageEmbeds(helpCrap(7, event).build()).setActionRows(
                        actionRows
                ).queue();
                return;
            case "info":
                event.getMessage().delete().queue();
                HelpCommand.updateDefaultActionRows(event);
                actionRows = HelpCommand.actionRows;
                event.getChannel().sendMessageEmbeds(helpCrap(1, event).build()).setActionRows(
                        actionRows
                ).queue();
                return;
            case "mod":
                event.getMessage().delete().queue();
                HelpCommand.updateDefaultActionRows(event);
                actionRows = HelpCommand.actionRows;
                event.getChannel().sendMessageEmbeds(helpCrap(3, event).build()).setActionRows(
                        actionRows
                ).queue();
                return;
            case "rpg":
                event.getMessage().delete().queue();
                HelpCommand.updateDefaultActionRows(event);
                actionRows = HelpCommand.actionRows;
                event.getChannel().sendMessageEmbeds(helpCrap(6, event).build()).setActionRows(
                        actionRows
                ).queue();
                return;
            case "game":
                SelectionMenu menu = SelectionMenu.create("menu:class")
                        .setPlaceholder("Choose the game you want to find help on") // shows the placeholder indicating what this menu is for
                        .setRequiredRange(1, 1) // only one can be selected
                        .addOption("Uno", "uno")
                        .addOption("Blackjack", "bj")
                        .addOption("Guess the number", "gn")
                        .addOption("Trivia", "trivia")
                        .addOption("Hangman", "hangman")
                        .build();

                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(helpCrap(5, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                return;
            case "backgames":
                event.getMessage().delete().queue();
                HelpCommand.updateDefaultActionRows(event);
                actionRows = HelpCommand.actionRows;
                event.getChannel().sendMessageEmbeds(HelpCommand.embedBuilder.build()).setActionRows(
                        actionRows
                ).queue();
                return;
            case "accept":
                String arrow = "<a:arrow_1:862525611465113640>";

                event.getMessage().delete().queue();
                EmbedBuilder em = new EmbedBuilder().setTitle("Stored data").setFooter("Press the Accept button if you accept the data that will be stored!\n");
                em.setDescription("The bot stores the following data:\n" +
                        arrow + " Reads all sent messages in the server the bot is in.\n" +
                        arrow + " Reads all the messages you sent to the bot.\n" +
                        arrow + " Reads your ignite coins.\n" +
                        arrow + " Reads your user name, profile picture, nitro status, and user id.\n" +
                        arrow + " Reads all the permissions you have on that server.");
                event.getChannel().sendMessageEmbeds(em.build()).setActionRow(
                        Button.primary("0000:yes", "Accept").withEmoji(Emoji.fromEmote("verify", Long.parseLong("863204252188672000"), true))
                ).queue();
                event.deferEdit().queue();
                break;
            case "validreport":
                Report report = MessageIdToReport.messageReport.get(event.getMessage());
                EmbedBuilder embedBuilder = new EmbedBuilder();
                User user = report.getReporter();
                User author = report.getAuthorOfMessage();
                UserPhoneUser userPhoneUser = Data.userUserPhoneUserHashMap.get(user);
                Message message = report.getMessage();
                embedBuilder.setTitle("Report Closed").setColor(Color.GREEN);
                TextChannel channel = report.getSourceChannelOfReport();
                UserPhoneUser authorPhoneUser = Data.userUserPhoneUserHashMap.get(author);
                embedBuilder.setTimestamp(OffsetDateTime.now()).setFooter("This feature is made by HELLO66 and General Koala");
                embedBuilder.setDescription("Message: " + (message == null ? "MESSAGE WAS DELETED" : message.getContentDisplay()) + "\n" +
                        "Raw Message: `" + (message == null ? "MESSAGE WAS DELETED" : message.getContentDisplay()) + "`" +
                        "\n\n" +
                        "Reported by: " + user.getName() + "\n\n" +
                        "Reporter info:\n" +
                        "Tag: `" + user.getAsTag() + "`\n" +
                        "Real name: `" + (userPhoneUser == null ? "Not registered" : userPhoneUser.getRealName()) + "`\n\n" +
                        "Author of the Message: " + author.getName() + "\n" +
                        "\n" +
                        "Author info:\n" +
                        "Tag: `" + author.getAsTag() + "`\n" +
                        "Real name: `" + (authorPhoneUser == null ? "Not registered" : authorPhoneUser.getRealName()) + "`\n\n" +
                        "\n" +
                        "Message sent in " + channel.getAsMention() + "\n" +
                        "\n" +
                        "Tasks done:\n" +
                        "***Deleted the message***: ✅\n" +
                        "***Shamed the user***: ✅\n" +
                        "***Notified the user***: ✅\n" +
                        "***Notified the reporter***: ✅");
                event.getHook().editOriginalEmbeds(embedBuilder.build()).setActionRow(
                        Button.secondary("1111:DONEANDDONE", "Approval done by " + event.getMember().getEffectiveName()).asDisabled()
                ).queue();

                try {
                    user.openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessage("Your report has been approved and the necessary actions have been done! 100,000 credits has been added to you! Thank for serving your country :flag_ph:").queue();
                        DatabaseManager.INSTANCE.setCredits(user.getIdLong(), 100_000);
                    });
                } catch (Exception e) {
                    event.getChannel().sendMessage("Failed to notify the reporter. Due to the reporter not allowing DM's").queue();
                }
                String quote = "Explain your actions by sending a DM to SkyacinthClues (Lagut ka na)";
                quote = quote.replaceAll("\\s+", "+");
                String name = author.getName().replace(" ", "%20");
                String pngUrl = "https://api.popcat.xyz/welcomecard?background=https://media.discordapp.net/attachments/911455088311140412/911462939817545788/259122638_983782225534207_5040543541645712794_n.png&text1=" + name + "&text2=Strive+to+be+Christlike+in+everything+you+do&text3=" + quote + "&avatar=" + author.getEffectiveAvatarUrl();

                try {
                    author.openPrivateChannel().queue((privateChannel -> {
                        privateChannel.sendMessage("Fix your behaviour in messaging!!!").queue();
                    }));
                } catch (Exception e) {
                    event.getChannel().sendMessage("Failed to notify the author of the message. Due to the person not allowing DM's").queue();

                }
                event.getJDA().getTextChannelById(911788233355046912L).sendMessage(pngUrl).queue();
                event.getJDA().getTextChannelById(876363970108334162L).sendMessage(pngUrl).queue();

                try {
                    report.getMessage().delete().queue();
                } catch (Exception e) {
                    event.getChannel().sendMessage("Message was deleted already! Failed to delete it!").queue();
                }
                Role memberRole = event.getGuild().getRoleById(876371852799389736L);
                Role muteRole = event.getGuild().getRoleById(902857447809646612L);

                event.getGuild().removeRoleFromMember(author.getId(), memberRole).queue();
                event.getGuild().addRoleToMember(author.getId(), muteRole).queue();
                break;
            case "invalidreport":
                 report = MessageIdToReport.messageReport.get(event.getMessage());
                embedBuilder = new EmbedBuilder();
                 user = report.getReporter();
                 author = report.getAuthorOfMessage();
                 userPhoneUser = Data.userUserPhoneUserHashMap.get(user);
                embedBuilder.setTitle("Report Marked As Spam").setColor(Color.GREEN);
                 message = report.getMessage();
                 authorPhoneUser = Data.userUserPhoneUserHashMap.get(author);
                embedBuilder.setTimestamp(OffsetDateTime.now()).setFooter("This feature is made by HELLO66 and General Koala");
                embedBuilder.setDescription("Message: " + (message == null ? "MESSAGE WAS DELETED" : message.getContentDisplay()) + "\n" +
                        "Raw Message: `" + (message == null ? "MESSAGE WAS DELETED" : message.getContentDisplay()) + "`" +
                        "\n\n" +
                        "Reported by: " + user.getName() + "\n\n" +
                        "Reporter info:\n" +
                        "Tag: `" + user.getAsTag() + "`\n" +
                        "Real name: `" + (userPhoneUser == null ? "Not registered" : userPhoneUser.getRealName()) + "`\n\n" +
                        "Author of the Message: " + author.getName() + "\n" +
                        "\n" +
                        "Author info:\n" +
                        "Tag: `" + author.getAsTag() + "`\n" +
                        "Real name: `" + (authorPhoneUser == null ? "Not registered" : authorPhoneUser.getRealName()) + "`\n\n" +
                        "\n" +
                        "Message sent in " + report.getSourceChannelOfReport().getAsMention() + "\n" +
                        "\n" +
                        "Tasks done:\n" +
                        "***Notified the reporter***: ✅");
                event.getHook().editOriginalEmbeds(embedBuilder.build()).setActionRow(
                        Button.secondary("1111:DONEANDDONE", "Marked as spam by " + event.getMember().getEffectiveName()).asDisabled()
                ).queue();

                try {
                    user.openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessage("Your report has been rejected! Kindly avoid making false report or severe punishments ***WILL*** happen!").queue();
                    });
                } catch (Exception e) {
                    event.getChannel().sendMessage("Failed to notify the reporter. Due to the reporter not allowing DM's").queue();
                }
                break;
            case "balance":
                Integer balance = BalanceCommand.dataInTheSky.get(event.getUser().getIdLong());
                event.reply("Your balance is **" + balance + "**").setEphemeral(true).queue();
                BalanceCommand.dataInTheSky.remove(event.getUser().getIdLong());
                event.getMessage().editMessageEmbeds(event.getMessage().getEmbeds().get(0)).setActionRow(event.getButton().asDisabled()).queue();
                break;
            case "yes":
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("<a:thanks:863989523461177394> Thank you for accepting the rules and data that will be stored.").queue();
                event.getChannel().sendMessage("<a:question:863989523368247346> For your Ignite Coins balance, may we ask for your first and last, real name? For example, **Nathan Tan** or **John Sy**").queue();
                Data.progress.put(event.getUser(), 1);
                break;
            case "hit":
                AtomicBoolean me = new AtomicBoolean(true);

                BlackjackGame bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    bjg.hit();
                    event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                        EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                        if (bjg.hasEnded()) {
                            int d = bjg.getWonCreds();
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            RPGUser.addShekels(event.getUser().getIdLong(), d);
                            eb.addField("Shekels", "You now have " + d + " more shekels", false);
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            me.set(false);
                        }
                        if (me.get()) {
                            m.editMessageEmbeds(eb.build()).queue();
                        } else {
                            m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                        }
                    });
                }

                event.deferEdit().queue();
                break;
            case "stand":
                me = new AtomicBoolean(true);

                bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    bjg.stand();
                    event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                        EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                        if (bjg.hasEnded()) {
                            int d = bjg.getWonCreds();
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            RPGUser.addShekels(event.getUser().getIdLong(), d);
                            eb.addField("Shekels", "You now have " + d + " more shekels", false);
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            me.set(false);
                        }
                        if (me.get()) {
                            m.editMessageEmbeds(eb.build()).queue();
                        } else {
                            m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                        }
                    });
                }

                event.deferEdit().queue();
                break;
            case "double":
                me = new AtomicBoolean(true);
                bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    if (bjg.canDouble()) {
                        if (DatabaseManager.INSTANCE.getCredits(event.getUser().getIdLong()) - 2 * bjg.getBet() >= 0) {
                            bjg.doubleDown();
                            event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                                EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                                if (bjg.hasEnded()) {
                                    int d = bjg.getWonCreds();
                                    GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                                    RPGUser.addShekels(event.getUser().getIdLong(), d);
                                    eb.addField("Shekels", "You now have " + d + " more shekels", false);
                                    me.set(false);
                                }
                                if (me.get()) {
                                    m.editMessageEmbeds(eb.build()).queue();
                                } else {
                                    m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                                }
                            });
                        } else {
                            event.getChannel().sendMessage("You have not enough shekels").queue();
                        }
                    } else {
                        event.getChannel().sendMessage("You can't do that").queue();
                    }
                }
                event.deferEdit().queue();
                break;
            case "split":
                me = new AtomicBoolean(true);
                bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    if (bjg.canSplit()) {
                        bjg.split();
                        event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                            EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                            if (bjg.hasEnded()) {
                                int d = bjg.getWonCreds();
                                GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                                RPGUser.addShekels(event.getUser().getIdLong(), d);
                                eb.addField("Shekels", "You now have " + d + " more shekels", false);
                                GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                                me.set(false);
                            }

                            long playerId = event.getUser().getIdLong();

                            if (me.get()) {
                                m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(
                                        Button.of(ButtonStyle.PRIMARY, playerId + ":hit", "Hit"),
                                        Button.of(ButtonStyle.PRIMARY, playerId + ":stand", "Stand"),
                                        Button.of(ButtonStyle.PRIMARY, playerId + ":double", "Double")
                                ), ActionRow.of(
                                        Button.of(ButtonStyle.SUCCESS, playerId + ":split", "Used Split").asDisabled(),
                                        Button.of(ButtonStyle.DANGER, playerId + ":endbj", "Surrender")
                                )).queue();
                            } else {
                                m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                            }
                        });
                    }
                } else {
                    event.getChannel().sendMessage("You have not enough shekels").queue();
                }

                event.deferEdit().queue();
                break;
            case "endbj":
                me = new AtomicBoolean(true);

                bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    bjg.stand();
                    if (!bjg.hasEnded()) {
                        bjg.stand();
                    }
                    event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                        EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                        if (bjg.hasEnded()) {
                            int d = bjg.getWonCreds();
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            RPGUser.addShekels(event.getUser().getIdLong(), d);
                            eb.addField("Shekels", "You now have " + d + " more shekels", false);
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            me.set(false);
                        }
                        if (me.get()) {
                            m.editMessageEmbeds(eb.build()).queue();
                        } else {
                            m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                        }
                    });
                }

                event.deferEdit().queue();
                break;
        }
        }

    public EmbedBuilder helpCrap (int number, ButtonClickEvent ctx) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        switch (number) {
            case 1:
                embedBuilder.setTitle("Information Commands");
                embedBuilder.setColor(Color.YELLOW);
                embedBuilder.addField("1.) Profile Command","`" + prefix + " profile`", false);
                embedBuilder.addField("2.) Server Information Command","`" + prefix + " serverinfo`", false);

                embedBuilder.setFooter("\nType " + prefix + " help [command name] to see what they do");
                break;
            case 2:
                embedBuilder.setTitle("About the Bot Commands");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Uptime Command", "`" + prefix + " uptime`", false);
                embedBuilder.addField("2.) Ping Command", "`" + prefix + " ping`", false);
                embedBuilder.addField("3.) About Command", "`" + prefix + " about`", false);


                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 3:
                embedBuilder.setTitle("Moderation Commands");
                embedBuilder.setColor(Color.red);
                embedBuilder.addField("1.) Set Prefix Command", "`" + prefix + " setprefix`", false);
                embedBuilder.addField("2.) Lockdown Command", "`" + prefix + " lockdown`", false);
                embedBuilder.addField("3.) Un-lockdown Command", "`" + prefix + " unlockdown`", false);
                embedBuilder.addField("4.) Update Ignite Coins Balance Command", "`" + prefix + " updatecoins`", false);
                embedBuilder.addField("5.) Command Count Command", "`" + prefix + " cmdcount`", false);
                embedBuilder.addField("6.) Add Credits Command", "`" + prefix + " addcredit`", false);
                embedBuilder.addField("7.) Add Shekels Command", "`" + prefix + " addshekels`", false);
                embedBuilder.addField("8.) Add Item Command", "`" + prefix + " addItems`", false);

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 4:
                embedBuilder.setTitle("User Commands");
                embedBuilder.setColor(Color.CYAN);
                embedBuilder.addField("1.) Marriage command", "`" + prefix + " marry`", false);
                embedBuilder.addField("2.) Adopt Command", "`" + prefix + " adopt`", false);
                embedBuilder.addField("3.) Divorce Command", "`" + prefix + " divorce`", false);
                embedBuilder.addField("4.) Leave Son Command", "`" + prefix + " dump`", false);
                embedBuilder.addField("5.) Family tree Command", "`" + prefix + " family`", false);
                embedBuilder.addField("6.) Share code Command (Programming)", "`" + prefix + " sharecode`", false);
                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 5:
                embedBuilder.setTitle("Games");
                embedBuilder.setColor(Color.ORANGE);
                embedBuilder.setDescription(Emojis.UNO + " **Uno** - Players take turns matching a card in their hand with the current card shown on top of the deck either by color or number. Special action cards deliver game-changing moments as they help you defeat your opponents.\n\n" +
                        Emojis.BLACKJACK + " **Blackjack** - Blackjack is a card game. The object of blackjack is to be dealt cards with a value of up to but not over 21 and to beat the dealer's hand. ... You place bets with the dealer on the likelihood that your hand will come equal or closer to 21 than will the dealer's.\n\n" +
                        Emojis.NUMBER + " **Guess the Number** - Your goal is to get the same number that the bot selected from 1-100 the bot will inform you if the number is *higher* or *lower*\n\n" +
                        Emojis.MARK_QUESTION + " **Trivia** - Users will be given a random question and they are to answer it.\n\n" +
                        Emojis.BABY_YODA + " **Hangman** - Hangman is a paper and pencil guessing game for two or more players. One player thinks of a word, phrase or sentence and the other(s) tries to guess it by suggesting letters within a certain number of guesses.");

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 6:
                embedBuilder.setTitle("RPG Commands *BETA*");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Journey Command", "`" + prefix + " journey`", false);
                embedBuilder.addField("2.) Buy Command", "`" + prefix + " buy`", false);
                embedBuilder.addField("3.) Sell Command", "`" + prefix + " sell`", false);
                embedBuilder.addField("4.) Shop Command", "`" + prefix + " shop`", false);
                embedBuilder.addField("5.) Inventory Command", "`" + prefix + " inv` or `" + prefix + " inventory`", false);
                embedBuilder.addField("6.) Fish Command", "`" + prefix + " fish`", false);
                embedBuilder.addField("7.) Hunt Command", "`" + prefix + " hunt`", false);
                embedBuilder.addField("8.) View Health Command", "`" + prefix + " health`", false);
                embedBuilder.addField("9.) Cook Command", "`" + prefix + " cook`", false);
                embedBuilder.addField("10.) Open Loot Boxes Command", "`" + prefix + " open`", false);

                embedBuilder.setFooter("Some commands here may or may not work due to it being a BETA command");
                break;
            case 7:
                embedBuilder.setTitle("Currency Commands");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Register Command", "`" + prefix + " register`", false);
                embedBuilder.addField("2.) Share Credits command", "`" + prefix + " share`", false);
                embedBuilder.addField("3.) Leaderboard Credits command", "`" + prefix + " lb`", false);
                embedBuilder.addField("4.) Work command", "`" + prefix + " work`", false);
                embedBuilder.addField("5.) Beg command", "`" + prefix + " beg`", false);
                embedBuilder.addField("6.) Remind Work command", "`" + prefix + " remindbeg`", false);
                embedBuilder.addField("7.) Remind beg command", "`" + prefix + " remindwork`", false);
                embedBuilder.addField("8.) Deposit Command", "`" + prefix + " deposit`", false);
                embedBuilder.addField("9.) Withdraw Command", "`" + prefix + " withdraw`", false);

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
        }
        return embedBuilder;
    }
}
