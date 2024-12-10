package zy.holder.example;

import zy.holder.BindingViewHolder;
import zy.holder.annotation.Holder;
import zy.holder.example.databinding.LayoutABinding;
import zy.holder.model.AModel;

@Holder(AModel.class)
public class AHolder extends BindingViewHolder<AModel, LayoutABinding> {

    public AHolder(LayoutABinding binding) {
        super(binding);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onBindData(AModel data) {

    }
}
