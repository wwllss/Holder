package zy.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangyuan
 * 2018/8/28.
 */
class DefaultItemTypePool implements ItemTypePool {

    private final List<ItemType> itemTypeList = new ArrayList<>();

    @Override
    public void registerType(@NonNull ItemType itemType) {
        removeIfExists(itemType);
        OneToManyLinker<?> linker = itemType.getLinker();
        if (linker == null) {
            itemTypeList.add(itemType);
        } else {
            HolderInfo[] holderInfoList = linker.getHolderInfoList();
            for (HolderInfo holderInfo : holderInfoList) {
                ItemType newItemType = ItemType.create(
                        itemType.getDataClass(),
                        holderInfo.holderClass,
                        holderInfo.layoutId
                );
                newItemType.setLinker(linker);
                itemTypeList.add(newItemType);
            }
        }
    }

    private void removeIfExists(@NonNull ItemType itemType) {
        Iterator<ItemType> iterator = itemTypeList.iterator();
        while (iterator.hasNext()) {
            ItemType next = iterator.next();
            if (itemType.equals(next)) {
                iterator.remove();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> int getItemType(@NonNull T data, int position) {
        for (int i = 0; i < itemTypeList.size(); i++) {
            ItemType itemType = itemTypeList.get(i);
            if (itemType.getDataClass() == data.getClass()) {
                OneToManyLinker<Object> linker = (OneToManyLinker<Object>) itemType.getLinker();
                return i + (linker == null ? 0 : linker.linkHolder(data, position));
            }
        }
        throw new RuntimeException("error data ---> " + data.getClass());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> BaseViewHolder<T> newInstance(ViewGroup parent, int itemViewType) {
        ItemType itemType = itemTypeList.get(itemViewType);
        Class<? extends BaseViewHolder<?>> holderClass = itemType.getHolderClass();
        boolean isBinding = BindingViewHolder.class.isAssignableFrom(holderClass);
        if (isBinding) {
            try {
                Class<? extends ViewBinding> bindingClass = HolderUtils.findBindingClass(holderClass);
                Constructor<? extends BaseViewHolder<?>> constructor = holderClass.getDeclaredConstructor(bindingClass);
                constructor.setAccessible(true);
                Context context = parent.getContext();
                ViewBinding viewBinding = HolderUtils.binding(bindingClass, context, parent);
                return (BaseViewHolder<T>) constructor.newInstance(viewBinding);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int layoutId = itemType.getLayoutId();
        try {
            Constructor<? extends BaseViewHolder<?>> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            Context context = parent.getContext();
            View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
            BaseViewHolder<T> baseViewHolder = (BaseViewHolder<T>) constructor.newInstance(view);
            if (isBinding) {
                BindingViewHolder<T, ViewBinding> bindingViewHolder = (BindingViewHolder<T, ViewBinding>) baseViewHolder;
                Class<? extends ViewBinding> bindingClass = HolderUtils.findBindingClass(holderClass);
                bindingViewHolder.binding = HolderUtils.binding(bindingClass, context, parent);
            }
            return baseViewHolder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
