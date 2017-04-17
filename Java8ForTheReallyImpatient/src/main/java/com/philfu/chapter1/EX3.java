package com.philfu.chapter1;

import com.philfu.Exercise;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 使用java.io.File类的list(FilenameFilter)方法，编写一个返回指定目录下、具有指定扩展名的所有文件。使用lambda表达式（而不是FilenameFilter）来实现。它会捕获闭合作用域中的哪些变量？
 *
 * Created by Phil on 2016/5/23.
 */
public class EX3 implements Exercise{

    @Override
    @Test
    public void perform() {
        File f = new File("");
        f.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("extension");
            }
        });
    }

    public String[] getFileWithExtension(String dir, String extension) {
        validateExtension(extension);
        File dirFile = new File(dir);
        if (dirFile.isFile()) {
            return dirFile.list((dirF, name) -> name.endsWith(extension));
        } else {
            throw new IllegalArgumentException("dir is not a file");
        }
    }

    private void validateExtension(String extension) {
        if (extension == null) {
            throw new IllegalArgumentException("extension cann't be null");
        }
    }
}
