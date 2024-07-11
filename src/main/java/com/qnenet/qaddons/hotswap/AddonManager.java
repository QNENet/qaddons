package com.qnenet.qaddons.hotswap;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qnenet.addonhotswappable.api.HotSwappableAddon;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

// To use this system:

// 1. Compile the `AddonImpl` class into a JAR file named `addon.jar` and place it in the directory specified by `ADDON_DIR` in the `AddonManager` class.

// 2. Start your Spring Boot application.

// 3. Use the endpoints:
//    - `GET http://localhost:8080/use-addon` to use the addon
//    - `GET http://localhost:8080/addon-version` to get the current addon version

// 4. To test hot-swapping:
//    - Update the `AddonImpl` class (e.g., change the version to "2.0")
//    - Recompile the addon project and replace the `addon.jar` file in the watched directory
//    - Wait a few seconds for the file

// This AddonManager class handles the following responsibilities:

// 1. Loading the addon JAR file
// 2. Creating a custom ClassLoader for the addon
// 3. Providing an ObjectFactory for creating instances

@Component
public class AddonManager {
    private static final String ADDON_DIR = "/path/to/your/addon/directory";
    private static final String ADDON_JAR = "addon.jar";
    private static final long POLL_INTERVAL = 5000; // 5 seconds

    private HotSwappableClassLoader classLoader;
    private Class<?> addonClass;
    private FileAlterationMonitor monitor;

    @PostConstruct
    public void init() throws Exception {
        loadAddon();
        startFileWatcher();
    }

    @PreDestroy
    public void destroy() throws Exception {
        if (monitor != null) {
            monitor.stop();
        }
    }

    private void loadAddon() throws Exception {
        File addonFile = new File(ADDON_DIR, ADDON_JAR);
        URL[] urls = new URL[]{addonFile.toURI().toURL()};
        classLoader = new HotSwappableClassLoader(urls);
        addonClass = classLoader.loadClass("com.example.AddonImpl");
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ObjectFactory<HotSwappableAddon> addonFactory() {
        return () -> {
            try {
                return (HotSwappableAddon) addonClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create addon instance", e);
            }
        };
    }

    private void startFileWatcher() throws Exception {
        FileAlterationObserver observer = new FileAlterationObserver(ADDON_DIR);
        observer.addListener(new FileAlterationListener() {
            @Override
            public void onFileChange(File file) {
                if (file.getName().equals(ADDON_JAR)) {
                    try {
                        loadAddon();
                        System.out.println("Addon reloaded");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Implement other methods of FileAlterationListener interface
            public void onStart(FileAlterationObserver observer) {}
            public void onDirectoryCreate(File directory) {}
            public void onDirectoryChange(File directory) {}
            public void onDirectoryDelete(File directory) {}
            public void onFileCreate(File file) {}
            public void onFileDelete(File file) {}
            public void onStop(FileAlterationObserver observer) {}
        });

        monitor = new FileAlterationMonitor(POLL_INTERVAL);
        monitor.addObserver(observer);
        monitor.start();
    }

    // Inner class for HotSwappableClassLoader
    private static class HotSwappableClassLoader extends URLClassLoader {
        public HotSwappableClassLoader(URL[] urls) {
            super(urls, HotSwappableClassLoader.class.getClassLoader());
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {
                return findClass(name);
            } catch (ClassNotFoundException e) {
                return super.loadClass(name);
            }
        }
    }
}