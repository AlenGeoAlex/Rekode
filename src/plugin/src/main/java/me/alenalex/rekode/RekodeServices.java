package me.alenalex.rekode;

import me.alenalex.rekode.abstractions.IRekodeServices;
import me.alenalex.rekode.abstractions.services.IMineService;
import me.alenalex.rekode.abstractions.services.IUserService;
import me.alenalex.rekode.services.MineService;
import me.alenalex.rekode.services.UserService;
import org.jetbrains.annotations.NotNull;

public class RekodeServices implements IRekodeServices {

    private final MineService mineService;
    private final UserService userService;

    public RekodeServices(final RekodePlugin plugin) {
        this.mineService = new MineService(plugin);
        this.userService = new UserService(plugin);
    }

    public boolean initialize(){
        try {
            this.mineService.initialize();
        } catch (Exception e) {
            RekodePlugin.INSTANCE.getLogger().severe("Failed to initialize mine service due to an exception."+ e.getMessage());
            return false;
        }

        try {
            this.userService.initialize();
        }catch (Exception e){
            RekodePlugin.INSTANCE.getLogger().severe("Failed to initialize user service due to an exception."+ e.getMessage());
            return false;
        }

        return true;
    }

    public void stop(){
        try {
            if(this.mineService.isInitialized())
                this.mineService.stop();
        }catch (Exception e){
            RekodePlugin.INSTANCE.getLogger().severe("Failed to stop mine service due to an exception."+ e.getMessage());
        }

        try {
            if(this.userService.isInitialized())
                this.userService.stop();
        }catch (Exception e){
            RekodePlugin.INSTANCE.getLogger().severe("Failed to stop user service due to an exception."+ e.getMessage());
        }
    }

    @Override
    public @NotNull IMineService mineService() {
        return this.mineService;
    }

    @Override
    public @NotNull IUserService userService() {
        return this.userService;
    }
}
