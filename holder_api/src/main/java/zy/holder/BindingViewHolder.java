package zy.holder;

import android.view.View;

import androidx.viewbinding.ViewBinding;

public abstract class BindingViewHolder<T, VB extends ViewBinding> extends BaseViewHolder<T> {

    VB binding;

    private BindingViewHolder(View itemView) {
        super(itemView);
    }

    public BindingViewHolder(VB binding) {
        this(binding.getRoot());
        this.binding = binding;
    }

    @Override
    protected void initView() {
        //empty
    }

    public VB getBinding() {
        return binding;
    }
}
