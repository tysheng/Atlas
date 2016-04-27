package tysheng.atlas.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by shengtianyang on 16/4/15.
 */
public class RxBus {
    private static RxBus mRxBus = null;

    public static synchronized RxBus getInstance() {
        if (mRxBus == null) {
            mRxBus = new RxBus();
        }
        return mRxBus;
    }

    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     */
    private Subject<Object, Object> mRxBusObserverable = new SerializedSubject<>(PublishSubject.create());

    public void post(Object o) {
        mRxBusObserverable.onNext(o);
    }


    public Observable<Object> toObserverable() {
        return mRxBusObserverable;
    }


    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mRxBusObserverable.hasObservers();
    }

    /**
     * BehaviorSubject
     */

//    private Subject<Object, Object> mBehavior = new SerializedSubject<>(BehaviorSubject.create());
//
//    public void postBehavior(Object o) {
//        mBehavior.onNext(o);
//    }
//    public Observable<Object> toBehaviorObserverable() {
//        return mBehavior;
//    }
//    public boolean hasBehaviorObservers() {
//        return mBehavior.hasObservers();
//    }

}
