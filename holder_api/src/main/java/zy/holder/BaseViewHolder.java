package zy.holder;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import androidx.appcompat.widget.TintContextWrapper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * @author zhangyuan
 * 2018/8/28.
 */
public abstract class BaseViewHolder<T> extends ViewHolder implements LifecycleObserver {

    private final Context context;

    private T data;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        initView();
    }

    public Context getContext() {
        return context instanceof TintContextWrapper
                ? ((ContextWrapper) context).getBaseContext()
                : context;
    }

    public T getData() {
        return data;
    }

    public void bindData(T data) {
        this.data = data;
        onBindData(data);
    }

    protected abstract void initView();

    protected abstract void onBindData(T data);

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        //empty
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        //empty
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        //empty
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        //empty
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        //empty
    }
}
