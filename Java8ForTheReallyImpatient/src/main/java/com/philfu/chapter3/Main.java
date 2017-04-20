package com.philfu.chapter3;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

/**
 * Created by fuweiwei02 on 2017/4/17.
 */
public class Main {
    private static final Logger logger = Logger.getLogger("Main");
    public static void main(String[] args) {
        // 3.1 延迟执行
        String x="", y="";
        logger.info("x: " + x + "y: " + y); //先计算字符串的值,然后传递给info方法,最后根据日志等级确定是否真要执行
        logger.info(() -> "x: " + x + "y: " + y);   //延迟执行,只有在要记录日志的时候,才会计算字符串的值

        // 组合
        // 传统方法需要创建一个中间图片。对于大图片,可能需要不小的存储空间
         Image image = new Image();
         Image image2 = transform(image, Color::brighter);
         Image finalImage = transform(image2, Color::darker);
        // 使用组合: 直接应用组合后的变换效果,不需要产生一个中间图片
         Image finalImage2 = transform(image2, compose(Color::brighter, Color::darker));

        // 延迟
        Image latent = LatentImage.from(image).transform(Color::brighter).transform(Color::darker).toImage();

        // 泛型   Employee extends Person
        // 如果一个方法只从列表中读取数据,那么它可以接受一个 List<? extends Person> 对象。（读取是协变的convariant,可以接受子类型）
        // 如果一个方法只向列表中写入数据,那么它可以接受一个 List<? super Employee> 对象。（写入是逆变的contravariant,可以接受父类型）
        // 一个函数类型的参数总是逆变的,而其返回值却是协变的。一般准则是父类作为参数类型,子类作为返回类型
        // void forEach(Consumer<? super T> action);
        // Stream<T> filter(Predicate<? super T> predicate);
        // <R> Stream<R> map(Function<? super T, ? extends R> mapper);
    }

    // 并行处理
    public static Color[][] parallelTransform(Color[][] in, UnaryOperator<Color> f) {
        int n = Runtime.getRuntime().availableProcessors();
        int height = in.length;
        int width = in[0].length;
        Color[][] out = new Color[height][width];
        try {
            ExecutorService pool = Executors.newCachedThreadPool();
            for (int i = 0; i < n; i++) {
                int fromY = i * height / n;
                int toY = (i + 1) * height / n;
                pool.submit(() -> {
                   for (int x = 0; x < width; x++) {
                       for (int y = fromY; y < toY; y++) {
                           out[y][x] = f.apply(in[y][x]);
                       }
                   }
                });
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    // 处理异常, first.run抛出异常,方法会被终止,second永远不会执行,并且调用者需要处理异常
    public static void doInOrder(Runnable first, Runnable second) {
        first.run();
        second.run();
    }

    // first抛出异常,线程为终止,second永远不会执行,但是方法会返回并进行另一个线程中的工作,因此无法让方法重新抛出异常
    public static void doInOrderAsync(Runnable first, Runnable second) {
        Thread t = new Thread() {
            public void run() {
                first.run();
                second.run();
            }
        };
        t.start();
    }

    // 提供一个handler处理异常
    public static void doInOrderAsync(Runnable first, Runnable second, Consumer<Throwable> handler) {
        Thread t = new Thread() {
            public void run() {
                try {
                    first.run();
                    second.run();
                } catch (Exception e) {
                    handler.accept(e);
                }
            }
        };
        t.start();
    }

    // first产生一个结果, 该结果会被second处理
    public static <T> void doInOrderAsync(Supplier<T> first, Consumer<T> second, Consumer<Throwable> handler) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    T result = first.get();
                    second.accept(result);
                } catch (Exception e) {
                    handler.accept(e);
                }
            }
        };
        thread.start();
    }

    public static <T> UnaryOperator<T> compose(UnaryOperator<T> op1, UnaryOperator<T> op2) {
        return t -> op2.apply(op1.apply(t));
    }

    private static Image transform(Image image, UnaryOperator<Color> f) {
        return image;
    }

    private static class Image {
    }

    private static class LatentImage {
        private Image in;
        private List<UnaryOperator<Color>> pendingOperations;   // 将操作先累积起来

        LatentImage transform(UnaryOperator<Color> f) {
            pendingOperations.add(f);
            return this;
        }

        public static LatentImage from(Image image) {
            return new LatentImage();
        }

        public static Image toImage() {
            return new Image();
        }
    }
}
