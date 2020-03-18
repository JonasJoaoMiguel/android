package com.example.androidserver;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.androidserver.server.Controller;
import com.example.androidserver.task.TerminalPaymentTask;
import com.example.androidserver.websocket.ToUpperWebsocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.uol.pagseguro.plugpag.PlugPag;
import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;
import br.com.uol.pagseguro.plugpag.PlugPagVoidData;

public class MainActivity extends AppCompatActivity implements TaskHandler , View.OnClickListener{

    private static final int PERMISSIONS_REQUEST_CODE = 0x1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Controller controller = new Controller();

//        try {
//            controller.connect(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onClick(View v) {
        PlugPagManager.create(this.getApplicationContext());
        this.requestPermissions();
        new ToUpperWebsocket(6060 , this).start();
    }

    private void requestPermissions() {
        String[] missingPermissions = null;

        missingPermissions = this.filterMissingPermissions(this.getManifestPermissions());

        if (missingPermissions != null && missingPermissions.length > 0) {
            ActivityCompat.requestPermissions(this, missingPermissions, MainActivity.PERMISSIONS_REQUEST_CODE);
        } else {
            System.out.println(R.string.msg_all_permissions_granted);
        }
    }

    private String[] filterMissingPermissions(String[] permissions) {
        String[] missingPermissions = null;
        List<String> list = null;
        list = new ArrayList<>();

        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    list.add(permission);
                }
            }
        }

        missingPermissions = list.toArray(new String[0]);
        return missingPermissions;
    }

    private String[] getManifestPermissions() {
        String[] permissions = null;
        PackageInfo info = null;

        try {
            info = this.getPackageManager()
                    .getPackageInfo(this.getApplicationContext().getPackageName(), PackageManager.GET_PERMISSIONS);
            permissions = info.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("PlugPag", "Package name not found", e);
        }

        if (permissions == null) {
            permissions = new String[0];
        }

        return permissions;
    }

    public void startTerminalCreditPayment(PlugPagPaymentData paymentData) {

        new TerminalPaymentTask(this).execute(paymentData);
    }

    @Override
    public void onTaskStart() {

    }

    @Override
    public void onProgressPublished(String progress, Object transactionInfo) {

        String message = null;
        String type = null;

        if (TextUtils.isEmpty(progress)) {
            message = this.getString(R.string.msg_wait);
        } else {
            message = progress;
        }

        if (transactionInfo instanceof PlugPagPaymentData) {
            switch (((PlugPagPaymentData) transactionInfo).getType()) {
                case PlugPag.TYPE_CREDITO:
                    type = this.getString(R.string.type_credit);
                    break;

                case PlugPag.TYPE_DEBITO:
                    type = this.getString(R.string.type_debit);
                    break;

                case PlugPag.TYPE_VOUCHER:
                    type = this.getString(R.string.type_voucher);
                    break;
            }

            message = this.getString(
                    R.string.msg_payment_info,
                    type,
                    (double) ((PlugPagPaymentData) transactionInfo).getAmount() / 100d,
                    ((PlugPagPaymentData) transactionInfo).getInstallments(),
                    message);
        } else if (transactionInfo instanceof PlugPagVoidData) {
            message = this.getString(R.string.msg_void_payment_info, message);
        }

    }

    @Override
    public void onTaskFinished(Object result) {

    }


}
