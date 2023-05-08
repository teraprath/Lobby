package net.spigotcloud.lobby.module;

public class ModuleDescriptionFile {

    private final String name;
    private final String author;
    private final String version;


    public ModuleDescriptionFile(String name, String author, String version) {
        this.name = name;
        this.author = author;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

}
