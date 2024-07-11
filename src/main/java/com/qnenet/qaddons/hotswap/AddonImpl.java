package com.qnenet.qaddons.hotswap;


import java.time.LocalDateTime;

public class AddonImpl implements HotSwappableAddon {
    private String version;

    public AddonImpl() {
        this.version = "1.0";
    }

    @Override
    public void doSomething() {
        System.out.println("Addon version " + version + " doing something at " + LocalDateTime.now());
    }

    @Override
    public String getVersion() {
        return version;
    }
}