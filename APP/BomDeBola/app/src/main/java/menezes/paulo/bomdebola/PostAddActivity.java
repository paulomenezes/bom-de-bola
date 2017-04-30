package menezes.paulo.bomdebola;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import menezes.paulo.bomdebola.listeners.Request;
import menezes.paulo.bomdebola.models.Post;
import menezes.paulo.bomdebola.util.SendJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;
import menezes.paulo.bomdebola.util.UploadFile;

public class PostAddActivity extends AppCompatActivity {

    private Uri fileUri = null;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        Button camera = (Button) findViewById(R.id.camera);
        Button photos = (Button) findViewById(R.id.photos);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(); // create a file to save
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set

                // start the image capture Intent
                startActivityForResult(intent, 0);
            }
        });

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(), fileUri);

                if (image.getWidth() > 1000) {
                    image = getResizedBitmap(image, image.getHeight() / 4, image.getWidth() / 4);
                }

                final ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
                imageView.setImageBitmap(image);

                findViewById(R.id.selectedImageLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonPanel).setVisibility(View.GONE);

                Button removeImage = (Button) findViewById(R.id.removeImage);
                removeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.selectedImageLayout).setVisibility(View.GONE);
                        findViewById(R.id.buttonPanel).setVisibility(View.VISIBLE);

                        imageView.setImageBitmap(null);
                    }
                });
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.layout), R.string.msg_error, Snackbar.LENGTH_LONG).show();
            }

        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                final InputStream imageStream = getContentResolver().openInputStream(uri);

                Bitmap image = BitmapFactory.decodeStream(imageStream);
                if (image.getWidth() > 1000) {
                    image = getResizedBitmap(image, image.getHeight() / 4, image.getWidth() / 4);
                }

                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BomDeBola");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File mediaFile;
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "BomDeBola_" + timeStamp + ".jpg");

                fileUri = Uri.fromFile(mediaFile);

                FileOutputStream fos = new FileOutputStream(fileUri.getPath());
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                fos.flush();
                fos.close();

                final ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
                imageView.setImageBitmap(image);

                findViewById(R.id.selectedImageLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonPanel).setVisibility(View.GONE);

                Button removeImage = (Button) findViewById(R.id.removeImage);
                removeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.selectedImageLayout).setVisibility(View.GONE);
                        findViewById(R.id.buttonPanel).setVisibility(View.VISIBLE);

                        imageView.setImageBitmap(null);
                    }
                });
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.layout), R.string.msg_error, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        Log.d("debug", Environment.getExternalStorageState());

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "BomDeBola");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Bom de Bola", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "BomDeBola_" + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.send) {
            register();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void register() {
        mDialog = ProgressDialog.show(this, "", getResources().getString(R.string.msg_loading), true);

        EditText title = (EditText) findViewById(R.id.title);
        EditText text = (EditText) findViewById(R.id.text);
        EditText place = (EditText) findViewById(R.id.place);

        RadioButton type_public = (RadioButton) findViewById(R.id.type_public);

        if (title.getText().length() == 0 || text.getText().length() == 0) {
            if (title.getText().length() == 0) {
                Snackbar.make(findViewById(R.id.layout), "Campo título obrigatório", Snackbar.LENGTH_LONG).show();
            } else if (text.getText().length() == 0) {
                Snackbar.make(findViewById(R.id.layout), "Campo texto obrigatório", Snackbar.LENGTH_LONG).show();
            }

            mDialog.dismiss();
        } else {
            final Post post = new Post();
            post.setTitle(title.getText().toString());
            post.setText(text.getText().toString());
            post.setPlace(place.getText().toString());
            post.setType(type_public.isChecked() ? "Public" : "Friends");
            post.setIdUser(MainActivity.sUser.getId());

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            post.setDate(dateFormat.format(date));

            if (fileUri != null) {
                post.setImage(fileUri.getPathSegments().get(fileUri.getPathSegments().size() - 1));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UploadFile uploadFile = new UploadFile();
                        if (uploadFile.uploadFile(URLs.API_POST_CREATE, fileUri.getPath())) {
                            new SendJsonAsyncTask(new Gson().toJson(post), new Request() {
                                @Override
                                public void success(Boolean status) {
                                    if (status) {
                                        startActivity(new Intent(PostAddActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Snackbar.make(findViewById(R.id.layout), R.string.msg_error, Snackbar.LENGTH_LONG).show();
                                    }

                                    mDialog.dismiss();
                                }
                            }).execute(URLs.API_POST_CREATE);
                        }
                    }
                }).start();
            } else {
                try {
                    new SendJsonAsyncTask(new Gson().toJson(post), new Request() {
                        @Override
                        public void success(Boolean status) {
                            if (status) {
                                startActivity(new Intent(PostAddActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Snackbar.make(findViewById(R.id.layout), R.string.msg_error, Snackbar.LENGTH_LONG).show();
                            }

                            mDialog.dismiss();
                        }
                    }).execute(URLs.API_POST_CREATE);
                } catch (Exception e) {
                    Snackbar.make(findViewById(R.id.layout), R.string.msg_error, Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }
}
