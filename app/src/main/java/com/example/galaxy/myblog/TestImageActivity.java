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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class TestImageActivity extends AppCompatActivity {


    ImageView imageView;
    Button pick;
    final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);

        imageView = (ImageView) findViewById(R.id.image_view);
        pick = (Button) findViewById(R.id.pick);

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            Bitmap bitmap = getPath(data.getData());

            Bitmap b1 = getCircleBitmap(bitmap);

            bitmap = Bitmap.createScaledBitmap(bitmap,720,640,false);

            imageView.setImageBitmap(bitmap);

            ParseFile file = null;
            ParseFile file1 = null;

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();

            b1.compress(Bitmap.CompressFormat.PNG,100,stream);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream1);

//            file = new ParseFile("A_thumb.png",stream.toByteArray());

            file1 = new ParseFile("A_full.png",stream1.toByteArray());

            final ProgressDialog dialog = ProgressDialog.show(TestImageActivity.this,"Uploading Image","Please Wait....",true,false);

//            file.saveInBackground(new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e==null) {
//                        Toast.makeText(TestImageActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//
            file1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null) {
                        Toast.makeText(TestImageActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            ParseUser user = ParseUser.getCurrentUser();
            user.put("photo_full", file1);
//            user.put("photo_thumb",file);

            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null)
                    {
                        Toast.makeText(TestImageActivity.this, "Pakka Saved h", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                }
            });

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
        getMenuInflater().inflate(R.menu.menu_test_image, menu);
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
