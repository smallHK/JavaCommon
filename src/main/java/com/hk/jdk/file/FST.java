package com.hk.jdk.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;

public class FST {


    /**
     * 查看jrt文件系统
     */
    private void jrtFileSystem() {

        try {
            FileSystem fs = FileSystems.newFileSystem(URI.create("jrt:/"), new HashMap<>());
            for(Path root: fs.getRootDirectories()) {
                System.out.println(root);
            }
            System.out.println(fs.getSeparator());


            URL classUrl = new URL("jrt:/java.base/java/lang/Object.class");
            InputStream input = classUrl.openStream();
            byte[] bytes = input.readAllBytes();
            System.out.println("Object.class file size: " + bytes.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //默认文件系统属性查看
    private void defaultFileSystemTest(){
        FileSystem fs = FileSystems.getDefault();
        System.out.println("根目录");
        for(Path path : fs.getRootDirectories()) {
            System.out.println(path);
        }
        System.out.println("存储");
        for(FileStore fst: fs.getFileStores()) {
            try {
                System.out.println(fst.name());
                System.out.println(fst.type());
                System.out.println(fst.getTotalSpace());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public static void main(String[] args) {

        var main = new FST();
//        main.defaultFileSystemTest();
        main.jrtFileSystem();
    }
}
