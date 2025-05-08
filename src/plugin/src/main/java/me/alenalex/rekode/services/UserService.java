package me.alenalex.rekode.services;

import me.alenalex.rekode.RekodePlugin;
import me.alenalex.rekode.abstractions.services.IUserService;

public class UserService implements IUserService {
    
    private final RekodePlugin plugin;
    private boolean initialized = false;

    public UserService(RekodePlugin plugin) {
        this.plugin = plugin;
    }
    
    public void initialize(){
        
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    public void stop() {
    }
}
