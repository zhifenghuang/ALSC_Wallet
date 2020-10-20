package com.cao.commons.util.file;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.cao.commons.base.PoliceApplication;
import com.cao.commons.util.TelephoneUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * 文件路径，图片
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-12-4
 */

public class FileUtils {
    /**
     * TAG for log messages.
     */
    static final String TAG = "FileUtils";
    private static FileOutputStream out;

    public static void getImageList(Bitmap btImage) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
        {      // 获取SDCard指定目录下 Environment.getExternalStorageDirectory().getPath() + File.separator + PoliceApplication.getUserId() + "/Video/"
            String sdCardDir = Environment.getExternalStorageDirectory().getPath() + File.separator + TelephoneUtil.getUID() + "/CoolImage/";
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {                //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }                            //文件夹有啦，就可以保存图片啦
            String imageName = System.currentTimeMillis() + ".jpg";
            File file = new File(sdCardDir, imageName);// 在SDcard的目录下创建图片文,以当前时间为其命名

            try {
                out = new FileOutputStream(file);
                btImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
                fileScan(PoliceApplication.newInstance(), sdCardDir + imageName);
                System.out.println("_________保存到____sd______指定目录文件夹下____________________");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 扫描媒体文件
     *
     * @param context
     * @param filePath
     */
    public static void fileScan(Context context, String filePath) {
        Uri data = Uri.parse("file://" + filePath);
        Log.d("TAG", "file:" + filePath);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    /**
     * 迭代扫描文件夹
     *  
     *
     * @param path
     */
    public static void folderScan(Context context, String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] array = file.listFiles();

            for (int i = 0; i < array.length; i++) {
                File f = array[i];
                if (f.isFile()) {// FILE TYPE
                    String name = f.getName();
                    // if(name.contains(".mp3")){
                    fileScan(context, f.getAbsolutePath());
                    // }
                } else {// FOLDER TYPE
                    folderScan(context, f.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 将文件从filePath目录，考贝到 指定目录，并将原来的删除
     *
     * @param fileName 文件名,如1523445455.arm
     */
    public static String copyFile2Phone(Context context, String filePath, String fileName) {
        String newPath = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            String sdCardDir = Environment.getExternalStorageDirectory().getPath() + File.separator +
                    TelephoneUtil.getUID() + File.separator + "Audio" + File.separator;
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {                //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            newPath = sdCardDir + File.separator + fileName;
            File file = new File(newPath);
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fos = new FileOutputStream(file);//如果文件不存在，FileOutputStream会自动创建文件
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();//刷新缓存区
                inputStream.close();
                fos.close();
                deleteFile(filePath);
                fileScan(context, filePath);
                fileScan(context, newPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newPath;
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String path) {
        // 删除缓存
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取文件格式名
     */
    public static String getFormatName(String fileName) {
        //去掉首尾的空格
        fileName = fileName.trim();
        String s[] = fileName.split("\\.");
        if (s.length >= 2) {
            return s[s.length - 1];
        }
        return "";
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br>
     * <br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
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
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
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
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author paulburke
     */
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
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (IllegalArgumentException ex) {
            Log.i(TAG, String.format(Locale.getDefault(), "getDataColumn: _data - [%s]", ex.getMessage()));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     * @author paulburke
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
