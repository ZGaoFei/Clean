package com.demo.clean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PERMISSION_CODE = 0x0001;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    private TextView tvDefaultDeviceId;
    private TextView tvSimSerialNumber;
    private TextView tvAndroidId;
    private TextView tvSerialNo;
    private TextView tvMacAddress;

    private TextView tvCurrentDeviceId;
    private EditText etDeviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        requestPermission();
    }

    private void initView() {
        tvDefaultDeviceId = findViewById(R.id.tv_device_id);
        tvSimSerialNumber = findViewById(R.id.tv_sim_serial_number);
        tvAndroidId = findViewById(R.id.tv_android_id);
        tvSerialNo = findViewById(R.id.tv_serial_no);
        tvMacAddress = findViewById(R.id.tv_mac_address);

        TextView tvReload = findViewById(R.id.tv_reload);
        tvReload.setOnClickListener(this);
        tvCurrentDeviceId = findViewById(R.id.tv_current_device_id);
        etDeviceId = findViewById(R.id.et_device_id);
    }

    private void initData() {

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                PERMISSIONS,
                REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDefaultId();
        }
    }

    private void getDefaultId() {
        String currentDeviceId = Utils.getIMEI(this);

        if (!TextUtils.isEmpty(currentDeviceId)) {
            // 第一次进入时先缓存当前的deviceid，后面不会再缓存
            Utils.saveDefaultDeviceId(this, currentDeviceId);

            tvDefaultDeviceId.setText(currentDeviceId);
        }

        String simSerialNumber = Utils.getSimSerialNumber(this);
        if (!TextUtils.isEmpty(simSerialNumber)) {
            tvSimSerialNumber.setText(simSerialNumber);
        }

        String androidId = Utils.getAndroidId(this);
        if (!TextUtils.isEmpty(androidId)) {
            tvAndroidId.setText(androidId);
        }

        String serialNo = Utils.getSerialNo(this);
        if (!TextUtils.isEmpty(serialNo)) {
            tvSerialNo.setText(serialNo);
        }

//        String macAddress = Utils.getMacAddress(this);
//        if (!TextUtils.isEmpty(macAddress)) {
//            tvMacAddress.setText(macAddress);
//        }

    }

    private void getCurrentDeviceId() {
        String currentDeviceId = Utils.getIMEI(this);
        if (!TextUtils.isEmpty(currentDeviceId)) {
            tvCurrentDeviceId.setText(currentDeviceId);
        }

        String testGetIMEI = testGetIMEI();
        Log.e("test", "==111==testGetIMEI====" + testGetIMEI);
    }

    public String testGetIMEI() {
        return "hahahhaha";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reload:
                getCurrentDeviceId();
                break;
        }
    }
}