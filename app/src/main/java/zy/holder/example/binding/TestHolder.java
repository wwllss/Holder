package zy.holder.example.binding;

import androidx.viewbinding.ViewBinding;

import zy.holder.BindingViewHolder;

public abstract class TestHolder<VB extends ViewBinding> extends BindingViewHolder<String, VB> {

    public TestHolder(VB binding) {
        super(binding);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onBindData(String data) {

    }
}
