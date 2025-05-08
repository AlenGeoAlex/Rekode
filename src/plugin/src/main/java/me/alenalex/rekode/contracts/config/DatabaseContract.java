package me.alenalex.rekode.contracts.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.PostProcess;
import me.alenalex.rekode.RekodePlugin;
import me.alenalex.rekode.base.contracts.config.IDatabaseContract;
import me.alenalex.rekode.base.enums.RekodeDbType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@SuppressWarnings("all")
@Configuration
public class DatabaseContract implements IDatabaseContract {

    public DatabaseContract() {
    }


    @Comment({"The type of database to connect to", "Currently only MariaDb/MySQL is supported", "Default: MariaDb"})
    private final RekodeDbType type = RekodeDbType.MariaDb;
    @Comment({"The host of the database", "Default: localhost"})
    private final String host = "localhost";
    @Comment({"The name of the database", "Default: rekode"})
    private final String database = "rekode";
    @Comment({"The username to connect to the database", "Default: root"})
    private final String username = "root";
    @Comment({"The password to connect to the database", "Default: empty"})
    private final String password = "";
    @Comment({"The port to connect to the database", "Default: 3306"})
    private final int port = 3306;
    @Comment({"Additional database properties", "Default: empty"})
    private final HashMap<String, String> properties = new HashMap<>();


    @Override
    public @NotNull RekodeDbType type() {
        return this.type;
    }

    @Override
    public @NotNull String host() {
        return this.host;
    }

    @Override
    public @NotNull String database() {
        return this.database;
    }

    @Override
    public @NotNull String username() {
        return this.username;
    }

    @Override
    public @NotNull String password() {
        return this.password;
    }

    @Override
    public int port() {
        return this.port;
    }

    @Override
    public @NotNull HashMap<String, String> properties() {
        return this.properties;
    }

    @PostProcess
    public void postProcess(){
        if(this.type == RekodeDbType.MySql){
            RekodePlugin.INSTANCE.getLogger().warning("Detected MySql, Its prefered to use MariaDb since the plugin was written for MariaDb.");
        }

        if(!properties.isEmpty()){
            RekodePlugin.INSTANCE.getLogger().info("Detected database properties, below properties will be applied to the connection");
            RekodePlugin.INSTANCE.getLogger().info("Properties: "+properties.entrySet().stream().map(entry -> entry.getKey()+"="+entry.getValue()).reduce("", (a,b) -> a+b));
        }
    }
}
