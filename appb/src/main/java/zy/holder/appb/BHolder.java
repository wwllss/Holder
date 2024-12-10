package zy.holder.appb;

import zy.holder.BindingViewHolder;
import zy.holder.annotation.Holder;
import zy.holder.appb.databinding.LayoutBBinding;
import zy.holder.model.BModel;

@Holder(BModel.class)
public class BHolder extends BindingViewHolder<BModel, LayoutBBinding> {

    public BHolder(LayoutBBinding binding) {
        super(binding);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onBindData(BModel data) {

    }
}
