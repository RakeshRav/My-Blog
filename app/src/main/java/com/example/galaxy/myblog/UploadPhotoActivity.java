package com.example.galaxy.myblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class UploadPhotoActivity extends AppCompatActivity {

    final int REQUEST_CODE = 100;
    Bitmap full_image = null;
    Bitmap thumb_image = null;

    ParseFile full_file = null;
    ParseFile thumb_file = null;

    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        profile = (ImageView) findViewById(R.id.profile_pic);

        Intent i = new Intent(UploadPhotoActivity.this,MyBlogActivity.class);

    }

    public void save(View v)
    {
        ByteArrayOutputStream full_stream = new ByteArrayOutputStream();
        ByteArrayOutputStream thumb_stream = new ByteArrayOutputStream();

        thumb_image.compress(Bitmap.CompressFormat.PNG, 100, thumb_stream);
        full_image.compress(Bitmap.CompressFormat.PNG, 100, full_stream);

        ParseUser user = ParseUser.getCurrentUser();

        String fullName = user.getUsername()+"_full";
        String thumbName = user.getUsername()+"_thumb";

        thumb_file = new ParseFile(thumbName,thumb_stream.toByteArray());

        full_file = new ParseFile(fullName,full_stream.toByteArray());

        final ProgressDialog dialog = ProgressDialog.show(UploadPhotoActivity.this,"Uploading Image","Please Wait....",true,false);

        full_file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(UploadPhotoActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        thumb_file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(UploadPhotoActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        user.put("photo_full", full_file);
        user.put("photo_thumb",thumb_file);

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null)
                {
                    Toast.makeText(UploadPhotoActivity.this, "Pakka Saved h", Toast.LENGTH_SHORT).show();
                }

                dialog.cancel();
            }
        });
    }

    public void uploadPhoto(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
             full_image = getPath(data.getData());

             thumb_image = getCircleBitmap(full_image);


            full_image = Bitmap.createScaledBitmap(full_image, 720, 640, false);

            profile.setImageBitmap(full_image);

        }
    }

    private Bitmap getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor
                .getColumnIndex(MediaStore.Images.Media.DATA);

        String filePath = cursor.getString(column_index);
        cursor.close();
        // Convert file path into bitmap image using below line.
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {

        bitmap = Bitmap.createScaledBitmap(bitmap,240,240,false);
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
