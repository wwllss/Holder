package zy.holder.appc;

import zy.holder.BindingViewHolder;
import zy.holder.annotation.Holder;
import zy.holder.appc.databinding.LayoutCBinding;
import zy.holder.model.CModel;

@Holder(CModel.class)
public class CHolder extends BindingViewHolder<CModel, LayoutCBinding> {

    public CHolder(LayoutCBinding binding) {
        super(binding);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onBindData(CModel data) {

    }
}
