package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileAdmin extends AppCompatActivity {

    EditText name,phone,presentAddress,permanentAddress;
    Button update;
    ImageView profilePic;
    String Name,Dept,ID,Phone;
    Uri imageUri;
    DatabaseReference userTable, adminTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_admin);

        name = findViewById(R.id.Name);
        phone = findViewById(R.id.phoneNumber);
        presentAddress = findViewById(R.id.presentAddress);
        permanentAddress = findViewById(R.id.permanentAddress);
        update = findViewById(R.id.update);
        profilePic = findViewById(R.id.imageViewProfilePic);

        Name = ProfileAdmin.Name;
        Phone = ProfileAdmin.Phone;
        ID = ProfileAdmin.ID;
        Dept = ProfileAdmin.Dept;

        name.setText(Name);
        phone.setText(Phone);

        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userTable = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
        adminTable = FirebaseDatabase.getInstance().getReference().child("Admins").child(Dept).child(ID);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
                Toast.makeText(EditProfileAdmin.this, "Your profile is updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileAdmin.this,ProfileAdmin.class));
            }
        });
    }

    void updateProfile(){
        String Name, Phone, PresentAddress, PermanentAddress;
        Name = name.getText().toString();
        Phone = phone.getText().toString();
        PresentAddress = presentAddress.getText().toString();
        PermanentAddress = permanentAddress.getText().toString();

        userTable.child("name").setValue(Name);
        userTable.child("phone").setValue(Phone);
        userTable.child("presentAddress").setValue(PresentAddress);
        userTable.child("permanentAddress").setValue(PermanentAddress);

        adminTable.child("name").setValue(Name);
        adminTable.child("phone").setValue(Phone);
        adminTable.child("presentAddress").setValue(PresentAddress);
        adminTable.child("permanentAddress").setValue(PermanentAddress);

        uploadImage();
    }

    void selectImage(){
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfileAdmin.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
//                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 100 && data != null && data.getData() != null){
//            imageUri = data.getData();
//            profilePic.setImageURI(imageUri);
//        }
        imageUri = data.getData();
        profilePic.setImageURI(imageUri);
    }

    void uploadImage(){
        final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("ProfilePics").child(ID);
        Bitmap bitmap = null;

        try {
            bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),imageUri);
        }catch (IOException e){
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        byte[] data=byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = filePath.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileAdmin.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            userTable.child("profilePicUrl").setValue(imageUrl);
                            adminTable.child("profilePicUrl").setValue(imageUrl);
                            finish();
                        }
                    });
                }
            }
        });
    }

}