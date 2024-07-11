package com.qnenet.qaddons.hotswap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddonController {
    private final AddonService addonService;

    public AddonController(AddonService addonService) {
        this.addonService = addonService;
    }

    @GetMapping("/use-addon")
    public String useAddon() {
        addonService.useAddon();
        return "Addon used successfully";
    }

    @GetMapping("/addon-version")
    public String getAddonVersion() {
        return "Current addon version: " + addonService.getAddonVersion();
    }
}