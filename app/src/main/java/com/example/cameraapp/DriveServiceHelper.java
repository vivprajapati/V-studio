package com.example.cameraapp;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {

    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    Drive mDriveService;

    public DriveServiceHelper(Drive mDriveService) {
        this.mDriveService = mDriveService;
    }

    public Task<String> createFileJpeg(String filePath) {
        return Tasks.call(mExecutor, () -> {
            File fileMetaData = new File();
            fileMetaData.setName(String.valueOf(System.currentTimeMillis()));
            java.io.File file = new java.io.File(filePath);
            FileContent mediaContent = new FileContent("image/jpeg", file);

            File myFile = null;
            try {
                myFile = mDriveService.files().create(fileMetaData, mediaContent).execute();

            } catch (Exception e) {
                Log.d("see_uri", "createFileJpeg: "+ e.getMessage());
            }

            if (myFile == null) {
                throw new IOException("Null request while creating file request");
            }
            return myFile.getId();
        });
    }



}


