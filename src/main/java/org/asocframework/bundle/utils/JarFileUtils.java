package org.asocframework.bundle.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class JarFileUtils {

    public static byte[] getClassBytesFromJarFile(String filePath, String className) throws IOException {
        File file = new File(filePath);
        //for .class file
        if (filePath.endsWith(".class")) {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            copy(fis, bos);
            return bos.toByteArray();
        }
        if(file.isDirectory()){
            Set<String> fileSet = new HashSet<>();
            _extractClassNameFromDirectory(file,".class",fileSet);
            Iterator<String> iterator =
                    fileSet.stream().filter(f -> transformJarEntryNameToClassName(f).contains(className)).iterator();
            if(iterator.hasNext()){
                FileInputStream fis = new FileInputStream(iterator.next());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                copy(fis,bos);
                return bos.toByteArray();
            }
            return null;
        }
        //for jar file
        JarFile jarFile = new JarFile(file);
        List<JarEntry> list    = listFile(jarFile, jarEntry -> className.equals(transformJarEntryNameToClassName(jarEntry.getName())));
        if (list.isEmpty()) {
            return null;
        }
        JarEntry              jarEntry = list.get(0);
        InputStream           in       = jarFile.getInputStream(jarEntry);
        ByteArrayOutputStream out      = new ByteArrayOutputStream();
        copy(in, out);
        in.close();
        return out.toByteArray();
    }

    public static Set<String> extractClassNameFromJarFile(String hotPatchFilePath) throws IOException {
        File hotPatchFile = new File(hotPatchFilePath);
        //for class file
        if(hotPatchFile.isDirectory()){
            return extractClassNameFromDirectory(hotPatchFile);
        }else if(hotPatchFilePath.endsWith(".class")){
            return extractClassNameFromClassFile(hotPatchFile);
        }
        return extractClassNameFromJarFile(new JarFile(hotPatchFile));
    }

    public static Set<String> extractClassNameFromJarFile(JarFile jarFile) {
        List<JarEntry> list = listFile(jarFile, jarEntry -> jarEntry.getName().endsWith(".class"));
        return list.stream()
                .map(e -> transformJarEntryNameToClassName(e.getName()))
                .collect(Collectors.toSet());
    }

    public static String transformJarEntryNameToClassName(String jarEntryName) {
        String filename = StringUtils.replace(jarEntryName, ".class", "");
        return StringUtils.replace(filename, "/", ".");
    }

    public static List<JarEntry> listFile(String filePath, JarEntryFilter filter) throws IOException {
        return listFile(new JarFile(new File(filePath)), filter);
    }

    public static List<JarEntry> listFile(JarFile jarFile, JarEntryFilter filter) {
        List<JarEntry> list = new ArrayList<>();
        for (Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements(); ) {
            JarEntry jarEntry = e.nextElement();
            if (jarEntry.isDirectory()) {
                continue;
            }
            if (filter.accept(jarEntry)) {
                list.add(jarEntry);
            }
        }
        return list;
    }

    public static Set<String> extractClassNameFromClassFile(File clazzFile) throws IOException {
        String fileName = clazzFile.getName();
        String className = fileName.substring(fileName.indexOf("classes/")+"classes/".length(),fileName.lastIndexOf(".class"));
        className = className.replace("/",".");
        Set<String> classSet = new HashSet<>();
        classSet.add(className);
        return classSet;
    }

    public static Set<String> extractClassNameFromDirectory(File f){
        Set<String> classSet = new HashSet<>();
        _extractClassNameFromDirectory(f,".class",classSet);
        return classSet.stream().map(file->file.substring(f.getAbsolutePath().length()+1).replace(".class","").replace("/",".")).collect(Collectors.toSet());
    }

    private static void _extractClassNameFromDirectory(File f,String pattern,Set<String> classSet){
        if (f.isDirectory()) {
            String[] files = f.list();
            if (files == null) return;
            for (int i = 0, max = files.length; i < max; i++) {
                File current = new File(f, files[i]);
                if (current.isDirectory()) {
                    _extractClassNameFromDirectory(current, pattern, classSet);
                } else {
                    if (current.getName().toLowerCase().endsWith(pattern)) {
                        classSet.add(current.getAbsolutePath());
                    }
                }
            }
        }
    }

    private static int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
