package com.example.android.architecture.redprints.sampleapp.presentation;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T binding(@LayoutRes int layoutResId) {
        final T binding = DataBindingUtil.setContentView(this, layoutResId);
        binding.setLifecycleOwner(this);
        return binding;
    }
}
