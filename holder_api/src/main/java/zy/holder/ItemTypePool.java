package zy.holder;

import android.view.ViewGroup;

/**
 * @author zhangyuan
 * 2018/8/28.
 */
public interface ItemTypePool {

    void registerType(ItemType itemType);

    <T> int getItemType(T data, int position);

    <T> BaseViewHolder<T> newInstance(ViewGroup parent, int itemViewType);

}
