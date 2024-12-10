package zy.holder;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyuan
 * 2018/8/28.
 */
public class OneAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private static final Map<Class<?>, ItemType> GLOBAL_ITEM_TYPE_LIST = new HashMap<>();

    private final List<T> dataList = new ArrayList<>();

    private final ItemTypePool itemTypePool;

    public OneAdapter() {
        this(new DefaultItemTypePool());
    }

    public OneAdapter(ItemTypePool itemTypePool) {
        if (itemTypePool == null) {
            throw new NullPointerException("itemTypePool must not be null");
        }
        this.itemTypePool = itemTypePool;
    }

    @NonNull
    @Override
    public BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder<T> holder = itemTypePool.newInstance(parent, viewType);
        Context context = parent.getContext();
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(holder);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        holder.bindData(getItem(position));
    }

    public List<T> getDataList() {
        return dataList;
    }

    private T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return itemTypePool.getItemType(getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public int notifyDataSetChanged(List<T> dataList) {
        return notifyDataSetChanged(dataList, true);
    }

    public int notifyDataSetChanged(List<T> dataList, boolean isRefresh) {
        if (dataList == null) {
            return this.dataList.size();
        }
        if (isRefresh) {
            this.dataList.clear();
        }
        this.dataList.addAll(dataList);
        super.notifyDataSetChanged();
        return this.dataList.size();
    }

    public int sizeOf(Class<?> type) {
        int count = 0;
        for (T t : dataList) {
            if (type.isInstance(t)) {
                count++;
            }
        }
        return count;
    }

    public int indexOf(Class<?> type) {
        if (!dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                T itemVO = dataList.get(i);
                if (type.isInstance(itemVO)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Class<?> type) {
        if (!dataList.isEmpty()) {
            for (int i = dataList.size() - 1; i >= 0; i--) {
                T itemVO = dataList.get(i);
                if (type.isInstance(itemVO)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static void register(String holderRegisterClassName) {
        try {
            Object o = Class.forName(holderRegisterClassName).newInstance();
            if (o instanceof HolderRegister) {
                ((HolderRegister) o).register();
            }
        } catch (Exception e) {
            Log.e(OneAdapter.class.getSimpleName(), e.getMessage());
        }
    }

    public static void globalRegister(
            @NonNull Class<?> dataClass,
            @NonNull Class<? extends BaseViewHolder<?>> holderClass,
            @LayoutRes int layoutId) {
        GLOBAL_ITEM_TYPE_LIST.put(dataClass, ItemType.create(dataClass, holderClass, layoutId));
    }

    public void register(Class<?>... dataClassArr) {
        for (Class<?> clazz : dataClassArr) {
            itemTypePool.registerType(GLOBAL_ITEM_TYPE_LIST.get(clazz));
        }
    }

    public void register(
            @NonNull Class<?> dataClass,
            @NonNull Class<? extends BaseViewHolder<?>> holderClass,
            @LayoutRes int layoutId) {
        itemTypePool.registerType(ItemType.create(dataClass, holderClass, layoutId));
    }

    public <D> void register(
            @NonNull Class<? extends D> dataClass, @NonNull OneToManyLinker<? super D> linker) {
        itemTypePool.registerType(ItemType.create(dataClass, linker));
    }

    public static <T> BaseViewHolder<T> newHolder(@NonNull ViewGroup parent, @NonNull T data) {
        OneAdapter<T> adapter = new OneAdapter<>();
        adapter.register(data.getClass());
        List<T> dataList = Collections.singletonList(data);
        adapter.notifyDataSetChanged(dataList);
        return adapter.onCreateViewHolder(parent, adapter.getItemViewType(dataList.indexOf(data)));
    }

}
