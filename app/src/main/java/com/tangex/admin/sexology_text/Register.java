package com.tangex.admin.sexology_text;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Register extends AppCompatActivity {
    private static final String TAG = "dangky";
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private user_db thongtinnguoidung = new user_db();

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dang_ki(thongtinnguoidung);

    }


    public void dang_ki(final user_db user) {

        final EditText edtName = findViewById(R.id.tendangnhap);

        final EditText edtPw = findViewById(R.id.matkhau);
        final EditText edtConfirmPw = findViewById(R.id.xacnhanmk);

        final EditText ngaysinh = findViewById(R.id.ngaysinh);
        final Button btnSignup = findViewById(R.id.xacnhan);
        final Button huydk = findViewById(R.id.huydk);
        final RadioGroup radioGroupLoai = findViewById(R.id.gt);
        final RadioButton radioBtnNam = findViewById(R.id.gtnam);
        final RadioButton radiobBtnNu = findViewById(R.id.gtnu);


        ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate(ngaysinh);
            }
        });

        huydk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtName.getText().toString();

                String pw = edtPw.getText().toString();
                String confirm_pw = edtConfirmPw.getText().toString();

                String ngaysinh_db = ngaysinh.getText().toString();

                if (name.equals("")) {
                    edtName.setError("Tên đăng nhập không được trống");
                } else if (pw.equals("")) {
                    edtPw.setError("Mật khẩu không được trống");
                } else if (!name.matches("[A-Za-z0-9]+")) {
                    edtName.setError("only alphabet or number allowed");
                } else if (name.length() < 6) {
                    edtName.setError("Tên đăng nhập ít nhất 6 kí tự");
                } else if (pw.length() < 6) {
                    edtPw.setError("Mật khẩu ít nhất 6 kí tự");
                } else if ((!ngaysinh_db.equals("")) && checkdate(ngaysinh_db)) {
                    if (checkConfirmPw(pw, confirm_pw)) {
                        if (radioGroupLoai.getCheckedRadioButtonId() != -1) {
                            if (radioBtnNam.isChecked()) {
                                int loai = 1;
                                int tmpgioitinh = 1;
                                user.setGioitinh(tmpgioitinh);
                                user.setLoai(loai);
                                user.setUserName(name);
                                user.setPw(getMd5Hash(pw));
                                user.setImage("https://firebasestorage.googleapis.com/v0/b/sexology-test.appspot.com/o/profile_img%2Fdownload.png?alt=media&token=489ac3c1-34f7-4e6e-8e5c-c2e40deb8113");
                                user.setNgaysinh(ngaysinh_db);
                                checkIfUserIsExist(user);


                            } else if (radiobBtnNu.isChecked()) {
                                int loai = 1;
                                int tmpgioitinh = 2;
                                user.setGioitinh(tmpgioitinh);
                                user.setLoai(loai);
                                user.setUserName(name);
                                user.setPw(getMd5Hash(pw));
                                user.setImage("https://firebasestorage.googleapis.com/v0/b/sexology-test.appspot.com/o/profile_img%2Fdownload.png?alt=media&token=489ac3c1-34f7-4e6e-8e5c-c2e40deb8113");
                                user.setNgaysinh(ngaysinh_db);
//                                user.setNgaythongbao("");
                                checkIfUserIsExist(user);
                            } else {
                                int loai = 1;
                                int tmpgioitinh = 3;
                                user.setGioitinh(tmpgioitinh);
                                user.setLoai(loai);
                                user.setUserName(name);
                                user.setPw(getMd5Hash(pw));
                                user.setImage("https://firebasestorage.googleapis.com/v0/b/sexology-test.appspot.com/o/profile_img%2Fdownload.png?alt=media&token=489ac3c1-34f7-4e6e-8e5c-c2e40deb8113");
                                user.setNgaysinh(ngaysinh_db);
                                checkIfUserIsExist(user);
                            }
                        } else {
                            Toast.makeText(Register.this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        edtPw.setText(null);
                        edtConfirmPw.setText(null);
                        Toast.makeText(Register.this, "Xác nhận mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ngaysinh.setError("ngày sinh còn thiếu " + "\n" +
                            "dd/mm/yyyy - Ví dụ: 01/01/2001");
//                    dialog.dismiss();
                }
            }
        });
    }

    public void pushDATAtoDB(user_db user) {
        CountDownTimer CDT;
        int i = 5;
//        String userName= user.getUserName();
//        String pw = user.getPw();
//        int loai = user.getLoai();

        String keyId = FirebaseDatabase.getInstance().getReference().push().getKey();
        FirebaseDatabase.getInstance().getReference().child("User").child(keyId).setValue(user);;

        dialog = new ProgressDialog(Register.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgress(i);
        dialog.show();
        CDT = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                dialog.dismiss();
                dialog.setMessage("Đăng ký thành công");
                Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(Register.this, Login.class);
//        startActivity(intent);
                finish();
            }
        }.start();
//        database.child("User").push().setValue(user);


    }

    public boolean checkIfEdtOK(String tendangnhap, EditText edtuserName, String pw, EditText edtPw, String confirm_pw, EditText confirm) {
        boolean result;
        if (!tendangnhap.equals("") && !pw.equals("") && !confirm_pw.equals("")) {
            result = true;
        } else {
            Toast.makeText(Register.this, "Điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            result = false;
        }

        return result;
    }


    public boolean checkConfirmPw(String pw, String confirm_pw) {
        boolean result;
        if (pw.equals(confirm_pw)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public void checkIfUserIsExist(final user_db user) {
        final String username = user.getUserName();


//        final String pw = user.getPw();
        database.child("User").orderByChild("userName").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(Register.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    pushDATAtoDB(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void chooseDate(final EditText ngaysinh) {
        final Calendar calendar = Calendar.getInstance();
        final int ngay = calendar.get(Calendar.DATE);
        final int thang = calendar.get(Calendar.MONTH);
        final int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ngaysinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private boolean checkdate(String ngaysinh) {
        try {
            new SimpleDateFormat("dd/mm/yyyy").parse(ngaysinh);
            String resuft[] = ngaysinh.split("/");
            int ngay = Integer.parseInt(resuft[0]);
            int thang = Integer.parseInt(resuft[1]);
            if ((ngay <= 31) && (thang <= 12)) {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
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
}
