package zy.holder;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * @author zhangyuan
 * 2017/8/28.
 */
public interface OneToManyLinker<T> {

    /**
     * 获取该数据源对应的多重HolderInfo
     *
     * @return array
     */
    @NonNull
    HolderInfo[] getHolderInfoList();

    /**
     * 根据传入数据返回getHolderInfoList所得数组的角标
     *
     * @param data     传入数据
     * @param position adapter中的position，请勿将该数据直接return
     * @return getHolderInfoList所得数组的角标
     */
    @IntRange(from = 0)
    int linkHolder(T data, int position);

}
