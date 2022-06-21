package pl.jp.quizletapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import pl.jp.quizletapp.models.User;
import pl.jp.quizletapp.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn_scan;
    Button btn_start;
    private AppCompatEditText et_server;
    private AppCompatEditText et_login;
    private QuizletApp quizletApp;

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
        checkPermission(Manifest.permission.INTERNET, 100);
        setContentView(R.layout.activity_main);

        quizletApp = (QuizletApp) getApplicationContext();

        et_server = (AppCompatEditText) findViewById(R.id.et_server);
        et_login = (AppCompatEditText) findViewById(R.id.et_login);
        btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_start = (Button) findViewById(R.id.btn_start);

        et_login.setText("j0tp3");
        et_server.setText("http://kni.prz.edu.pl:8080/");

        btn_scan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            someActivityResultLauncher.launch(intent);
        });

        btn_start.setOnClickListener(v -> {
            if (!URLUtil.isValidUrl(String.valueOf(et_server.getText()))) {
                Toast.makeText(this, "Adres serwera jest nieprawidłowy", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(et_login.getText())) {
                Toast.makeText(this, "Login jest nieprawidłowy", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Logowanie...", Toast.LENGTH_SHORT).show();

            quizletApp.setRetrofit(String.valueOf(et_server.getText()));

            UserService service = quizletApp.getRetrofit().create(UserService.class);
            Call<User> callAsync = service.getUser(String.valueOf(et_login.getText()));

            callAsync.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == HttpsURLConnection.HTTP_OK) {
                        User user = response.body();
                        quizletApp.setUser(user);
                        Toast.makeText(MainActivity.this, "Zalogowano jako " + user.getLogin(), Toast.LENGTH_SHORT).show();
                        loggedIn();
                    } else if (response.code() == HttpsURLConnection.HTTP_NOT_FOUND) {
                        Call<User> callAsync = service.postUser(String.valueOf(et_login.getText()));
                        callAsync.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.code() == HttpsURLConnection.HTTP_CREATED) {
                                    User user = response.body();
                                    quizletApp.setUser(user);
                                    Toast.makeText(MainActivity.this, "Utworzono użytkownika " + user.getLogin(), Toast.LENGTH_SHORT).show();
                                    loggedIn();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable throwable) {
                                loginFailure();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable throwable) {
                    loginFailure();
                }
            });
        });
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        }
    }

    private void loginFailure() {
        Toast.makeText(MainActivity.this, "Nie można zalogować", Toast.LENGTH_SHORT).show();
    }

    private void loggedIn() {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
    }

}