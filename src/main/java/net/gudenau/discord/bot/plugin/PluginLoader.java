package net.gudenau.discord.bot.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.jar.JarFile;
import net.gudenau.discord.bot.IPlugin;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class PluginLoader{
    public static ModuleLayer loadPlugins(){
        var pluginDir = new File("./plugins");
        if(!pluginDir.exists()){
            pluginDir.mkdirs();
        }
        var candidateJars = pluginDir.listFiles((file, name)->name.toLowerCase().endsWith(".jar"));
        if(candidateJars == null || candidateJars.length == 0){
            return null;
        }
        
        List<Path> pluginJars = new ArrayList<>();
        List<String> pluginNames = new ArrayList<>();
    
        for(var pluginFile : candidateJars){
            try(var jarFile = new JarFile(pluginFile)){
                var entry = jarFile.getJarEntry("module-info.class");
                if(entry == null){
                    jarFile.close();
                    continue;
                }
                try(var in = jarFile.getInputStream(entry)){
                    var classNode = new ClassNode();
                    var classReader = new ClassReader(in);
                    classReader.accept(
                        classNode,
                        ClassReader.SKIP_CODE |
                            ClassReader.SKIP_DEBUG |
                            ClassReader.SKIP_FRAMES
                    );
                    pluginNames.add(classNode.module.name);
                }
                pluginJars.add(pluginFile.toPath());
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        
        if(pluginJars.isEmpty()){
            return null;
        }
    
        var finder = ModuleFinder.of(pluginJars.toArray(new Path[0]));
        var parent = PluginLoader.class.getModule().getLayer();
        var configuration = parent.configuration().resolve(
            finder,
            ModuleFinder.of(),
            pluginNames
        );
        var classLoader = PluginLoader.class.getClassLoader();
        return parent.defineModulesWithManyLoaders(configuration,classLoader);
    }
}
