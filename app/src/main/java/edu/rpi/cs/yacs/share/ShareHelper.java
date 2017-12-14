package edu.rpi.cs.yacs.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by mark on 11/20/17.
 */

public class ShareHelper {
    public void shareViewToApps(Activity activity, Context context, View view) {
        Bitmap bitmap = getBitmapFromView(view);
        Uri uri = getImageUri(context, bitmap);

        if (!uri.getPath().isEmpty()) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_STREAM, uri);

            try {
                activity.startActivity(Intent.createChooser(i, "Share Schedule Image"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(context, "Could not initialize share intent", Toast.LENGTH_SHORT).show();
            }
        }

        File file = new File(uri.getPath());

        if (file.exists()) {
            file.delete();
        }
    }

    public void saveToGallery(Activity activity, Context context, View view) {
        Bitmap bitmap = getBitmapFromView(view);
        getImageUri(context, bitmap);

        Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT).show();
    }

    public void shareToFacebook(Activity activity, Context context, View view) {
//        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.ACTION_PROCESS_TEXT, "text to be shared");
//        PackageManager pm = activity.getPackageManager();
//        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
//        for (final ResolveInfo app : activityList) {
//            if ((app.activityInfo.name).contains("facebook")) {
//                final ActivityInfo a = app.activityInfo;
//                final ComponentName name = new ComponentName(a.applicationInfo.packageName, a.name);
//                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                shareIntent.setComponent(name);
//                activity.startActivity(shareIntent);
//                break;
//            }
//        }

        Bitmap bitmap = getBitmapFromView(view);
        Uri uri = getImageUri(context, bitmap);

        String urlToShare = "Hello";

        try {
            Intent intent1 = new Intent();
            intent1.setPackage("com.facebook.katana");
            intent1.setAction("android.intent.action.SEND");
            intent1.setType("image/jpeg");
            intent1.putExtra("android.intent.extra.TEXT", urlToShare);
            intent1.putExtra("android.intent.extra.STREAM", uri);
            intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(intent1);
        } catch (Exception e) {
            // If we failed (not native FB app installed), try share through url
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            activity.startActivity(intent);
        }

        File file = new File(uri.getPath());

        if (file.exists()) {
            file.delete();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        Log.d("ShareHelper", "Size: " + view.getWidth() + " " + view.getHeight());

        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();

        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }

        view.draw(canvas);

        return returnedBitmap;
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Share Schedule Image", null);

        Uri uri = Uri.parse(path);

        if (uri.getPath().isEmpty()) {
            return Uri.EMPTY;
        }

        return uri;
    }
}