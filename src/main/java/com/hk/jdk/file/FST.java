package com.hk.jdk.file;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class FST {


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
        main.defaultFileSystemTest();
    }
}
