package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileTeacher extends AppCompatActivity {

    TextView name,teacherID,phone,address,roomNo,graduation,masters,phd;
    TextInputLayout designationLayout;
    AutoCompleteTextView designationTextView;
    String designation,dept,preTeacherId;
    Uri imageUri;
    Button update;
    CircleImageView profilePic;
    DatabaseReference profilePics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_teacher);

        name = findViewById(R.id.Name);
        teacherID = findViewById(R.id.TeacherId);
        phone = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.Address);
        roomNo = findViewById(R.id.roomNO);
        graduation = findViewById(R.id.graduation);
        masters = findViewById(R.id.masters);
        phd = findViewById(R.id.phd);
        update = findViewById(R.id.update);
        profilePic = findViewById(R.id.imageViewProfilePic);
        profilePics = FirebaseDatabase.getInstance().getReference().child("ProfilePics");

        designationLayout = findViewById(R.id.designationDropDownLayout);
        designationTextView = findViewById(R.id.designationDropDownTextView);

        String[] allDesignations = {"Professor","Associate Professor","Assistant Professor","Lecturer"};
        ArrayAdapter<String> designationAdapter = new ArrayAdapter<>(EditProfileTeacher.this,R.layout.items_list,allDesignations);
        designationTextView.setAdapter(designationAdapter);

        designationTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                designation = designationAdapter.getItem(i);
                Toast.makeText(EditProfileTeacher.this, designationAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        name.setText(ProfileTeacher.Name);
        phone.setText(ProfileTeacher.Phone);
        teacherID.setText(ProfileTeacher.ID);
        designationTextView.setText(ProfileTeacher.Designation,false);
        dept = ProfileTeacher.Dept;
        preTeacherId = ProfileTeacher.ID;

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
                Toast.makeText(EditProfileTeacher.this, "Your Profile is Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileTeacher.this,ProfileTeacher.class));
            }
        });
    }

    public  void Update_Profile(){
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userTable,teacherTable;
        userTable = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
        teacherTable = FirebaseDatabase.getInstance().getReference().child("Teachers").child(dept);
        Toast.makeText(this, preTeacherId, Toast.LENGTH_SHORT).show();
        teacherTable.child(preTeacherId).removeValue();
        String Name,TeacherId,Phone,Address,RoomNo,Graduation,Masters,PhD;
        Name = name.getText().toString();
        TeacherId = teacherID.getText().toString();
        Phone = phone.getText().toString();
        Address = address.getText().toString();
        RoomNo = roomNo.getText().toString();
        Graduation = graduation.getText().toString();
        Masters = masters.getText().toString();
        PhD = phd.getText().toString();

        userTable.child("name").setValue(Name);
        userTable.child("id").setValue(TeacherId);
        userTable.child("phone").setValue(Phone);
        userTable.child("address").setValue(Address);
        userTable.child("roomNo").setValue(RoomNo);
        userTable.child("education").setValue(Graduation+Masters+PhD);
        userTable.child("designation").setValue(designation);

        teacherTable.child(TeacherId).child("name").setValue(Name);
        teacherTable.child(TeacherId).child("id").setValue(TeacherId);
        teacherTable.child(TeacherId).child("phone").setValue(Phone);
        teacherTable.child(TeacherId).child("address").setValue(Address);
        teacherTable.child(TeacherId).child("roomNo").setValue(RoomNo);
        teacherTable.child(TeacherId).child("education").setValue(Graduation+Masters+PhD);
        teacherTable.child(TeacherId).child("designation").setValue(designation);

        uploadImage();

    }

    void selectImage(){
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfileTeacher.this)
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
                Toast.makeText(EditProfileTeacher.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
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