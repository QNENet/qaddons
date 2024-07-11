package com.qnenet.qaddons.hotswap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;

import com.qnenet.addonhotswappable.api.HotSwappableAddon;

@Service
public class AddonService {
    private final ObjectFactory<HotSwappableAddon> addonFactory;

    public AddonService(AddonManager addonManager) {
        this.addonFactory = addonManager.addonFactory();
    }

    public void useAddon() {
        HotSwappableAddon addon = addonFactory.getObject();
        addon.doSomething();
    }

    public String getAddonVersion() {
        HotSwappableAddon addon = addonFactory.getObject();
        return addon.getVersion();
    }
}