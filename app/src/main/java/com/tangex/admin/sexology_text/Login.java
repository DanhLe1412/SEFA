package com.tangex.admin.sexology_text;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

public class Login extends AppCompatActivity {
    private Button dk, dangnhap;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String idUser;
    private SharedPreferences sharedPreferences;
    private ProgressDialog dialog;
    private CountDownTimer CDT;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText edttendangnhap = findViewById(R.id.edtusername);
        final EditText edtPw = findViewById(R.id.edtpw);
        Button Btnsinup = findViewById(R.id.dk);
        Button btnSignin = findViewById(R.id.dangnhap);


        final Intent intent = new Intent(Login.this, Home.class);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edttendangnhap.getText().toString();
                String pw = edtPw.getText().toString();
                if (isNetworkAvailable()) {
                    if (checkInputIsOk(username, pw)) {
                        database.child("User")
                                .orderByChild("userName")
                                .equalTo(edttendangnhap.getText().toString())
                                .addValueEventListener(new ValueEventListener() {
                                    /**
                                     * Khi user nhap userName va pw
                                     * Dung userName de xac dinh iduser, dong thoi lay du lieu user
                                     */
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                idUser = childSnapshot.getKey();
                                                /**
                                                 * So sanh pw dang nhap va pw cua database
                                                 * Chuyen sang Home
                                                 */
                                                database.child("User").child(idUser)
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Log.d("pw",getMd5Hash(dataSnapshot.child("pw").getValue().toString()));
                                                                if (Objects.equals(dataSnapshot.child("pw").getValue().toString(),getMd5Hash(edtPw.getText().toString())
                                                                        )) {
                                                                    final int gt = dataSnapshot.child("gioitinh").getValue(Integer.class);
                                                                    intent.putExtra("gt",gt);
                                                                    intent.putExtra("iduser", idUser);
//                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(Login.this,
                                                                            "Tên đăng nhập hoặc mật khẩu bị sai!",
                                                                            Toast.LENGTH_SHORT).show();
                                                                    edtPw.setText(null);
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                            }
                                        } else {
                                            dialog = new ProgressDialog(Login.this);
                                            dialog.setCanceledOnTouchOutside(false);
                                            dialog.setProgress(5);
                                            dialog.show();
                                            CDT = new CountDownTimer(5000, 1000) {
                                                @Override
                                                public void onTick(long l) {

                                                }

                                                @Override
                                                public void onFinish() {
                                                    dialog.setMessage("Tài khoản không tồn tại");
                                                    Toast.makeText(Login.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            }.start();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    } else {
                        Toast.makeText(Login.this, "Điền đầy đủ thông tin!",
                                Toast.LENGTH_SHORT).show();
                        edtPw.setText(null);
                        edttendangnhap.setText(null);
                        edtPw.setHintTextColor(Color.parseColor("#FF0000"));
                        edttendangnhap.setHintTextColor(Color.parseColor("#FF0000"));
                    }


                } else Toast.makeText(Login.this, "Hãy kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Chuyen sang man hinh dang ki neu chua co tai khoan
         */
        Btnsinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean checkInputIsOk(String phone, String pw) {
        if ((phone != "") && (pw != "")) {
            return true;
        } else {
            return false;
        }
    }

    private static String getMd5Hash(String  input){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);
            while (md5.length()<32){md5= "0" + md5;}
            return md5;
        }catch (NoSuchAlgorithmException e){
            Log.e("MD5",e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("place_id", Context.MODE_MULTI_PROCESS);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        String login = sharedPreferences.getString("LOGIN", null);

        if ((login != null) && (login != "")) {
            final Intent i = new Intent(Login.this, Home.class);
            i.putExtra("iduser", login);
            startActivity(i);
        }
    }
}
