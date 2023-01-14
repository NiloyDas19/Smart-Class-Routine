package com.example.smartclassroutinefinalinterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadNoticeTeacher extends AppCompatActivity {
    Toolbar uploadNoticeToolbar;
    EditText description;
    Button uploadFile,uploadImage;

    DatabaseReference fileDatabase;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice_teacher);

        uploadNoticeToolbar = findViewById(R.id.uploadNoticeToolbar);
        description = findViewById(R.id.writeNotice);
        uploadFile = findViewById(R.id.btnUpload);
        uploadImage = findViewById(R.id.btnUploadImage);
        setSupportActionBar(uploadNoticeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fileDatabase = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFile();
                //startActivity(new Intent(getApplicationContext(),TeacherPannel.class));
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFile();
                //startActivity(new Intent(getApplicationContext(),TeacherPannel.class));
            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public  void selectFile(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uploadFile(data.getData());
        }else {
            uploadImageFile(data.getData());
        }
    }

    private void uploadFile(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("Notice/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());

                Uri url = uriTask.getResult();

                FileClass fileClass = new FileClass(description.getText().toString(),url.toString());

                fileDatabase.child("Notices").child(fileDatabase.push().getKey()).setValue(fileClass);

                Toast.makeText(UploadNoticeTeacher.this, "File Uploaded!", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),TeacherPannel.class));
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded:"+(int)progress+"%");

            }
        });

    }

    public  void selectImageFile(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,200);
        ImagePicker.with(UploadNoticeTeacher.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }


    private void uploadImageFile(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("Notice/"+System.currentTimeMillis()+".jpg");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());

                Uri url = uriTask.getResult();

                FileClass fileClass = new FileClass(description.getText().toString(),url.toString());

                fileDatabase.child("Notices").child(fileDatabase.push().getKey()).setValue(fileClass);

                Toast.makeText(UploadNoticeTeacher.this, "File Uploaded!", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),TeacherPannel.class));
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded:"+(int)progress+"%");

            }
        });

    }


}