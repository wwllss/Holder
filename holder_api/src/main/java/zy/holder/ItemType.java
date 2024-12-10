package zy.holder;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * @author zhangyuan
 * 2018/8/28.
 */
public class ItemType {

    private int layoutId;

    private final Class<?> dataClass;

    private Class<? extends BaseViewHolder<?>> holderClass;

    private OneToManyLinker<?> linker;

    private ItemType(@NonNull Class<?> dataClass, @NonNull Class<? extends BaseViewHolder<?>> holderClass, @LayoutRes int layoutId) {
        this.dataClass = dataClass;
        this.holderClass = holderClass;
        this.layoutId = layoutId;
    }

    private ItemType(@NonNull Class<?> dataClass, OneToManyLinker<?> linker) {
        this.dataClass = dataClass;
        this.linker = linker;
    }

    public static ItemType create(@NonNull Class<?> dataClass, @NonNull Class<? extends BaseViewHolder<?>> holderClass, @LayoutRes int layoutId) {
        return new ItemType(dataClass, holderClass, layoutId);
    }

    public static <T> ItemType create(@NonNull Class<T> dataClass, @NonNull OneToManyLinker<? super T> linker) {
        return new ItemType(dataClass, linker);
    }

    public int getLayoutId() {
        return layoutId;
    }

    public Class<?> getDataClass() {
        return dataClass;
    }

    public Class<? extends BaseViewHolder<?>> getHolderClass() {
        return holderClass;
    }

    public OneToManyLinker<?> getLinker() {
        return linker;
    }

    public void setLinker(OneToManyLinker<?> linker) {
        this.linker = linker;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ItemType && (obj == this || dataClass == ((ItemType) obj).dataClass);
    }
}
