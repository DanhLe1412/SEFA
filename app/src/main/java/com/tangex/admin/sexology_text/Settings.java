package com.tangex.admin.sexology_text;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Math.random;

public class Settings extends AppCompatActivity {

    Button mImgBtn;
    TextView name;
    String username_set;
    DatabaseReference mdataref,cd ;
    Query mdataQue;
    CircleImageView avt;


    private static final int GALLERY_PICK = 1;


    private StorageReference mImgStorage;

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        anhxa();
        Intent fr = getIntent();
        final String iduser = fr.getStringExtra("iduser");
        mImgStorage = FirebaseStorage.getInstance().getReference();
        mdataref = FirebaseDatabase.getInstance().getReference().child("User").child(iduser);
        cd = FirebaseDatabase.getInstance().getReference();

        mdataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name.setText(dataSnapshot.child("userName").getValue().toString());
                Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(avt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Settings.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });

        mImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                //them code moi
//                galleryintent.setAction(android.content.Intent.ACTION_VIEW);

                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, GALLERY_PICK);

//                galleryintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(galleryintent);

//                galleryintent.setAction(android.content.Intent.ACTION_GET_CONTENT);
//
//                startActivityForResult(Intent.createChooser(galleryintent, "Select image"), GALLERY_PICK);

//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(Settings.this);
            }
        });
    }

    private void anhxa() {
        mImgBtn = (Button) findViewById(R.id.setting_img_btn);
        name = (TextView) findViewById(R.id.setting_name);
        avt = (CircleImageView) findViewById(R.id.setting_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri).setAspectRatio(1, 1).start(Settings.this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){

                mProgressDialog = new ProgressDialog(Settings.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Please wait while we upload and process the image.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                Uri resultUri = result.getUri();
                Intent fu = getIntent();
                final String user_id = fu.getStringExtra("iduser");

                //tao ref img
                final StorageReference filepath = mImgStorage.child("profile_img").child(user_id + ".jpg");

                final StorageReference ref = filepath;
                UploadTask uploadTask;
                uploadTask = ref.putFile(resultUri);
                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downUrl = task.getResult();
                            final String Url = downUrl.toString();

                            cd.child("User").child(user_id).child("image").setValue(Url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(Settings.this, "ok", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Settings.this, "not ok", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });


//                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            String downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
//
//                            mdataref.child("User").child(user_id).child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        mProgressDialog.dismiss();
//                                        Toast.makeText(Settings.this, "success", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//
//                        } else {
//                            Toast.makeText(Settings.this, "Error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
            }
        }
    }
}