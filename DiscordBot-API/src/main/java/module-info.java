module net.gudenau.discord.DiscordBot.api {
    requires transitive JDA;
    requires transitive java.desktop;
    requires transitive gson;
    
    exports net.gudenau.discord.bot;
    exports net.gudenau.discord.bot.command;
    exports net.gudenau.discord.bot.result;
    exports net.gudenau.discord.bot.util;
    
    opens net.gudenau.discord.bot.result to JDA;
    
    uses net.gudenau.discord.bot.IPlugin;
}