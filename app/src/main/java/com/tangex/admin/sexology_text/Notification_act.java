package com.tangex.admin.sexology_text;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Notification_act extends AppCompatActivity {
    private SharedPreferences fisrt_open;
    private Boolean first_open_b;
    private Notification_Obj_Handle ntc_obj;


    private DatabaseReference ref_user;

    private String iduser;
    private PendingIntent pendingIntent;

    private String ngaydudoan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        iduser = getIntent().getStringExtra("iduser");


        ref_user = FirebaseDatabase.getInstance().getReference().child("User").child(iduser);


        fisrt_open = getSharedPreferences("place_first", Context.MODE_MULTI_PROCESS);
        fisrt_open = PreferenceManager.getDefaultSharedPreferences(Notification_act.this);
        first_open_b = fisrt_open.getBoolean("FIRST", false);
        final int i = getIntent().getIntExtra("vitringay", 0);
        if (i > 0) {
            setContentView(R.layout.activity_notification_update_date);
            final EditText edt_update_date = findViewById(R.id.edt_update_date);
            final Button huy_update = findViewById(R.id.huy_update);
            final Button update_date_button = findViewById(R.id.update_date_button);

            edt_update_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseDate(edt_update_date);
                }
            });
            update_date_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ref_user.child("user_not_inf").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int ngay = dataSnapshot.child("soNgayDeu").getValue(Integer.class);
                            final String ngayGanday;
                            if (checkdate(edt_update_date.getText().toString())) {
                                set_notification(edt_update_date.getText().toString(), ngay);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });


        } else if (first_open_b == true) {
            setContentView(R.layout.notification_questions);
            findViewById(R.id.notifcation_question_con).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final RadioButton yes_button = findViewById(R.id.radioButton_yes_1);
                    final RadioButton no_button = findViewById(R.id.radioButton_no_1);
                    if (yes_button.isChecked()) {
                        setContentView(R.layout.notification_question_yes);
                        final RadioButton yes_fl_day = findViewById(R.id.radioButton_fowlowday_yes);
                        final RadioButton no_fl_day = findViewById(R.id.radioButton_fowlowday_no);
                        final RadioButton yes_kndeu = findViewById(R.id.radioButton_kndeu_yes);
                        final RadioButton no_kndeu = findViewById(R.id.radioButton_kndeu_no);
                        final RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
                        final EditText edt_kndeu = findViewById(R.id.editText_kndeu);
                        final EditText date_last_month = findViewById(R.id.edt_date_lastmonth);
                        final CheckBox NDCT = findViewById(R.id.checkBox1);
                        final CheckBox DBD = findViewById(R.id.checkBox2);
                        final CheckBox DL = findViewById(R.id.checkBox3);
                        final CheckBox NNM = findViewById(R.id.checkBox4);
                        final Button acept = findViewById(R.id.button_final_acept_not);

                        ntc_obj = new Notification_Obj_Handle();


                        date_last_month.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                chooseDate(date_last_month);
                            }
                        });

                        yes_kndeu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                relativeLayout.setVisibility(View.VISIBLE);
                            }
                        });
                        no_kndeu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                relativeLayout.setVisibility(View.INVISIBLE);
                            }
                        });
                        acept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ntc_obj.setNgayGanDay(date_last_month.getText().toString());
                                ///----THEO DÕI NGÀY HÀNH KINH-----

                                if (yes_fl_day.isChecked()) {
                                    ntc_obj.setTheoDoi(0);
                                } else if (no_fl_day.isChecked()) {
                                    ntc_obj.setTheoDoi(1);
                                }

                                ///---KINH NGUYỆT ĐỀU-----

                                if (yes_kndeu.isChecked()) {
                                    ntc_obj.setKinhNguyetCoDeu(0);
                                    if (!edt_kndeu.getText().toString().equals("")) {
                                        final int songay = Integer.parseInt(edt_kndeu.getText().toString());
//

                                        if ((songay >= 22) && (songay <= 32)) {
                                            ntc_obj.setSoNgayDeu(songay);


                                            ///----DẤU HIỆU TRƯỚC NGÀY HÀNH KINH----

                                            if (NDCT.isChecked()) {
                                                ntc_obj.setNuiDoiCangTuc(0);
                                            } else {
                                                ntc_obj.setNuiDoiCangTuc(1);
                                            }
                                            if (DBD.isChecked()) {
                                                ntc_obj.setDauBungDuoi(0);
                                            } else {
                                                ntc_obj.setDauBungDuoi(1);
                                            }
                                            if (DL.isChecked()) {
                                                ntc_obj.setDauLung(0);
                                            } else {
                                                ntc_obj.setDauLung(1);
                                            }
                                            if (NNM.isChecked()) {
                                                ntc_obj.setNoiNhieuMun(0);
                                            } else {
                                                ntc_obj.setNoiNhieuMun(1);
                                            }


                                            // ----- CHECK ĐỦ THỨ-----------
                                            if (checkdate(date_last_month.getText().toString())) {
                                                if (yes_fl_day.isChecked() || no_fl_day.isChecked()) {
                                                    if (yes_kndeu.isChecked() || no_kndeu.isChecked()) {
                                                        if (NDCT.isChecked() || DBD.isChecked() || DL.isChecked() || NNM.isChecked()) {

                                                            ref_user.child("user_not_inf").setValue(ntc_obj);


//                                                            SharedPreferences.Editor editor = fisrt_open.edit();
//                                                            editor.putBoolean("FIRST", false);
//                                                            editor.commit();
//                                                            editor.apply();


                                                            set_notification(ntc_obj.getNgayGanDay(), ntc_obj.getSoNgayDeu());

                                                            final Intent toShow = new Intent(Notification_act.this, Notification_ShowView.class);
                                                            toShow.putExtra("iduser", getIntent().getStringExtra("iduser"));
                                                            startActivity(toShow);

                                                            Log.d("ngayganday", ntc_obj.getNgayGanDay());
                                                            Log.d("ngaydudoan", ngaydudoan + "   112121");
                                                        } else
                                                            Toast.makeText(Notification_act.this, "Thông tin còn thiếu", Toast.LENGTH_SHORT).show();
                                                    } else
                                                        Toast.makeText(Notification_act.this, "Thông tin còn thiếu", Toast.LENGTH_SHORT).show();
                                                } else
                                                    Toast.makeText(Notification_act.this, "Thông tin còn thiếu", Toast.LENGTH_SHORT).show();


                                            } else
                                                date_last_month.setError("Nhập lại ngày gần đây");

                                        } else {
                                            edt_kndeu.setError("Số ngày dao động từ 28-32");
                                        }
                                    } else {
                                        edt_kndeu.setError("Số ngày không thể trống");
                                    }
                                } else if (no_kndeu.isChecked()) {
                                    ntc_obj.setKinhNguyetCoDeu(1);
                                    ntc_obj.setSoNgayDeu(0);


                                    ///----DẤU HIỆU TRƯỚC NGÀY HÀNH KINH----

                                    if (NDCT.isChecked()) {
                                        ntc_obj.setNuiDoiCangTuc(0);
                                    } else {
                                        ntc_obj.setNuiDoiCangTuc(1);
                                    }
                                    if (DBD.isChecked()) {
                                        ntc_obj.setDauBungDuoi(0);
                                    } else {
                                        ntc_obj.setDauBungDuoi(1);
                                    }
                                    if (DL.isChecked()) {
                                        ntc_obj.setDauLung(0);
                                    } else {
                                        ntc_obj.setDauLung(1);
                                    }
                                    if (NNM.isChecked()) {
                                        ntc_obj.setNoiNhieuMun(0);
                                    } else {
                                        ntc_obj.setNoiNhieuMun(1);
                                    }


                                    // ----- CHECK ĐỦ THỨ-----------
                                    if (checkdate(date_last_month.getText().toString())) {
                                        if (yes_fl_day.isChecked() || no_fl_day.isChecked()) {
                                            if (yes_kndeu.isChecked() || no_kndeu.isChecked()) {
                                                if (NDCT.isChecked() || DBD.isChecked() || DL.isChecked() || NNM.isChecked()) {

                                                    ref_user.child("user_not_inf").setValue(ntc_obj);
//                                                setContentView(R.layout.activity_notification_act);


//                                                    SharedPreferences.Editor editor = fisrt_open.edit();
//                                                    editor.putBoolean("FIRST", false);
//                                                    editor.commit();
//                                                    editor.apply();


                                                    set_notification(ntc_obj.getNgayGanDay(), ntc_obj.getSoNgayDeu());

                                                    final Intent toShow = new Intent(Notification_act.this, Notification_ShowView.class);
                                                    toShow.putExtra("iduser", getIntent().getStringExtra("iduser"));
                                                    startActivity(toShow);

                                                    Log.d("ngayganday", ntc_obj.getNgayGanDay());
                                                    Log.d("ngaydudoan", ngaydudoan + "   112121");
                                                } else
                                                    Toast.makeText(Notification_act.this, "Thông tin còn thiếu", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(Notification_act.this, "Thông tin còn thiếu", Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(Notification_act.this, "Thông tin còn thiếu", Toast.LENGTH_SHORT).show();


                                    } else
                                        date_last_month.setError("Nhập lại ngày gần đây");
                                }


                            }
                        });
                    } else if (no_button.isChecked()) {
                        setContentView(R.layout.notification_question_no);
                        findViewById(R.id.button_xacnhan).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final ProgressDialog dialog;
                                final CountDownTimer CDT;
                                dialog = new ProgressDialog(Notification_act.this);
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.setProgress(5);
                                dialog.show();
                                CDT = new CountDownTimer(5000, 1000) {
                                    @Override
                                    public void onTick(long l) {
                                        dialog.setMessage("Có thể bạn đã sắp đến kỳ hành kinh đầu tiên của mình. Hãy trang bị thêm cho mình nhiều kiến thức về kinh nguyệt nhé");
                                    }

                                    @Override
                                    public void onFinish() {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }.start();

                            }
                        });
                    }
                }
            });
        } else if (first_open_b == false) {
            final Intent toShow = new Intent(Notification_act.this, Notification_ShowView.class);
            toShow.putExtra("iduser", getIntent().getStringExtra("iduser"));
            startActivity(toShow);
        }

    }

    private Calendar getCalendar(String ngayGanday) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar_not = Calendar.getInstance();
        try {
            Date date = format.parse(ngayGanday);
            calendar_not.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar_not;
    }

    private void set_notification(String ngayGanday, int songay) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar_not = Calendar.getInstance();

        int ngayHK = 0;
        String result[] = ngayGanday.split("/");
        int thangTT = Integer.parseInt(result[1]);
        //   try {
//            Date date = format.parse(ngayGanday);
//            calendar_not.setTime(date);
        //----------------------
        if (ngayHK == 0) {
            if (songay == 0) {
                if (check_month(thangTT) == 0) {

                    ThongbaoTruoc(ngayGanday, 0, 31);
                    ThongbaoTrong(ngayGanday, 0, 31);
                    ThongbaoSau(ngayGanday, 0, 31);

                } else if (check_month(thangTT) == 1) {
                    ThongbaoTruoc(ngayGanday, 0, 30);
                    ThongbaoTrong(ngayGanday, 0, 30);
                    ThongbaoSau(ngayGanday, 0, 30);
                } else {
                    ThongbaoTruoc(ngayGanday, 0, 28);
                    ThongbaoTrong(ngayGanday, 0, 28);
                    ThongbaoSau(ngayGanday, 0, 28);
                }
            } else {
                if (check_month(thangTT) == 0) {

                    ThongbaoTruoc(ngayGanday, songay, 31);
                    ThongbaoTrong(ngayGanday, songay, 31);
                    ThongbaoSau(ngayGanday, songay, 31);

                } else if (check_month(thangTT) == 1) {
                    ThongbaoTruoc(ngayGanday, songay, 30);
                    ThongbaoTrong(ngayGanday, songay, 30);
                    ThongbaoSau(ngayGanday, songay, 30);
                } else {
                    ThongbaoTruoc(ngayGanday, songay, 28);
                    ThongbaoTrong(ngayGanday, songay, 28);
                    ThongbaoSau(ngayGanday, songay, 28);
                }
            }
        } else {
            String d = ngayHK + "";
            Toast.makeText(this, d, Toast.LENGTH_SHORT).show();
        }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    private void startAlarm(Calendar calendar, String noidung, int id, int vitringay) {
        Intent intent = new Intent(Notification_act.this, AlarmNotificationReceiver.class);
        intent.putExtra("noidung", noidung);
        intent.putExtra("vitringay", vitringay);
        intent.putExtra("iduser", iduser);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }


    private void chooseDate(final EditText ngay_nhap) {
        final Calendar calendar = Calendar.getInstance();
        final int ngay = calendar.get(Calendar.DATE);
        final int thang = calendar.get(Calendar.MONTH);
        final int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ngay_nhap.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private boolean checkdate(String eddt) {
        try {
            new SimpleDateFormat("dd/mm/yyyy").parse(eddt);
            String resuft[] = eddt.split("/");
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

    private int check_month(int thang) {
        HashSet<Integer> thang31 = new HashSet<>();
        thang31.add(1);
        thang31.add(3);
        thang31.add(5);
        thang31.add(7);
        thang31.add(8);
        thang31.add(10);
        thang31.add(12);

        HashSet<Integer> thang30 = new HashSet<>();
        thang30.add(4);
        thang30.add(6);
        thang30.add(9);
        thang30.add(11);

        if (thang31.contains(thang)) {
            return 0;
        } else if (thang30.contains(thang)) {
            return 1;
        } else return 2;
    }

    private String RandomNoidungThonbao(List<String> mangnoidung) {
        Random rd = new Random();
        String ketqua = mangnoidung.get(rd.nextInt(mangnoidung.size()));
        return ketqua;
    }

    private Calendar setCalendarChoNhieuThang(String ngayTT, int songay, int ngaycuathang, int vitringay) {
        Calendar calendar = Calendar.getInstance();


        String split_re[] = ngayTT.split("/");
        int ngayTT_ = Integer.parseInt(split_re[0]);
        int thangTT = Integer.parseInt(split_re[1]);
        int namTT = Integer.parseInt(split_re[2]);

        int re_thangTB;
        int re_namTb;

        String resuslt = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();

        if (songay == 0) {
            int re_ngayTB = ngayTT_ + vitringay;

            if (re_ngayTB < 0) {
                re_ngayTB = thangTT - re_ngayTB;
            } else if (re_ngayTB == 0) {
                re_ngayTB = thangTT;
            } else {
                re_thangTB = thangTT + 1;

                if (re_thangTB > 12) {
                    re_thangTB = 1;
                    re_namTb = namTT + 1;
                    resuslt = re_ngayTB + "/" + re_thangTB + "/" + re_namTb;

                    resuslt = resuslt + " 06:00";

                    try {
                        date = format.parse(resuslt);
                        calendar.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    re_namTb = namTT;
                    resuslt = re_ngayTB + "/" + re_thangTB + "/" + re_namTb;
                    resuslt = resuslt + " 06:00";

                    try {
                        date = format.parse(resuslt);
                        calendar.setTime(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            int re_ngayTB = ngayTT_ + songay - ngaycuathang + vitringay;
            if (re_ngayTB < 0) {
                re_ngayTB = thangTT - re_ngayTB;
            } else if (re_ngayTB == 0) {
                re_ngayTB = thangTT;
            } else {
                re_thangTB = thangTT + 1;

                if (re_thangTB > 12) {
                    re_thangTB = 1;
                    re_namTb = namTT + 1;

                        resuslt = re_ngayTB + "/" + re_thangTB + "/" + re_namTb;
                        resuslt = resuslt + " 06:00";
                        try {
                            date = format.parse(resuslt);
                            calendar.setTime(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                } else {
                    re_namTb = namTT;
                        resuslt = re_ngayTB + "/" + re_thangTB + "/" + re_namTb;
                        resuslt = resuslt + " 06:00";
                        try {
                            date = format.parse(resuslt);
                            calendar.setTime(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                }
            }
        }

        return calendar;
    }

//    private String ngayThongbao(int songay, int songaycuathang, int thang, int years){
//        int resultNgayTB;
//        int resultThangTB;
//        int resultNamTB;
//
//
//        return "";
//    }

    private void ThongbaoTruoc(final String ngayTT, final int songay, final int ngaycuathang) {
        DatabaseReference ref_truoc = FirebaseDatabase.getInstance().getReference();
        final List<String> MangNoiDung_truoc = new ArrayList<>();
        ref_truoc.child("Not_Content").child("Truoc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot abc : dataSnapshot.getChildren()) {
                    DB_Content_Notif dbContentNotif = abc.getValue(DB_Content_Notif.class);
                    MangNoiDung_truoc.add(dbContentNotif.getNoidung());
                }
                final int id_not = (int) System.currentTimeMillis();

                startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, -5), RandomNoidungThonbao(MangNoiDung_truoc), id_not, -5);

                startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, -4), RandomNoidungThonbao(MangNoiDung_truoc), id_not, -4);

                startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, -3), RandomNoidungThonbao(MangNoiDung_truoc), id_not, -3);

                startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, -2), RandomNoidungThonbao(MangNoiDung_truoc), id_not, -2);

                startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, -1), RandomNoidungThonbao(MangNoiDung_truoc), id_not, -1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void ThongbaoTrong(final String ngayTT, final int songay, final int ngaycuathang) {
        DatabaseReference ref_truoc = FirebaseDatabase.getInstance().getReference();
        final List<String> MangNoiDung_trong = new ArrayList<>();
        ref_truoc.child("Not_Content").child("Trong").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot abc : dataSnapshot.getChildren()) {
                    DB_Content_Notif dbContentNotif = abc.getValue(DB_Content_Notif.class);
                    MangNoiDung_trong.add(dbContentNotif.getNoidung());
                }
                final int id_not = (int) System.currentTimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, 0);

                ref_user.child("user_not_inf").child("ngayDuDoan").setValue(format.format(calendar.getTime()));
                Log.d("trong", calendar.getTime() + "");
                startAlarm(calendar, RandomNoidungThonbao(MangNoiDung_trong), id_not, 0);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ThongbaoSau(final String ngayTT, final int songay, final int ngaycuathang) {
        DatabaseReference ref_truoc = FirebaseDatabase.getInstance().getReference();
        final List<String> MangNoiDung_Sau = new ArrayList<>();
        ref_truoc.child("Not_Content").child("Sau").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot abc : dataSnapshot.getChildren()) {
                    DB_Content_Notif dbContentNotif = abc.getValue(DB_Content_Notif.class);
                    MangNoiDung_Sau.add(dbContentNotif.getNoidung());
                    final int id_not = (int) System.currentTimeMillis();

                    startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, 5), RandomNoidungThonbao(MangNoiDung_Sau), id_not, 5);

                    startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, 4), RandomNoidungThonbao(MangNoiDung_Sau), id_not, 4);

                    startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, 3), RandomNoidungThonbao(MangNoiDung_Sau), id_not, 3);

                    startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, 2), RandomNoidungThonbao(MangNoiDung_Sau), id_not, 2);

                    startAlarm(setCalendarChoNhieuThang(ngayTT, songay, ngaycuathang, 1), RandomNoidungThonbao(MangNoiDung_Sau), id_not, 1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}