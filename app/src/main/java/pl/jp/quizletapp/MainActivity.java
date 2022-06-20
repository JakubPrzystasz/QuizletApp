package pl.jp.quizletapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    AppCompatEditText et_server = (AppCompatEditText) findViewById(R.id.et_server);
                    et_server.setText(Objects.requireNonNull(data).getStringExtra("server"));
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission(Manifest.permission.INTERNET,100);
        setContentView(R.layout.activity_main);

        final Button btn_scan = (Button) findViewById(R.id.btn_scan);
        final Button btn_start = (Button) findViewById(R.id.btn_start);

        btn_scan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            someActivityResultLauncher.launch(intent);
        });

        btn_start.setOnClickListener(v -> {
            final AppCompatEditText et_server = (AppCompatEditText) findViewById(R.id.et_server);
            final AppCompatEditText et_login = (AppCompatEditText) findViewById(R.id.et_login);

            et_login.setText("j0tp3");
            et_server.setText("http://192.168.1.102:7070/java");

            if(!URLUtil.isValidUrl(String.valueOf(et_server.getText()))){
                Toast.makeText(this, "Adres serwera jest nieprawidłowy", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(et_login.getText())){
                Toast.makeText(this, "Login jest nieprawidłowy", Toast.LENGTH_SHORT).show();
                return;
            }

            //use retrofit to check connection

            Toast.makeText(this, "Logowanie...", Toast.LENGTH_SHORT).show();

        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
    }

}