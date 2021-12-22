//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package kurohack.manager;

import kurohack.util.element.*;
import kurohack.features.*;
import kurohack.features.setting.*;
import kurohack.*;
import kurohack.features.modules.render.*;
import java.util.stream.*;
import java.nio.file.attribute.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;
import java.io.*;
import kurohack.features.modules.*;

public class ConfigManager implements IUtil
{
    public ArrayList<Feature> features;
    public String config;
    public boolean loadingConfig;
    public boolean savingConfig;
    
    public ConfigManager() {
        this.features = new ArrayList<Feature>();
        this.config = "kurohack/config/";
    }
    
    public static void setValueFromJson(final Feature feature, final Setting setting, final JsonElement element) {
        final String type = setting.getType();
        switch (type) {
            case "Boolean": {
                setting.setValue((Object)element.getAsBoolean());
                break;
            }
            case "Double": {
                setting.setValue((Object)element.getAsDouble());
                break;
            }
            case "Float": {
                setting.setValue((Object)element.getAsFloat());
                break;
            }
            case "Integer": {
                setting.setValue((Object)element.getAsInt());
                break;
            }
            case "String": {
                final String str = element.getAsString();
                setting.setValue((Object)str.replace("_", " "));
                break;
            }
            case "Bind": {
                setting.setValue((Object)new Bind.BindConverter().doBackward(element));
                break;
            }
            case "Enum": {
                try {
                    final EnumConverter converter = new EnumConverter((Class)((Enum)setting.getValue()).getClass());
                    final Enum value = converter.doBackward(element);
                    setting.setValue((value == null) ? setting.getDefaultValue() : value);
                }
                catch (Exception ex) {}
                break;
            }
            default: {
                KuroHack.LOGGER.error("Unknown Setting type for: " + feature.getName() + " : " + setting.getName());
                break;
            }
        }
    }
    
    private static void loadFile(final JsonObject input, final Feature feature) {
        for (final Map.Entry entry : input.entrySet()) {
            final String settingName = entry.getKey();
            final JsonElement element = entry.getValue();
            if (feature instanceof FriendManager) {
                try {
                    KuroHack.friendManager.addFriend(new FriendManager.Friend(element.getAsString(), UUID.fromString(settingName)));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                boolean settingFound = false;
                for (final Setting setting : feature.getSettings()) {
                    if (!settingName.equals(setting.getName())) {
                        continue;
                    }
                    try {
                        setValueFromJson(feature, setting, element);
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    settingFound = true;
                }
                if (settingFound) {
                    continue;
                }
            }
            if (feature instanceof XRay) {
                feature.register(new Setting(settingName, (Object)true, v -> (boolean)((XRay)feature).showBlocks.getValue()));
            }
        }
    }
    
    public void loadConfig(final String name) {
        this.loadingConfig = true;
        final List<File> files = Arrays.stream((Object[])Objects.requireNonNull((T[])new File("kurohack").listFiles())).filter(File::isDirectory).collect((Collector<? super Object, ?, List<File>>)Collectors.toList());
        this.config = (files.contains(new File("kurohack/" + name + "/")) ? ("kurohack/" + name + "/") : "kurohack/config/");
        KuroHack.friendManager.onLoad();
        for (final Feature feature : this.features) {
            try {
                this.loadSettings(feature);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveCurrentConfig();
        this.loadingConfig = false;
    }
    
    public void saveConfig(final String name) {
        this.savingConfig = true;
        this.config = "kurohack/" + name + "/";
        final File path = new File(this.config);
        if (!path.exists()) {
            path.mkdir();
        }
        KuroHack.friendManager.saveFriends();
        for (final Feature feature : this.features) {
            try {
                this.saveSettings(feature);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveCurrentConfig();
        this.savingConfig = false;
    }
    
    public void saveCurrentConfig() {
        final File currentConfig = new File("kurohack/currentconfig.txt");
        try {
            if (currentConfig.exists()) {
                final FileWriter writer = new FileWriter(currentConfig);
                final String tempConfig = this.config.replaceAll("/", "");
                writer.write(tempConfig.replaceAll("kurohack", ""));
                writer.close();
            }
            else {
                currentConfig.createNewFile();
                final FileWriter writer = new FileWriter(currentConfig);
                final String tempConfig = this.config.replaceAll("/", "");
                writer.write(tempConfig.replaceAll("kurohack", ""));
                writer.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String loadCurrentConfig() {
        final File currentConfig = new File("kurohack/currentconfig.txt");
        String name = "config";
        try {
            if (currentConfig.exists()) {
                final Scanner reader = new Scanner(currentConfig);
                while (reader.hasNextLine()) {
                    name = reader.nextLine();
                }
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
    
    public void resetConfig(final boolean saveConfig, final String name) {
        for (final Feature feature : this.features) {
            feature.reset();
        }
        if (saveConfig) {
            this.saveConfig(name);
        }
    }
    
    public void saveSettings(final Feature feature) throws IOException {
        final JsonObject object = new JsonObject();
        final File directory = new File(this.config + this.getDirectory(feature));
        if (!directory.exists()) {
            directory.mkdir();
        }
        final String featureName;
        final Path outputFile;
        if (!Files.exists(outputFile = Paths.get(featureName = this.config + this.getDirectory(feature) + feature.getName() + ".json", new String[0]), new LinkOption[0])) {
            Files.createFile(outputFile, (FileAttribute<?>[])new FileAttribute[0]);
        }
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String json = gson.toJson((JsonElement)this.writeSettings(feature));
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(outputFile, new OpenOption[0])));
        writer.write(json);
        writer.close();
    }
    
    public void init() {
        this.features.addAll((Collection<? extends Feature>)KuroHack.moduleManager.modules);
        this.features.add(KuroHack.friendManager);
        final String name = this.loadCurrentConfig();
        this.loadConfig(name);
        KuroHack.LOGGER.info("Config loaded.");
    }
    
    private void loadSettings(final Feature feature) throws IOException {
        final String featureName = this.config + this.getDirectory(feature) + feature.getName() + ".json";
        final Path featurePath = Paths.get(featureName, new String[0]);
        if (!Files.exists(featurePath, new LinkOption[0])) {
            return;
        }
        this.loadPath(featurePath, feature);
    }
    
    private void loadPath(final Path path, final Feature feature) throws IOException {
        final InputStream stream = Files.newInputStream(path, new OpenOption[0]);
        try {
            loadFile(new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject(), feature);
        }
        catch (IllegalStateException e) {
            KuroHack.LOGGER.error("Bad Config File for: " + feature.getName() + ". Resetting...");
            loadFile(new JsonObject(), feature);
        }
        stream.close();
    }
    
    public JsonObject writeSettings(final Feature feature) {
        final JsonObject object = new JsonObject();
        final JsonParser jp = new JsonParser();
        for (final Setting setting : feature.getSettings()) {
            if (setting.isEnumSetting()) {
                final EnumConverter converter = new EnumConverter((Class)((Enum)setting.getValue()).getClass());
                object.add(setting.getName(), converter.doForward((Enum)setting.getValue()));
            }
            else {
                if (setting.isStringSetting()) {
                    final String str = (String)setting.getValue();
                    setting.setValue((Object)str.replace(" ", "_"));
                }
                try {
                    object.add(setting.getName(), jp.parse(setting.getValueAsString()));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }
    
    public String getDirectory(final Feature feature) {
        String directory = "";
        if (feature instanceof Module) {
            directory = directory + ((Module)feature).getCategory().getName() + "/";
        }
        return directory;
    }
}
