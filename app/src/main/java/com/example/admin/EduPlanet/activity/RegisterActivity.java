package com.example.admin.EduPlanet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.admin.EduPlanet.Model.UserModel;
import com.example.admin.EduPlanet.R;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterActivity extends Activity{

    private EditText et_username;
    private EditText et_password;
    private EditText et_confirmpassword;
    private Button btn_register;
    private ImageView avatar;

    private File file;
    private String path;
    private String avatarurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        initview();
    }

    private void initview() {
        et_username = findViewById(R.id.r_username_et);
        et_password = findViewById(R.id.r_password_et);
        et_confirmpassword = findViewById(R.id.passwordconfirm_et);
        avatar = findViewById(R.id.r_iv_avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 42);
            }
        });

        btn_register = findViewById(R.id.r_register_btn);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel.getInstance().register(et_username.getText().toString(), et_password.getText().toString(), et_confirmpassword.getText().toString(), avatarurl, new LogInListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42 && resultCode == RESULT_OK) {
            try {
                avatar.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
                Uri uri = data.getData();
                path = getPath(this, uri);
                file = new File(path);
                final BmobFile avatar = new BmobFile(file);
                avatar.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        avatarurl = avatar.getUrl();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
