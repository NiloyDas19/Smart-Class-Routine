package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileStudent extends AppCompatActivity {

    TextView name, phone, presentAddress,permanentAddress, fatherName,motherName;
    Button update;
    String Name,Phone,ID,dept;
    CircleImageView profilePic;
    Uri imageUri;
    DatabaseReference profilePics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_student);
        name = findViewById(R.id.Name);
        phone = findViewById(R.id.phoneNumber);
        presentAddress = findViewById(R.id.presentAddress);
        permanentAddress = findViewById(R.id.permanentAddress);
        fatherName = findViewById(R.id.fatherName);
        motherName = findViewById(R.id.motherName);
        update = findViewById(R.id.update);
        profilePic = findViewById(R.id.imageViewProfilePic);

        Name = ProfileStudent.Name;
        Phone = ProfileStudent.Phone;
        ID = ProfileStudent.ID;
        dept = ProfileStudent.Dept;

        name.setText(Name);
        phone.setText(Phone);

        profilePics = FirebaseDatabase.getInstance().getReference().child("ProfilePics");

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_Profile();
                Toast.makeText(EditProfileStudent.this, "Your Profile is Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileStudent.this, ProfileStudent.class));
            }
        });
    }

    public void Update_Profile() {
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userTable, studentTable;
        userTable = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
        studentTable = FirebaseDatabase.getInstance().getReference().child("Students").child(dept).child(ID);

        String Name, Phone, PresentAddress, PermanentAddress, FatherName,MotherName;
        Name = name.getText().toString();
        Phone = phone.getText().toString();
        FatherName = fatherName.getText().toString();
        MotherName = motherName.getText().toString();
        PresentAddress = presentAddress.getText().toString();
        PermanentAddress = permanentAddress.getText().toString();

        userTable.child("name").setValue(Name);
        userTable.child("phone").setValue(Phone);
        userTable.child("fatherName").setValue(FatherName);
        userTable.child("motherName").setValue(MotherName);
        userTable.child("presentAddress").setValue(PresentAddress);
        userTable.child("permanentAddress").setValue(PermanentAddress);

        studentTable.child("name").setValue(Name);
        studentTable.child("phone").setValue(Phone);
        studentTable.child("fatherName").setValue(FatherName);
        studentTable.child("motherName").setValue(MotherName);
        studentTable.child("presentAddress").setValue(PresentAddress);
        studentTable.child("permanentAddress").setValue(PermanentAddress);

        uploadImage();
    }

    void selectImage(){
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfileStudent.this)
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

    void uploadImage() {
        String currentUser =FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("ProfilePics").child(currentUser);
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = filePath.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileStudent.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
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
                            String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            profilePics.child(currentUser).child("url").setValue(imageUrl);
                            finish();
                        }
                    });
                }
            }
        });
    }
}