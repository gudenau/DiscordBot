module net.gudenau.discord.DiscordBot.TestPlugin {
    requires net.gudenau.discord.DiscordBot.api;
    
    provides net.gudenau.discord.bot.IPlugin with net.gudenau.discord.test.TestPlugin;
}