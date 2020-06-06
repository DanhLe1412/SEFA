package com.tangex.admin.sexology_text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class Forum_push_question extends AppCompatActivity {

    private disscusion_db disscusionDb = new disscusion_db();

    private DatabaseReference ref;

    private Calendar calendar;

    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_push_question);

        final EditText edt_tieude = (EditText) findViewById(R.id.push_Tieude);
        final EditText edt_noidung = (EditText) findViewById(R.id.push_Noidung);
        final RadioButton radio_Nam = (RadioButton) findViewById(R.id.dissc_radio_nam);
        final RadioButton radio_Nu = (RadioButton) findViewById(R.id.dissc_radio_nu);
        final RadioButton radio_lgbt = (RadioButton) findViewById(R.id.dissc_radio_lgbt);
        final Button dang = (Button) findViewById(R.id.diss_dang);
        final RadioGroup radioGroup_push_diss = (RadioGroup) findViewById(R.id.radioGroup_push_for);

        calendar = Calendar.getInstance();
        date=new Date();
        ref = FirebaseDatabase.getInstance().getReference();

        disscusionDb.setIduserDang(getIntent().getStringExtra("iduser"));

        dang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_tieude.equals("")) {
                    disscusionDb.setTieude(edt_tieude.getText().toString());
                    if (!edt_noidung.equals("")) {
                        disscusionDb.setNoidung(edt_noidung.getText().toString());
                        if (radioGroup_push_diss.getCheckedRadioButtonId() != -1 ){
                            if (radio_Nam.isChecked()) {
                                disscusionDb.setNam(1);
                                date.setTime(calendar.getTimeInMillis());
                                disscusionDb.setThoigianDang(date.getTime());
                                final String idBaiDang = ref.child("Forum").push().getKey();
                                disscusionDb.setIdBaiDang(idBaiDang);
                                ref.child("Forum").child(idBaiDang).setValue(disscusionDb);
                                finish();
                            } else if (radio_Nu.isChecked()) {
                                disscusionDb.setNu(1);
                                date.setTime(calendar.getTimeInMillis());
                                disscusionDb.setThoigianDang(date.getTime());
                                final String idBaiDang = ref.child("Forum").push().getKey();
                                disscusionDb.setIdBaiDang(idBaiDang);
                                ref.child("Forum").child(idBaiDang).setValue(disscusionDb);
                                finish();
                            } else if (radio_lgbt.isChecked()) {
                                disscusionDb.setLgbt(1);
                                date.setTime(calendar.getTimeInMillis());
                                disscusionDb.setThoigianDang(date.getTime());
                                final String idBaiDang = ref.child("Forum").push().getKey();
                                disscusionDb.setIdBaiDang(idBaiDang);
                                ref.child("Forum").child(idBaiDang).setValue(disscusionDb);
                                finish();
                            }
                        } else {
                            Toast.makeText(Forum_push_question.this, "điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }

                    } else edt_noidung.setError("Nhập nội dung");
                } else {
                    edt_tieude.setError("Nhập tiêu đề");
                }
            }
        });
    }
}
