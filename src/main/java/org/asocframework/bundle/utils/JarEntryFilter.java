package org.asocframework.bundle.utils;

import java.util.jar.JarEntry;

public interface JarEntryFilter {

    boolean accept(JarEntry jarEntry);
}
