package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Bot;
import com.general_hello.commands.Config;
import com.general_hello.commands.RPG.Objects.RPGEmojis;
import com.general_hello.commands.RPG.RpgUser.RPGDataUtils;
import com.general_hello.commands.RPG.RpgUser.RPGUser;
import com.general_hello.commands.commands.Emoji.Emojis;
import com.general_hello.commands.commands.GroupOfGames.Games.TriviaCommand;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.Marriage.MarriageData;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.RankingSystem.LevelCalculator;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.UserPhoneUser;
import com.general_hello.commands.commands.Utils.UtilNum;
import com.general_hello.commands.commands.Work.WorkCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class OnSelectionMenu extends ListenerAdapter {
    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        EmbedBuilder embedBuilder;

        SelectionMenu menu = SelectionMenu.create("menu:class")
                .setPlaceholder("Choose the game you want to find help on") // shows the placeholder indicating what this menu is for
                .setRequiredRange(1, 1) // only one can be selected
                .addOption("Uno", "uno")
                .addOption("Blackjack", "bj")
                .addOption("Guess the number", "gn")
                .addOption("Trivia", "trivia")
                .addOption("Chess", "chess")
                .build();
        int x = 0;
        while (x < event.getSelectedOptions().size()) {
            switch (event.getSelectedOptions().get(x).getValue()) {
                case "reject" -> {
                    event.getUser().openPrivateChannel().complete().sendMessage("Sorry, you are too young to use this bot! (You shouldn't be on Discord!)").queue();
                    event.getMessage().delete().queue();
                    return;
                }
                case "noice", "oh", "old" -> {
                    embedBuilder = new EmbedBuilder().setTitle("Rules").setColor(InfoUserCommand.randomColor());
                    String arrow = "<a:arrow_1:862525611465113640>";
                    String message = arrow + " THIS IS A CHRISTIAN COMMUNITY SERVER. That means, we value the things Christ teaches us! " + Emojis.USER + " Let us try our best to exemplify Christlikness in all that we do here! " + Emojis.CHECK + "\n" +
                            "\n" +
                            arrow + " THIS GROUP IS FOR HIGH SCHOOL STUDENTS ONLY. High schoolers and Ignite friends (like your Ahyas and Achis) are the only ones allowed to join this server. This is to ensure your safety and security! " + Emojis.MOD + "\n" +
                            "\n" +
                            arrow + " INVITE HIGH SCHOOLERS TO JOIN OUR PROGRAMS! COIL was made to provide an avenue for high school students to connect in a safe, Christian community! Let's help our community grow by inviting your friends to hangout with us! \n" +
                            "\n" +
                            arrow + " BE COURTEOUS IN YOUR SPEECH. Out of the overflow of the heart, the mouth speaks! Let's avoid saying words that hurt others and cause people to stumble! Instead, let us encourage and uplift one another!\n" +
                            "\n" +
                            arrow + " SHOW LOVE TO EVERYONE. In COIL, we do not tolerate bullying of any sort! Kindly make an effort to love one another, even in situations wherein our uniqueness makes it harder for us to do so! Let's make COIL a safe space for everyone to hang!";
                    embedBuilder.setDescription(message);
                    embedBuilder.setFooter("Press the Accept button if you accept the rules stated above!");
                    event.getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).setActionRow(
                            Button.primary("0000:accept", "Accept").withEmoji(Emoji.fromEmote("verify", 863204252188672000L, true))
                    ).queue();
                    event.getMessage().delete().queue();
                    return;
                }
                case "bj" -> {
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(1, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                }
                case "gn" -> {
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(2, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                }
                case "trivia" -> {
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(4, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                }
                case "hangman" -> {
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(5, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                }
                case "uno" -> {
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessageEmbeds(helpCrap(6, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                    event.deferEdit().queue();
                }
                case "credit" -> {
                    try {
                        HashMap<User, UserPhoneUser> userUserPhoneUserHashMap = Data.userUserPhoneUserHashMap;
                        ArrayList<User> users = Data.users;
                        ArrayList<UserPhoneUser> userPhoneUsers = new ArrayList<>();
                        int z = 0;
                        while (z < users.size()) {
                            User user = users.get(z);
                            if (userUserPhoneUserHashMap.containsKey(user)) {
                                userPhoneUsers.add(userUserPhoneUserHashMap.get(user));
                            }
                            z++;
                        }

                        List<UserPhoneUser> collectedUsers = userPhoneUsers.stream().sorted(UserPhoneUser::compareTo).collect(Collectors.toList());

                        List<User> toFilter = new ArrayList<>();
                        DecimalFormat formatter = new DecimalFormat("#,###.00");

                        x = 0;
                        while (x < collectedUsers.size()) {
                            toFilter.add(collectedUsers.get(x).getDiscordUser());
                            x++;
                        }

                        Set<User> set = new LinkedHashSet<>(toFilter);

                        toFilter.clear();
                        toFilter.addAll(set);

                        embedBuilder = new EmbedBuilder();
                        embedBuilder.setTitle("Leaderboard of Credits").setFooter("Who is the richest of the richest?").setTimestamp(OffsetDateTime.now());
                        StringBuilder stringBuilder = new StringBuilder();

                        int y = 0;
                        x = 0;
                        boolean userThere = false;
                        while (y < userUserPhoneUserHashMap.size()) {
                            UserPhoneUser credits = userUserPhoneUserHashMap.get(toFilter.get(y));
                            if (!credits.getDiscordUser().isBot()) {
                                int rank = x + 1;
                                String rankShow;

                                if (rank == 1) {
                                    rankShow = "🥇";
                                } else if (rank == 2) {
                                    rankShow = "🥈";
                                } else if (rank == 3) {
                                    rankShow = "🥉";
                                } else {
                                    rankShow = "🔹";
                                }
                                Integer credits1 = credits.getCredits();
                                stringBuilder.append(rankShow).append(" <:credit:905976767821525042> **").append(formatter.format(credits1)).append("** - ").append(credits.getDiscordUser().getAsTag()).append("\n");

                                if (credits.getDiscordUser().equals(event.getUser())) {
                                    userThere = true;
                                }
                                if (y == 15) {
                                    break;
                                }
                                x++;
                            }
                            y++;
                        }

                        x = 15;
                        y = 15;

                        while (!userThere) {
                            UserPhoneUser credit = userUserPhoneUserHashMap.get(toFilter.get(x));
                            if (!credit.getDiscordUser().isBot()) {
                                y++;
                            }
                            if (credit.getDiscordUser().equals(event.getUser())) {
                                int credits = credit.getCredits();
                                stringBuilder.append("**😎 ").append(y).append(" <:credit:905976767821525042> **").append(formatter.format(credits)).append("** - ").append(credit.getDiscordUser().getAsTag()).append("**");
                                userThere = true;
                            }
                            x++;
                        }


                        embedBuilder.setDescription(stringBuilder.toString());
                        embedBuilder.setColor(InfoUserCommand.randomColor());
                        embedBuilder.setThumbnail("https://images-ext-1.discordapp.net/external/CldQTLK4UezxcAi3qvvrGrFCFa-1aFY_Miz5czSDPdY/https/cdn.discordapp.com/emojis/716848179022397462.gif");
                        menu = SelectionMenu.create("menu:leaderboard")
                                .setPlaceholder("Leaderboard types") // shows the placeholder indicating what this menu is for
                                .setRequiredRange(1, 1) // only one can be selected
                                .addOption("Credits", "credit", "Old Currency", Emoji.fromMarkdown(RPGEmojis.credits))
                                .addOption("Shekels", "shekel", "RPG Currency", Emoji.fromMarkdown(RPGEmojis.shekels))
                                .addOption("Marriage", "marry", "Marriage XP", Emoji.fromMarkdown("<:sparkle_blue:917915035236458608>"))
                                .build();
                        event.getMessage().editMessageEmbeds(embedBuilder.build()).setActionRow(menu).queue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    event.deferEdit().queue();
                }
                case "shekel" -> {
                    try {
                        DecimalFormat formatter = RPGDataUtils.formatter;
                        List<Member> members = event.getGuild().getMembers();
                        x = 0;
                        ArrayList<Integer> money = new ArrayList<>();
                        HashMap<Integer, Member> hashMap = new HashMap<>();
                        while (x < members.size()) {
                            Member member = members.get(x);
                            int shekels = RPGUser.getShekels(member.getIdLong());
                            if (shekels != -1) {
                                money.add(shekels);
                                hashMap.put(shekels, member);
                            }
                            x++;
                        }
                        money.sort(Collections.reverseOrder());
                        embedBuilder = new EmbedBuilder();
                        embedBuilder.setTitle("Leaderboard of Shekels").setFooter("Who is the richest of the richest?").setTimestamp(OffsetDateTime.now());
                        StringBuilder stringBuilder = new StringBuilder();

                        int y = 0;
                        x = 0;
                        boolean userThere = false;
                        while (y < hashMap.size()) {
                            Member member = hashMap.get(money.get(y));
                            Integer credits = money.get(y);
                            if (!member.getUser().isBot()) {
                                int rank = x + 1;
                                String rankShow;

                                if (rank == 1) {
                                    rankShow = "🥇";
                                } else if (rank == 2) {
                                    rankShow = "🥈";
                                } else if (rank == 3) {
                                    rankShow = "🥉";
                                } else {
                                    rankShow = "🔹";
                                }
                                stringBuilder.append(rankShow).append(" " + Emojis.shekels + " **").append(formatter.format(credits)).append("** - ").append(member.getUser().getAsTag()).append("\n");
                                if (member.getUser().equals(event.getUser())) {
                                    userThere = true;
                                }
                                if (y == 15) {
                                    break;
                                }
                                x++;
                            }
                            y++;
                        }

                        x = 15;
                        y = 15;

                        try {
                            while (!userThere) {
                                Member member = hashMap.get(money.get(x));
                                Integer credits = money.get(x);
                                if (!member.getUser().isBot()) {
                                    y++;
                                }
                                if (member.getUser().equals(event.getUser())) {
                                    stringBuilder.append("😢 **").append(y).append("** " + RPGEmojis.shekels + " **").append(formatter.format(credits)).append("** - **").append(member.getUser().getAsTag()).append("**");
                                    userThere = true;
                                }
                                x++;
                            }
                        } catch (Exception ignored) {
                        }


                        embedBuilder.setDescription(stringBuilder.toString());
                        embedBuilder.setColor(InfoUserCommand.randomColor());
                        embedBuilder.setThumbnail("https://cdn.discordapp.com/emojis/718136428219072662.gif");
                        menu = SelectionMenu.create("menu:leaderboard")
                                .setPlaceholder("Leaderboard types") // shows the placeholder indicating what this menu is for
                                .setRequiredRange(1, 1) // only one can be selected
                                .addOption("Credits", "credit", "Old Currency", Emoji.fromMarkdown(RPGEmojis.credits))
                                .addOption("Shekels", "shekel", "RPG Currency", Emoji.fromMarkdown(RPGEmojis.shekels))
                                .addOption("Marriage", "marry", "Marriage XP", Emoji.fromMarkdown("<:sparkle_blue:917915035236458608>"))
                                .build();
                        event.getMessage().editMessageEmbeds(embedBuilder.build()).setActionRow(menu).queue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    event.deferEdit().queue();
                }
                case "marry" -> {
                    try {
                        DecimalFormat formatter = RPGDataUtils.formatter;
                        List<Member> members = event.getGuild().getMembers();
                        x = 0;
                        ArrayList<Integer> xp = new ArrayList<>();
                        HashMap<Integer, Member> hashMap = new HashMap<>();
                        while (x < members.size()) {
                            Member member = members.get(x);
                            int marriageXP = (int) MarriageData.getXP(member.getIdLong());
                            if (marriageXP != -1 && !xp.contains(marriageXP)) {
                                xp.add(marriageXP);
                                hashMap.put(marriageXP, member);
                            }
                            x++;
                        }
                        xp.sort(Collections.reverseOrder());
                        embedBuilder = new EmbedBuilder();
                        embedBuilder.setTitle("Leaderboard of Marriage").setFooter("Who is the most faithful of the faithful?").setTimestamp(OffsetDateTime.now());
                        StringBuilder stringBuilder = new StringBuilder();

                        int y = 0;
                        x = 0;
                        boolean userThere = false;
                        while (y < hashMap.size()) {
                            Member member = hashMap.get(xp.get(y));
                            Integer xps = xp.get(y);
                            if (!member.getUser().isBot()) {
                                int rank = x + 1;
                                String rankShow;

                                if (rank == 1) {
                                    rankShow = "🥇";
                                } else if (rank == 2) {
                                    rankShow = "🥈";
                                } else if (rank == 3) {
                                    rankShow = "🥉";
                                } else {
                                    rankShow = "🔹";
                                }
                                stringBuilder.append(rankShow).append(" **").append(formatter.format(xps)).append(" XP** *(Level ").append(LevelCalculator.calculateLevel(xps)).append(")*  - **").append(RPGDataUtils.getNameFromUser(member.getUser())).append("** and **").append(RPGDataUtils.getNameFromUser(Bot.jda.getUserById(MarriageData.getWife(member.getIdLong())))).append("**").append("\n");
                                if (member.getUser().equals(event.getUser())) {
                                    userThere = true;
                                }
                                if (y == 15) {
                                    break;
                                }
                                x++;
                            }
                            y++;
                        }

                        try {
                            x = 15;
                            y = 15;

                            while (!userThere) {
                                Member member = hashMap.get(xp.get(x));
                                Integer credits = xp.get(x);
                                if (!member.getUser().isBot()) {
                                    y++;
                                }
                                if (member.getUser().equals(event.getUser())) {
                                    stringBuilder.append("😢 **").append(y).append("** " + RPGEmojis.shekels + " **").append(formatter.format(credits)).append("** - **").append(member.getUser().getAsTag()).append("**");
                                    userThere = true;
                                }
                                x++;
                            }
                        } catch (Exception e) {
                        }

                        embedBuilder.setDescription(stringBuilder.toString());
                        embedBuilder.setColor(InfoUserCommand.randomColor());
                        embedBuilder.setThumbnail("https://images-ext-1.discordapp.net/external/CldQTLK4UezxcAi3qvvrGrFCFa-1aFY_Miz5czSDPdY/https/cdn.discordapp.com/emojis/716848179022397462.gif");
                        menu = SelectionMenu.create("menu:leaderboard")
                                .setPlaceholder("Leaderboard types") // shows the placeholder indicating what this menu is for
                                .setRequiredRange(1, 1) // only one can be selected
                                .addOption("Credits", "credit", "Old Currency", Emoji.fromMarkdown(RPGEmojis.credits))
                                .addOption("Shekels", "shekel", "RPG Currency", Emoji.fromMarkdown(RPGEmojis.shekels))
                                .addOption("Marriage", "marry", "Marriage XP", Emoji.fromMarkdown("<:sparkle_blue:917915035236458608>"))
                                .build();
                        event.getMessage().editMessageEmbeds(embedBuilder.build()).setActionRow(menu).queue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    event.deferEdit().queue();
                }
            }

            x++;
        }


        if (TriviaCommand.storeAnswer.containsKey(event.getUser())) {
            String answer = TriviaCommand.storeAnswer.get(event.getUser());
            String question = TriviaCommand.storeQuestion.get(event.getUser());
            String difficulty = TriviaCommand.storeDifficulty.get(event.getUser());
            int reward = 500;

            int multiplier = difficulty.equals("medium") ? 3 : 1;
            multiplier = difficulty.equals("hard") ? 5 : multiplier;

            reward = reward * multiplier;
            System.out.println(WorkCommand.job.containsKey(event.getUser()));
            if (event.getSelectedOptions().get(0).getValue().equals(answer)) {
                if (WorkCommand.job.containsKey(event.getUser())) {
                    UserPhoneUser bankUser = Data.userUserPhoneUserHashMap.get(event.getJDA().getSelfUser());
                    int bankCredits = bankUser.getCredits();

                    int minRobOrFine = 1_000;
                    int maxRobOrFine = 10_000;

                    if (maxRobOrFine > bankCredits) {
                        maxRobOrFine = bankCredits;
                    }

                    int randomNum = UtilNum.randomNum(minRobOrFine, maxRobOrFine);

                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    RPGUser.addShekels(event.getUser().getIdLong(), randomNum);
                    EmbedBuilder e = new EmbedBuilder();
                    e.setTitle("Great Work!");
                    e.setColor(Color.green);
                    e.setDescription("You were given " + RPGEmojis.shekels + " `" + formatter.format(randomNum) + "` for an hour of work.");
                    e.setFooter("Working as a teacher");
                    event.getHook().deleteOriginal().queue();
                    event.deferEdit().queue();
                    WorkCommand.job.remove(event.getUser());
                    event.getChannel().sendMessageEmbeds(e.build()).setActionRow(event.getSelectionMenu().asDisabled()).queue();
                } else {
                    event.getChannel().sendMessage("Correct answer!!!!\n" +
                            "You got " + RPGEmojis.shekels + " " + reward + " for getting the correct answer!\n" +
                            "Question: `" + question + "`").queue();
                    RPGUser.addShekels(event.getUser().getIdLong(), reward);
                    event.deferEdit().queue();
                    event.getMessage().delete().queue();
                }
                TriviaCommand.storeAnswer.remove(event.getUser());
            } else {
                if (WorkCommand.job.containsKey(event.getUser())) {

                    UserPhoneUser bankUser = Data.userUserPhoneUserHashMap.get(event.getJDA().getSelfUser());
                    int bankCredits = bankUser.getCredits();

                    int minRobOrFine = 0;
                    int maxRobOrFine = 2_000;

                    if (maxRobOrFine > bankCredits) {
                        maxRobOrFine = bankCredits;
                    }

                    int randomNum = UtilNum.randomNum(minRobOrFine, maxRobOrFine);

                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    RPGUser.addShekels(event.getUser().getIdLong(), randomNum);

                    EmbedBuilder e = new EmbedBuilder();
                    e.setTitle("TERRIBLE Work!");
                    e.setColor(Color.red);
                    e.setDescription("You lost the mini-game because the answer you chose wasn't correct.\n" +
                            "You were given " + RPGEmojis.shekels + " `" + formatter.format(randomNum) + "` for a sub-par hour of work.");
                    e.setFooter("Working as a teacher");
                    event.getHook().deleteOriginal().queue();
                    event.deferEdit().queue();
                    WorkCommand.job.remove(event.getUser());
                    event.getChannel().sendMessageEmbeds(e.build()).setActionRow(event.getSelectionMenu().asDisabled()).queue();
                } else {
                    EmbedBuilder e = new EmbedBuilder();
                    e.setTitle("Incorrect answer");
                    e.setFooter("A correct answer gives you " + RPGEmojis.shekels + " " + reward);
                    e.addField("Question: `" + question + "`\n" + "Difficulty: **" + difficulty +
                            "**\nThe correct answer is " + TriviaCommand.storeAnswer.get(event.getUser()), "Better luck next time", false).setColor(Color.RED);
                    event.getChannel().sendMessageEmbeds(e.build()).queue();
                    event.getMessage().delete().queue();
                    event.deferEdit().queue();
                }
                TriviaCommand.storeAnswer.remove(event.getUser());
                TriviaCommand.storeQuestion.remove(event.getUser());
                TriviaCommand.storeDifficulty.remove(event.getUser());
                try {
                    WorkCommand.job.remove(event.getUser());
                } catch(Exception ignored) {}
            }
        }
    }

    public EmbedBuilder helpCrap (int number, SelectionMenuEvent ctx) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        switch (number) {
            case 1:
                embedBuilder.setTitle("Blackjack Commands");
                embedBuilder.setColor(Color.YELLOW);
                embedBuilder.addField("1.) Start a blackjack game command","`" + prefix + " blackjack`", false);
                embedBuilder.addField("2.) Hit card command","`" + prefix + " hit`", false);
                embedBuilder.addField("3.) Stand command","`" + prefix + " stand`", false);
                embedBuilder.addField("4.) Double command","`" + prefix + " double`", false);
                embedBuilder.addField("5.) Split card command","`" + prefix + " split`", false);

                embedBuilder.setFooter("\nType " + prefix + " help [command name] to see what they do");
                break;
            case 2:
                embedBuilder.setTitle("Guess the number Commands");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Start the Guess the number game Command", "`" + prefix + " gn start`", false);
                embedBuilder.addField("2.) Guess a number Command", "`" + prefix + " gn [number]`", false);
                embedBuilder.addField("3.) End game Command", "`" + prefix + " gn end`", false);

                embedBuilder.setFooter("\nType " + prefix + " help [command name] to see what they do");
                break;
            case 4:
                embedBuilder.setTitle("Trivia Commands");
                embedBuilder.setColor(Color.CYAN);
                embedBuilder.addField("1.) Start trivia Command", "`" + prefix + " trivia`", false);

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 5:
                embedBuilder.setTitle("Hangman Commands");
                embedBuilder.setColor(Color.CYAN);
                embedBuilder.addField("1.) Start a Hangman Game Command", "`" + prefix + " hangman-start`", false);
                embedBuilder.addField("2.) Reset hangman Command", "`" + prefix + " hangman-reset`", false);
                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 6:
                embedBuilder.setTitle("Uno Commands");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Start the UNO game Command", "`" + prefix + " startuno`", false);
                embedBuilder.addField("2.) Play card Command", "`" + prefix + " playcard [card name]`\n" +
                        "Example: `" + prefix + "` playcard red4", false);
                embedBuilder.addField("3.) Draw Card Command", "`" + prefix + " draw`", false);
                embedBuilder.addField("4.) Challenge a +4 card Command", "`" + prefix + " challenge`", false);

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
        }
        return embedBuilder;
    }
}
