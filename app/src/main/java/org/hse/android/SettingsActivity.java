package org.hse.android;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private static final int REQUEST_PICTURE_CAPTURE = 1;
    private static String pictureFilePath = "";
    private static String tmpPictureFilePath = "";

    private PreferenceManager preferenceManager;

    ImageView avatar;

    private final View.OnClickListener capture = view -> {
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            sendTakePictureIntent();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.preferenceManager = new PreferenceManager(this);
        avatar = findViewById(R.id.avatar_iv);

        Button captureButton = findViewById(R.id.take_photo);
        captureButton.setOnClickListener(capture);
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            captureButton.setEnabled(false);
        }

        Button saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(v -> {
            saveSettings();
        });

        pictureFilePath = getPicturePath();

        // load avatar if it exists
        File img = new File(pictureFilePath);
        if (img.exists()) avatar.setImageURI(Uri.fromFile(img));
    }

    private void saveSettings() {
        TextView nameTextView = findViewById(R.id.name);
        this.preferenceManager.saveValue("name", (String)nameTextView.getText());
    }

    private void sendTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);

            File pictureFile;
            try {
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "org.hse.android.provider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }
        }
    }

    private String generatePicturePath() {
        String pictureFile = "avatar";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String path = storageDir.getAbsolutePath() + "/" + pictureFile + ".jpg";

        this.preferenceManager.saveValue("avatar_path", path);

        return path;
    }

    private String getPicturePath() {
        return this.preferenceManager.getValue("avatar_path", generatePicturePath());
    }

    private File getPictureFile() throws IOException {
        File img = new File(getPicturePath());
        if (!img.exists()) {
            img.createNewFile();
        }
//        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        pictureFilePath = img.getAbsolutePath();
        return img;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(getPicturePath());
            if (imgFile.exists()) {
                avatar.setImageURI(Uri.fromFile(imgFile));
                avatar.invalidate();
            }
        }
    }
}

