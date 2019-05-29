package com.example.rxandroiddemo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * https://www.jianshu.com/p/061f23ecc19a
 * Rxjava2中Observable不再支持被压，如果被压使用Flowable
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doClick();
//                doClick2();
//                doClick3();
//                doClick4();
//                doClick5();
//                doClick6();
//                doClick7();
//                doClick8();
//                doClick9();
//                doClick10();
//                doClick11();
//                doClick12();
//                doClick13();
//                doClick14();
//                doClick15();
//                doClick16();
//                doClick17();
//                doClick18();
//                doClick19();
//                doClick20();
//                doClick21();
//                doClick22();
//                doClick23();
                doClick24();
            }
        });
    }

    /**
     * 分步创建一个完整的调用示例
     */
    private void doClick() {
        /**
         * 创建被观察者
         */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("one");
                emitter.onNext("two");
                emitter.onNext("three");
                emitter.onComplete();
            }
        });

        /**
         * 创建观察者
         */
        Observer<String> observer = new Observer<String>() {

            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e("fff", "-------onSubscribe--------" + d);
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.e("fff", "-------onNext--------" + s);
                Log.e("fff", "-------onNext----- Thread.currentThread()---" + Thread.currentThread().getName());
                if (s.equals("two")) {
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("fff", "-------onError--------");
            }

            @Override
            public void onComplete() {
                Log.e("fff", "-------onComplete--------");
            }
        };
        observable.subscribe(observer);
    }

    /**
     * 链式创建示例
     */
    private void doClick2() {
        /**
         * 链式调用
         */
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("one");
                Thread.sleep(2000);
                emitter.onNext("two");
                Thread.sleep(2000);
                emitter.onNext("three");
                Thread.sleep(2000);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io()) //执行在io线程
                /**
                 * 回调在主线程
                 */
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("fff", "-------onSubscribe--------" + d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("fff", "-------onNext--------" + s);
                        Log.e("fff", "-------onNext----- Thread.currentThread()---" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("fff", "-------onError--------" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("fff", "-------onComplete--------");
                    }
                });
    }

    /**
     * 使用 Consumer
     */
    @SuppressLint("CheckResult")
    private void doClick3() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("one");
                Thread.sleep(2000);
                emitter.onNext("two");
                Thread.sleep(2000);
                emitter.onNext("three");
                Thread.sleep(2000);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("fff", "-------accept---e-----" + throwable.getMessage());
                    }
                });
    }

    /**
     * just
     */
    @SuppressLint("CheckResult")
    private void doClick4() {
        Observable.just("aaa", "bbb").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                });
    }

    /**
     * from
     */
    @SuppressLint("CheckResult")
    private void doClick5() {
        String[] arrays = {"aaa", "bbb", "ccc", "ddd"};
        Observable.fromArray(arrays).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                });
    }

    /**
     * range
     */
    @SuppressLint("CheckResult")
    private void doClick6() {
        Observable.range(6, 3).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                });
    }

    /**
     * timer，延时执行
     */
    @SuppressLint("CheckResult")
    private void doClick7() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                });
    }

    /**
     * interval 操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     */
    @SuppressLint("CheckResult")
    private void doClick8() {
        Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                });
    }

    /**
     * map，变换
     */
    @SuppressLint("CheckResult")
    private void doClick9() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "当前值是：" + integer;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                });
    }

    /**
     * flatMap 将一个被观察者转化为多个被观察者，然后再将他们合并到一个被观察者中，
     * flatMap转化后 并不能保证事件的顺序。
     */
    @SuppressLint("CheckResult")
    private void doClick10() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("新的" + integer, String.valueOf(integer));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                });
    }

    /**
     * concatMap和flatMap的区别在于 它能保证顺序
     */
    @SuppressLint("CheckResult")
    private void doClick11() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("新的" + integer, String.valueOf(integer));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("fff", "-------accept--------" + s);
                    }
                });
    }

    /**
     * buffer的作用是定时或定量缓存打包事件，将其发射到下游，而不是一次一个发射！
     */
    @SuppressLint("CheckResult")
    private void doClick12() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .buffer(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.e("fff", "-------accept--------");
                        for (Integer integer : integers) {
                            Log.e("fff", "-------accept--111------" + integer);
                        }
                    }
                });
    }

    /**
     * take 表示只取前面X个事件
     */
    @SuppressLint("CheckResult")
    private void doClick13() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .take(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("fff", "-------accept--------" + integer);
                    }
                });
    }

    /**
     * distinct 去重
     */
    @SuppressLint("CheckResult")
    private void doClick14() {
        Observable.just(1, 1, 3, 2, 3, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("fff", "--doClick14-----accept--------" + integer);
                    }
                });
    }

    /**
     * filter 过滤
     */
    @SuppressLint("CheckResult")
    private void doClick15() {
        Observable.just(1, 1, 3, 2, 3, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 3;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("fff", "---doClick15----accept--------" + integer);
                    }
                });
    }

    /**
     * element
     */
    @SuppressLint("CheckResult")
    private void doClick16() {
        Observable.range(0, 9)
                .subscribeOn(Schedulers.io())
                .elementAt(5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("fff", "---doClick16----accept--------" + integer);
                    }
                });
    }

    /**
     * skip
     */
    @SuppressLint("CheckResult")
    private void doClick17() {
        Observable.range(0, 9)
                .subscribeOn(Schedulers.io())
                .skip(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("fff", "---doClick17----accept--------" + integer);
                    }
                });
    }

    /**
     * concat，concatArray
     * 二者区别：组合被观察者的数量，即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
     */
    @SuppressLint("CheckResult")
    private void doClick18() {
        Observable.concat(Observable.just(1, 2),
                Observable.just(3, 4, 5),
                Observable.just(6, 7, 8))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("fff", "--doClick18-----accept--------" + integer);
                    }
                });
    }

    /**
     * merge
     */
    @SuppressLint("CheckResult")
    private void doClick19() {
        Observable.merge(Observable.just(1, 2),
                Observable.just(3, 4, 5),
                Observable.just(6, 7, 8))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("fff", "---doClick19----accept--------" + integer);
                    }
                });
    }

    /**
     * zip
     */
    @SuppressLint("CheckResult")
    private void doClick20() {

    }

    /**
     * collect
     */
    @SuppressLint("CheckResult")
    private void doClick21() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .collect(new Callable<List<Integer>>() {
                    @Override
                    public List<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<List<Integer>, Integer>() {
                    @Override
                    public void accept(List<Integer> integers, Integer integer) throws Exception {
                        integers.add(integer);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        for (Integer integer : integers) {
                            Log.e("fff", "---doClick21----accept--------" + integer);
                        }
                    }
                });
    }

    /**
     * startWith ，startWithArray
     */
    @SuppressLint("CheckResult")
    private void doClick22() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .startWith(4)
                .startWithArray(5, 6)
                .startWith(Observable.just(7, 8))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("fff", "--doClick22-----accept--------" + integer);
                    }
                });
    }

    /**
     * count
     */
    @SuppressLint("CheckResult")
    private void doClick23() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .count()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e("fff", "---doClick23----accept--------" + aLong);
                    }
                });
    }

    /**
     * 演示下线程切换
     */
    @SuppressLint("CheckResult")
    private void doClick24() {
        Observable.just(111)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        Log.e("fff", "--doClick24-----map----Thread----" + Thread.currentThread().getName());
                        return "参数经过(map)" + integer;
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        Log.e("fff", "--doClick24-----flatMap----Thread----" + Thread.currentThread().getName());
                        return Observable.just("参数经过(flatMap)" + s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        Log.e("fff", "--doClick24-----map----Thread--222--" + Thread.currentThread().getName());
                        return "参数再次(map)" + s;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        Log.e("fff", "--doClick24-----map----Thread--333--" + Thread.currentThread().getName());
                        return "参数再再次(map)" + s;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        Log.e("fff", "--doClick24-----flatMap----Thread--222--" + Thread.currentThread().getName());
                        return Observable.just("参数再次经过(flatMap)" + s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("fff", "--doClick24-----subscribe----Thread----" + Thread.currentThread().getName());
                        Log.e("fff", "--doClick24-----subscribe----result----" + s);
                    }
                });
    }

}
