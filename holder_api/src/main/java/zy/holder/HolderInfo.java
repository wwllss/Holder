package zy.holder;

import androidx.annotation.LayoutRes;

/**
 * @author zhangyuan
 * 2017/8/28.
 */
public class HolderInfo {

    @LayoutRes
    final int layoutId;

    final Class<? extends BaseViewHolder<?>> holderClass;

    private HolderInfo(Class<? extends BaseViewHolder<?>> holderClass, int layoutId) {
        this.holderClass = holderClass;
        this.layoutId = layoutId;
    }

    public static HolderInfo info(Class<? extends BaseViewHolder<?>> holderClass, int layoutId) {
        return new HolderInfo(holderClass, layoutId);
    }
}
