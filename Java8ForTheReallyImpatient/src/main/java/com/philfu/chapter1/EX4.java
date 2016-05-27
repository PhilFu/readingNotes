package com.philfu.chapter1;

import com.philfu.Exercise;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 对于一个指定的File对象数组，首先按照路径的目录排序，然后对每组目录中的元素再按照路径名排序。使用lambda表达式（而不是Comparator）来实现。
 *
 * Created by Phil on 2016/5/24.
 */
public class EX4 implements Exercise{

    @Override
    @Test
    public void perform() {

    }

    public File[] sortFiles(File[] files) {
        Arrays.sort(files, (f1, f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) {
                return 1;
            } else if (!f1.isDirectory() && f2.isDirectory()) {
                return -1;
            } else {
                return f1.getName().compareToIgnoreCase(f2.getName());
            }
        });
        return files;
    }

    public File[] alternative(File[] files) {
        Arrays.sort(files, Comparator.comparing(File::isDirectory).reversed().thenComparing(File::getName));
        return files;
    }
}
