package com.qnenet.qaddons.hotswap;

import java.net.URL;
import java.net.URLClassLoader;




public class HotSwappableClassLoader extends URLClassLoader {
    public HotSwappableClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            // First, check if the class has already been loaded
            Class<?> loadedClass = findLoadedClass(name);
            if (loadedClass != null) {
                return loadedClass;
            }

            // Try to load the class from our URLs first
            try {
                return findClass(name);
            } catch (ClassNotFoundException e) {
                // If not found, delegate to parent
                return super.loadClass(name, resolve);
            }
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }
}