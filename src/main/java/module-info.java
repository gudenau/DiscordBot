module net.gudenau.discord.DiscordBot.main {
    requires JDA;
    requires java.desktop;
    requires gson;
    requires java.sql; // GSON bug, still
    requires org.objectweb.asm;
    requires org.objectweb.asm.tree;
    requires net.gudenau.discord.DiscordBot.api;
    
    uses net.gudenau.discord.bot.IPlugin;
    
    // JSON access stuff
    exports net.gudenau.discord.bot.json to gson;
    
    // JDA access stuff
    exports net.gudenau.discord.bot.implementation to JDA;
}