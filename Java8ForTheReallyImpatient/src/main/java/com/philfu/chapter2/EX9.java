package com.philfu.chapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.philfu.Exercise;

/**
 * Created by fuweiwei02 on 2017/4/13.
 */
public class EX9<T> implements Exercise{
    @Override
    @Test
    public void perform() {

    }

    public List<T> mergeToArray1(Stream<ArrayList<T>> stream) {
        return stream.flatMap(ArrayList::stream).collect(Collectors.toList());
    }

    // reduce 接收的函数有两个参数,第一个参数是上次函数执行的返回值（中间值）,第二个参数是stream中的当前元素。
    // 该函数把这两个元素合并到一起,得到的结果被赋值给下次执行这个函数的第一个参数。
    // 第一次执行的时候,第一个参数的值是Stream中的第一个元素,第二个参数是Stream中的第二个元素。
    // 第二次执行的时候,第一个参数是Stream中前两个元素处理后的结果,第二个参数是Stream中的第三个元素,依次类推。
    public List<T> mergeToArray2(Stream<ArrayList<T>> stream) {
        return stream.reduce((l, e) -> {        // 该 reduce 返回 Optional
           ArrayList<T> list = new ArrayList<>(l);
           list.addAll(e);
           return list;
        }).orElse(new ArrayList<>());
    }

    // reduce的第一个参数 identity, 指定Stream循环的初始值。如果Stream为空,直接返回该值,所以reduce的这种用法不会返回Optional
    public List<T> mergeToArray3(Stream<ArrayList<T>> stream) {
        return stream.reduce(new ArrayList<>(), (l, e) -> {
           ArrayList<T> list = new ArrayList<>(l);
           list.addAll(e);
           return list;
        });
    }

    // 如果使用了parallelStream, reduce操作是并发进行的, 为了避免竞争,每个reduce线程都会有独立的结果
    // reduce的第三个参数combiner的作用在于合并每个线程的结果,得到最终结果。
    // combiner用来处理并发操作,如何处理数据的重复性,应多做考虑,否则会出现重复数据（?）
    public List<T> mergeToArray4(Stream<ArrayList<T>> stream) {
        return stream.reduce(new ArrayList<>(), (l, e) -> {
           ArrayList<T> list = new ArrayList<>(l);
           list.addAll(e);
           return list;
        }, (l, e) -> {
            ArrayList<T> list = new ArrayList<>(l);
            list.addAll(e);
            return list;
        });
    }
}
