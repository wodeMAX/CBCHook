package com.debin.cbc.hook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.debin.cbc.hook.util.PrefUtil;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private CheckBox rootCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootCheckBox = (CheckBox) findViewById(R.id.cb_root_check);
        rootCheckBox.setOnCheckedChangeListener(this);

        init();
    }

    private void init() {
        if (MyApplication.getPrefUtil() != null) {
            PrefUtil prefUtil = MyApplication.getPrefUtil();
            rootCheckBox.setChecked(prefUtil.getBoolean(PrefUtil.IGNORE_CBC_WARN, true));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (MyApplication.getPrefUtil() != null) {
            PrefUtil prefUtil = MyApplication.getPrefUtil();
            if (buttonView.getId() == rootCheckBox.getId()) {
                prefUtil.setBoolean(PrefUtil.IGNORE_CBC_WARN, isChecked);
            }
        } else {
            android.util.Log.e("onCheckedChanged", "MyApplication.getPrefUtil() return null");
        }
    }
}
