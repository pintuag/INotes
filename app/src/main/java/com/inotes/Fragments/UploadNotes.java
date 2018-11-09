package com.inotes.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.inotes.Adapters.UploadImagesAdapter;
import com.inotes.Models.UploadUri;
import com.inotes.NavigationActivity;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class UploadNotes extends Fragment {

    ProgressBar progressBar;
    SessionManager manager;
    public static Button upload;
    TextView pdfname;
    RelativeLayout rv;
    int check=0;

    //firebase work;
    FirebaseStorage storage;
    FirebaseDatabase database;


    public Button camera, gallery,pdf;
    private final static int MY_PERMISSIONS_REQUEST_CODE_FOR_CAMERA = 1000;
    private final static int MY_PERMISSIONS_REQUEST_CODE_FOR_GALLERY = 1300;
    private final static int GALLERY = 2, CAMERA = 1,PDF=3;
    Uri ImageURI;
    Uri selectedImageuri;
    RecyclerView recyclerView2;
    UploadImagesAdapter madapter2;
    public int checkstorage =0 ;

    List<UploadUri> list = new ArrayList<>();
    List<UploadUri> pdflist = new ArrayList<>();
    ProgressDialog progressDialog;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.upload_notes,container,false);

        manager=new SessionManager();
        camera = (Button) view.findViewById(R.id.camera);
        gallery = (Button) view.findViewById(R.id.gallery);
        pdf = (Button) view.findViewById(R.id.pdf);
        upload = (Button) view.findViewById(R.id.upload);
        pdfname = (TextView)view.findViewById(R.id.pdffilename);
        rv=(RelativeLayout)view.findViewById(R.id.rv);

        //firebase
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView2.setItemViewCacheSize(20);
        recyclerView2.setDrawingCacheEnabled(true);
        recyclerView2.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                        checkPermissionCamera();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    checkPermissionCamera();
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkstorage=1;
                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                        checkPermissionStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    checkPermissionStorage();
                }
            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkstorage=2;
                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                        checkPermissionStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    checkPermissionStorage();
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View mview = getLayoutInflater().inflate(R.layout.enter_folder, null);

                final EditText notesname = (EditText)mview.findViewById(R.id.notes);
                Button done =(Button) mview.findViewById(R.id.done);
                Button cancel=(Button)mview.findViewById(R.id.cancel);
                builder.setView(mview);
                final AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!notesname.getText().toString().isEmpty()){

                            String name = notesname.getText().toString();
                            uploadFile(name);
                            dialog.dismiss();

                        }else{
                            notesname.setError("Please Enter Any Name");
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });



        return view;
    }

    private void uploadFile(final String name) {

        try {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Uploading File..");
            progressDialog.setProgress(0);
            progressDialog.show();

            final String course = manager.getPrefs(getActivity(), "course");

            StorageReference storageReference = storage.getReference();

            final List<UploadUri> uploadUris = new ArrayList<>();
            if (pdflist.size() > 0) {
                check = 1;
                uploadUris.addAll(pdflist);
                Log.e("sizeoflisttt", "hhh " + uploadUris.size());
            } else if (list.size() > 0) {
                check = 2;
                for(int i =0;i<list.size();i++){
                    uploadUris.add(list.get(i));
                }
                Log.e("sizeoflisttt", "hhh " + uploadUris.size());
            }

            for (int i = 0; i < uploadUris.size(); i++) {
                final int pos = i;
                final String filename = System.currentTimeMillis() + "";
                Log.e("Iskeandraagyame","lll");
                storageReference.child(course).child(name).child(filename).putFile(uploadUris.get(i).getSelectedUri())
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.e("Iskeandraagyame","success");
                                // Get a URL to the uploaded content
                                String url = taskSnapshot.getUploadSessionUri().toString();

                                DatabaseReference reference = database.getReference("Users");
                                reference.child("Teacher").child(course).child(name).child(filename).push().setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Snackbar.make(rv, "Uploaded Successfully", Snackbar.LENGTH_SHORT).show();
                                            if(pos==uploadUris.size()-1){
                                                pdfname.setText("");
                                                list.clear();
                                                uploadUris.clear();
                                                pdflist.clear();
                                                Fragment fragment = new NotesListFragment();
                                                FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                                        android.R.anim.fade_out);
                                                fragmentTransaction.replace(R.id.frame, fragment).commit();
                                            }


                                        } else {
                                            Snackbar.make(rv, "Not Successfully Uploaded", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Iskeandraagyame","failure");
                        Snackbar.make(rv, "Someting Went Wrong", Snackbar.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapterMethod();
        if (madapter2.uris.size() > 0) {
            upload.setVisibility(View.VISIBLE);
        }else if(pdflist.size()>0){
            upload.setVisibility(View.VISIBLE);
        } else {
            upload.setVisibility(View.GONE);
        }

        //  Globalfunctions.setFragment_index(12);
    }

    public void setAdapterMethod() {

        madapter2 = new UploadImagesAdapter(getActivity(), list,getActivity().getSupportFragmentManager());
        recyclerView2.setAdapter(madapter2);
        madapter2.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        list.clear();
    }

    public void checkPermissionCamera() {
        try {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Directly request for required permissions, without explanation
                requestPermissions(
                        new String[]{
                                Manifest.permission.CAMERA,
                        },
                        MY_PERMISSIONS_REQUEST_CODE_FOR_CAMERA
                );
                // }
            } else {
                // Do something, when permissions are already granted
                takePhotoFromCamera();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkPermissionStorage() {
        try {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Directly request for required permissions, without explanation
                requestPermissions(
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CODE_FOR_GALLERY
                );
                // }
            } else {
                // Do something, when permissions are already granted
                choosePhotoFromGallary();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void choosePhotoFromGallary() {
            Intent intent;
            if(checkstorage==1) {
                if(pdflist.size()==0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    } else {
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                    }
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
                }else{
                    Snackbar.make(rv,"You can select only pdf for now",Snackbar.LENGTH_LONG).show();
                }
            }else if(checkstorage==2){

                if(pdflist.size()==0 && list.size()==0) {
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
                    startActivityForResult(intent, PDF);
                }else{
                    AlertDialog.Builder builder;

                    builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("You can select only 1 file at a time !!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        }

    public void takePhotoFromCamera() {

        try {

                if(pdflist.size()==0) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // Create imageDir
                    Random generator = new Random();
                    int n = 10000;
                    n = generator.nextInt(n);
                    String filename = "Image-" + n + ".jpg";
                    File file = new File(getActivity().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + filename);

                    ImageURI = Uri.fromFile(file);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, ImageURI);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivityForResult(cameraIntent, CAMERA);
                }else
                {
                    Snackbar.make(rv,"You can select only pdf for now",Snackbar.LENGTH_LONG).show();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_CODE_FOR_CAMERA:

                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    takePhotoFromCamera();
                    //  Log.e("permission ","")
                    // Permissions are granted
                   // Snackbar.make(NavigationActivity., "Permission Granted", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                   // Snackbar.make(getActivity().view, "Permission Denied", Snackbar.LENGTH_SHORT).show();
                }
                return;

            case MY_PERMISSIONS_REQUEST_CODE_FOR_GALLERY:

                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    choosePhotoFromGallary();
                    //  Log.e("permission ","")
                    // Permissions are granted
                  //  Snackbar.make(relativeLayout, "Permission Granted", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                   // Snackbar.make(relativeLayout, "Permission Denied", Snackbar.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            if (requestCode == GALLERY && data != null) {

                try {
                    Log.e("clipdatame", "me aa gya" + data.getData());
                    if (data.getData() != null) {
                        Uri selectedImage = data.getData();
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            getActivity().getContentResolver().takePersistableUriPermission(
                                    selectedImage,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }
                        Log.e("iskeandrr", "me aa gya");
                        UploadUri uriModel = new UploadUri();
                        selectedImageuri = data.getData();
                        String realPath = getPathFromUri(getActivity(), selectedImageuri);
                        Log.e("imagepath", "realpath  " + realPath);
                        uriModel.setRealpath(realPath);
                        uriModel.setSelectedUri(selectedImageuri);
                        uriModel.setUri(selectedImageuri.toString());
                        list.add(uriModel);
                    } else {
                        ClipData clipData = data.getClipData();
                        if (clipData != null) {
                            Log.e("clipdatame2", "me aa gya");
                            //   uriModel=new UriModel();
                            Log.e("clipdata", "" + clipData.getItemCount());
                            // uris= new Uri[clipData.getItemCount()];
                            for (int i = 0; i < clipData.getItemCount(); i++) {

                                UploadUri uriModel = new UploadUri();
                                ClipData.Item item = clipData.getItemAt(i);
                                selectedImageuri = item.getUri();
                                String realPath = getPathFromUri(getActivity(),selectedImageuri);
                                Log.e("imagepath","realpath  "+realPath);
                                uriModel.setRealpath(realPath);
                                uriModel.setSelectedUri(selectedImageuri);
                                uriModel.setUri(selectedImageuri.toString());
                                list.add(uriModel);
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == CAMERA) {
                try {
                    //  uriModel=new UriModel();
                    UploadUri uriModel = new UploadUri();
                    Bitmap photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), ImageURI);

                    Log.e("BITMAP", photo.getHeight() + "" + photo.getWidth());
                    Log.e("Uri", "uri" + ImageURI.toString());
                    Log.e("UriPath", "uri" + ImageURI.getPath());
                    uriModel.setUri(ImageURI.toString());
                    uriModel.setSelectedUri(selectedImageuri);
                    String realPath = getPathFromUri(getActivity(), ImageURI);
                    uriModel.setRealpath(realPath);
                    Log.e("selectedimageuri", "" + ImageURI);
                    Log.e("urisss", "" + list.size());
                    list.add(uriModel);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }else if(requestCode == PDF && data!=null){

                try {
                    Log.e("pdfresult","meaagya");
                    if (data.getData() != null) {
                        Log.e("pdfresulnnnt","meaagya");
                        selectedImageuri = data.getData();
                        Log.e("PDfpath","hh"+selectedImageuri);
                        Log.e("lathsegment",""+selectedImageuri.getLastPathSegment());
                        pdfname.setText(selectedImageuri.getLastPathSegment());
                        UploadUri uploadUri = new UploadUri();
                        uploadUri.setSelectedUri(selectedImageuri);
                        uploadUri.setUri(selectedImageuri.toString());
                        pdflist.add(uploadUri);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }
    }
    public String getRealPathFromURI(Uri contentURI, Activity context) {
        Log.e("IMAGEURI",selectedImageuri.getPath()+""+selectedImageuri);
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }

    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
