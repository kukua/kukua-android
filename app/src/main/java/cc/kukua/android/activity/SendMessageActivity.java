package cc.kukua.android.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.model.server_response_model.SendSmsResponseModel;
import cc.kukua.android.retrofit.APIService;
import cc.kukua.android.retrofit.RetrofitClient;
import cc.kukua.android.utils.LogUtils;
import cc.kukua.android.utils.UiUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_send)
    Button btnSend;

    String phoneNo;
    String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private String TAG = SendMessageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        ButterKnife.bind(this);

        toolbarTitle.setText(R.string.send_sms);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage();
            }
        });
    }

    protected void sendSMSMessage() {
        phoneNo = etPhone.getText().toString();
        message = etMessage.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, message, null, null);
                        hitSendMessageEndpoint(phoneNo, message);
                        Toast.makeText(getApplicationContext(), "SMS sent.",
                                Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    private void hitSendMessageEndpoint(String recipientPhone, String messageBody) {
        APIService apiService = RetrofitClient.getClient().create(APIService.class);
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("recipient", recipientPhone);
            paramObject.put("sms_text", messageBody);

            UiUtils.showProgressDialog(SendMessageActivity.this, getString(R.string.please_wait));
            Call<SendSmsResponseModel> call = apiService.sendSMS(paramObject.toString());
            call.enqueue(new Callback<SendSmsResponseModel>() {
                @Override
                public void onResponse(Call<SendSmsResponseModel> call, Response<SendSmsResponseModel> response) {
                    UiUtils.dismissAllProgressDialogs();
                    LogUtils.log(TAG, "ServerResponse: " + response.body().toString());
                    if (response.body().getState() == 200) {
                        clearInputFields();
                        Toast.makeText(SendMessageActivity.this, "Message Sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SendSmsResponseModel> call, Throwable t) {
                    UiUtils.dismissAllProgressDialogs();
                    Toast.makeText(SendMessageActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearInputFields() {
        etMessage.setText("");
        etPhone.setText("");
    }
}
