package com.ascendant.thegade.Activity.ui.Karyawan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ascendant.thegade.API.ApiRequest;
import com.ascendant.thegade.API.RetroServer;
import com.ascendant.thegade.Activity.HomeActivity;
import com.ascendant.thegade.BuildConfig;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.Response.ResponseObject;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispensasiKehadiranActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    LinearLayout LTanggalMulai, LTanggalSelesai;
    TextView TanggalMulai, TanggalSelesai;
    String Tanggals = "Mulai";
    String tanggalMulai = "1994-01-15";
    String tanggalSelesai = "1994-01-15";
    Ascendant AscNet;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    TextView nama,nik;

    EditText JenisDispensasi,Keterangan;

    Button btnBerkas;
    LinearLayout linearBerkas;
    TextView tvBerkas;
    ImageView ivBerkas;
    Button Kirim;
    //Dellaroy Logic
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMERA_PIC_REQUEST = 1111;


    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private String mediaPath;

    private Button btnCapturePicture;

    private String mImageFileLocation = "";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    String postFoto1= "";
    //ONCLICK
    Boolean Gambar1 = false;

    private String[] galleryPermissions =
            {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensasi_kehadiran);
        AscNet = new Ascendant();
        if(EasyPermissions.hasPermissions(DispensasiKehadiranActivity.this, galleryPermissions)) {

        }else{
            EasyPermissions.requestPermissions(DispensasiKehadiranActivity.this, "Access for storage and Camera",
                    101, galleryPermissions);
        }
        Declaration();
        OnClick();
    }
    private void OnClick(){
        Kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checker();
            }
        });
        LTanggalMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tanggals = "Mulai";
                showDatePicker();
            }
        });
        LTanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tanggals = "Selesai";
                showDatePicker();
            }
        });
        btnBerkas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(DispensasiKehadiranActivity.this)
                        .title("Pilih Gambar")
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        Gambar1 = true;
                                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                        linearBerkas.setVisibility(View.VISIBLE);
                                        break;
                                    case 1:
                                        Gambar1 = true;
                                        captureImage();
                                        linearBerkas.setVisibility(View.VISIBLE);
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });
    }
    private void Checker(){
        if (Gambar1=false){
            Toast.makeText(DispensasiKehadiranActivity.this, "Harap Unggah Gambar", Toast.LENGTH_SHORT).show();
        }else if (JenisDispensasi.getText().toString().isEmpty()){
            Toast.makeText(DispensasiKehadiranActivity.this, "Harap Jenis Dispensasi Diisi", Toast.LENGTH_SHORT).show();
        }else if(Keterangan.getText().toString().isEmpty()){
            Toast.makeText(DispensasiKehadiranActivity.this, "Harap Keterangan Diisi", Toast.LENGTH_SHORT).show();
        }else{
            Logic();
        }
    }
    private void Logic(){
        final ProgressDialog pd = new ProgressDialog(DispensasiKehadiranActivity.this);
        pd.setMessage("Sedang Mengajukan");
        pd.show();
        pd.setCancelable(false);
        File file = new File(postFoto1);
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part Foto = MultipartBody.Part.createFormData("file_dispensasi", file.getName(), fileReqBody);

        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseObject> Claim =api.DispensasionPengajuan(
                AscNet.AUTH(),
                RequestBody.create(MediaType.parse("text/plain"),Id),
                RequestBody.create(MediaType.parse("text/plain"),JenisDispensasi.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),tanggalMulai),
                RequestBody.create(MediaType.parse("text/plain"),tanggalSelesai),
                RequestBody.create(MediaType.parse("text/plain"),Keterangan.getText().toString()),
                Foto
        );
        Claim.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                pd.hide();
                Toast.makeText(DispensasiKehadiranActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (response.body().getCode().equals(200)){
                    Intent intent =new Intent(DispensasiKehadiranActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                pd.hide();
                Toast.makeText(DispensasiKehadiranActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Declaration(){
        btnBerkas = findViewById(R.id.btnBerkas);
        linearBerkas = findViewById(R.id.linearBerkas);
        tvBerkas = findViewById(R.id.tvBerkas);
        ivBerkas = findViewById(R.id.ivBerkas);
        Kirim = findViewById(R.id.btnKirim);

        JenisDispensasi = findViewById(R.id.etJenisDispensasi);
        Keterangan = findViewById(R.id.etKetrangan);
        LTanggalMulai = findViewById(R.id.linearTanggalMulai);
        LTanggalSelesai = findViewById(R.id.linearTanggalSelesai);
        TanggalMulai = findViewById(R.id.tvTanggalMulai);
        TanggalSelesai = findViewById(R.id.tvTanggalSelesai);
        nama = findViewById(R.id.tvNama);
        nik = findViewById(R.id.tvNik);
        dbHelper = new DB_Helper(this);
        Cursor cursor = dbHelper.checkUser();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                Id = cursor.getString(0);
                Username = cursor.getString(1);
                Nama = cursor.getString(2);
                Status = cursor.getString(3);
                Nik = cursor.getString(4);
            }
        }
        nama.setText(Nama);
        nik.setText(Nik);

        TanggalMulai.setText(AscNet.thisDay());
        TanggalSelesai.setText(AscNet.thisDay());
        tanggalMulai = AscNet.Today();
        tanggalSelesai = AscNet.Today();
    }

    private void showDatePicker(){
        DatePickerDialog dialog = new DatePickerDialog(this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String M = "01";
        String D = "01";
        int m = month+1;
        if (m<10){
            M="0"+String.valueOf(month+1);
        }else{
            M=String.valueOf(month+1);
        }
        if (dayOfMonth<10){
            D="0"+String.valueOf(dayOfMonth);
        }else{
            D=String.valueOf(dayOfMonth);
        }
        String date = year+"-"+M+"-"+D;
        if (Tanggals.equals("Mulai")){
            TanggalMulai.setText(AscNet.DateChanges(String.valueOf(year),M,D));
            tanggalMulai = date;
        }else{
            TanggalSelesai.setText(AscNet.DateChanges(String.valueOf(year),M,D));
            tanggalSelesai = date;
        }
    }
    //Dellaroy Logic
    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }


    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TEST", "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }
    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
            if (data != null) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                // Set the Image in ImageView for Previewing the Media

//                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                if(Gambar1) {
                    postFoto1 = mediaPath;
                    String filename = postFoto1.substring(postFoto1.lastIndexOf("/") + 1);
                    linearBerkas.setVisibility(View.VISIBLE);
                    ivBerkas.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    tvBerkas.setText(filename);
                    Gambar1 = false;
                    Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == CAMERA_PIC_REQUEST){
            if (Gambar1){
                if (Build.VERSION.SDK_INT > 21) {
                    Glide.with(this).load(mImageFileLocation).into(ivBerkas);
                    postFoto1 = mImageFileLocation;
                }else{
                    Glide.with(this).load(fileUri).into(ivBerkas);
                    postFoto1 = fileUri.getPath();
                }
                String filename=postFoto1.substring(postFoto1.lastIndexOf("/")+1);
                linearBerkas.setVisibility(View.VISIBLE);
                tvBerkas.setVisibility(View.VISIBLE);
                tvBerkas.setText(filename);
                Gambar1=false;
            }
        }
    }
}