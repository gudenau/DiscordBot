module net.gudenau.discord.DiscordBot.api {
    requires transitive JDA;
    requires transitive java.desktop;
    requires gson;
    
    exports net.gudenau.discord.bot;
    exports net.gudenau.discord.bot.command;
    exports net.gudenau.discord.bot.result;
    exports net.gudenau.discord.bot.util;
    
    uses net.gudenau.discord.bot.IPlugin;
}