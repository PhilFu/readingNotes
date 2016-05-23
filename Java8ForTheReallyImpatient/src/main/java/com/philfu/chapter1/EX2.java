package com.philfu.chapter1;

import com.philfu.Exercise;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

/**
 * 使用java.io.File类的listFiles(FileFilter)和isDirectory方法，编写一个返回指定目录下所有子目录的方法。使用lambda表达式来代替FileFilter对象，再将它改写为一个方法引用。
 *
 * Created by Phil on 2016/5/23.
 */
public class EX2 implements Exercise {

    @Override
    @Test
    public void perform() {
        File f = new File("");
        File[] files = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
    }

    private File[] getDirWithLambda(String dir) {
        File dirFile = new File(dir);
        return dirFile.listFiles(f -> f.isDirectory());
    }

    private File[] getDirWithMethodReference(String dir) {
        File dirFile = new File(dir);
        return dirFile.listFiles(File::isDirectory);
    }
}
