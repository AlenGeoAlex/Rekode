package me.alenalex.rekode;

import me.alenalex.rekode.abstractions.IRekodeStores;
import me.alenalex.rekode.abstractions.store.IMineStore;
import me.alenalex.rekode.abstractions.store.IMineUserStore;
import me.alenalex.rekode.abstractions.store.IUserStore;
import me.alenalex.rekode.store.MineStore;
import me.alenalex.rekode.store.MineUserStore;
import me.alenalex.rekode.store.UserStore;
import org.jetbrains.annotations.NotNull;

public class RekodeStores implements IRekodeStores {

    private final IUserStore userService;
    private final IMineStore mineStore;
    private final IMineUserStore mineUserStore;

    public RekodeStores(RekodePlugin plugin) {
        this.userService = new UserStore(plugin);
        this.mineStore = new MineStore(plugin);
        this.mineUserStore = new MineUserStore(plugin);
    }

    @Override
    public @NotNull IUserStore userStore() {
        return this.userService;
    }

    @Override
    public @NotNull IMineStore mineStore() {
        return this.mineStore;
    }

    @Override
    public @NotNull IMineUserStore mineUserStore() {
        return this.mineUserStore;
    }
}
