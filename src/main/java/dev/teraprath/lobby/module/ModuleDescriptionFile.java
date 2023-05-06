package dev.teraprath.lobby.module;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginAwareness;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.InputStream;
import java.util.Map;

public class ModuleDescriptionFile {

    private static final ThreadLocal<Yaml> YAML = new ThreadLocal<Yaml>() {
        protected Yaml initialValue() {
            return new Yaml(new SafeConstructor() {
                {
                    this.yamlConstructors.put(null, new AbstractConstruct() {
                        public Object construct(final Node node) {
                            return !node.getTag().startsWith("!@") ? SafeConstructor.undefinedConstructor.construct(node) : new PluginAwareness() {
                                public String toString() {
                                    return node.toString();
                                }
                            };
                        }
                    });
                    PluginAwareness.Flags[] var2;
                    int var3 = (var2 = PluginAwareness.Flags.values()).length;

                    for (int var4 = 0; var4 < var3; ++var4) {
                        final PluginAwareness.Flags flag = var2[var4];
                        this.yamlConstructors.put(new Tag("!@" + flag.name()), new AbstractConstruct() {
                            public PluginAwareness.Flags construct(Node node) {
                                return flag;
                            }
                        });
                    }
                }
            });
        }
    };

    private String main;
    private String author;
    private String name;

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getMain() {
        return main;
    }

    public ModuleDescriptionFile(InputStream stream) throws InvalidDescriptionException {
        this.loadMap(this.asMap(YAML.get().load(stream)));
    }

    private void loadMap(Map<?, ?> map) throws InvalidDescriptionException {

        try {
            this.main = map.get("main").toString();
        } catch (NullPointerException e) {
            throw new InvalidDescriptionException("main is not defined");
        } catch (ClassCastException var18) {
            throw new InvalidDescriptionException(var18, "main is of wrong type");
        }

        try {
            this.author = map.get("author").toString();
        } catch (NullPointerException e) {
            throw new InvalidDescriptionException("author is not defined");
        } catch (ClassCastException var18) {
            throw new InvalidDescriptionException(var18, "author is of wrong type");
        }

        try {
            this.name = map.get("name").toString();
        } catch (NullPointerException e) {
            throw new InvalidDescriptionException("name is not defined");
        } catch (ClassCastException var18) {
            throw new InvalidDescriptionException(var18, "name is of wrong type");
        }
    }

    private Map<?, ?> asMap(Object object) throws InvalidDescriptionException {
        if (object instanceof Map) {
            return (Map) object;
        } else {
            throw new InvalidDescriptionException(object + " is not properly structured.");
        }
    }
}

