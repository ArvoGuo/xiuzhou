package com.jsycloud.ir.xiuzhou;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CommonTools {

    public static boolean newVersion(String first, String second) {
        String[] firsts = first.split("\\.");
        String[] seconds = second.split("\\.");
        int firstToNum = Integer.parseInt(firsts[0]) * 100 + Integer.parseInt(firsts[1]) * 10 + Integer.parseInt(firsts[2]);
        int secondToNum = Integer.parseInt(seconds[0]) * 100 + Integer.parseInt(seconds[1]) * 10 + Integer.parseInt(seconds[2]);
        if(firstToNum > secondToNum){
            return true;
        }
        return false;
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getRiverId(String url) {

        String result = "";
        boolean bStart = false;
        for(int i = 0;i<url.length();i++){
            if(url.charAt(i)=='&'){
                break;
            }
            if(bStart){
                result = result + url.charAt(i);
            }
            if(url.charAt(i)=='='){
                bStart = true;
            }
        }
        return result;
    }

    public static int getRiverIndexByRiverId(String riverId) {

        for(int i = 0;i<Constant.allriverList.size();i++){
            if(Constant.allriverList.get(i).getId().equals(riverId)){
                return i;
            }
        }
        return 0;
    }

    public static String getMacAddress(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }

    public static String getVersionNum(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (info != null) {
                return info.versionName;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static Bitmap decodeBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // ͨ�����bitmap��ȡͼƬ�Ŀ�͸�&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap == null) {
            System.out.println("bitmapΪ��");
        }
        float realWidth = options.outWidth;
        float realHeight = options.outHeight;
        int inSampleSize = 1;
        if (realHeight > 500 || realWidth > 500) {
            // �����ʵ�ʿ�ߺ�Ŀ���ߵı���
            final int heightRatio = Math.round(realWidth / (float) 500);
            final int widthRatio = Math.round(realHeight / (float) 500);
            // ѡ���͸�����С�ı�����ΪinSampleSize��ֵ���������Ա�֤����ͼƬ�Ŀ�͸�
            // һ��������ڵ���Ŀ��Ŀ�͸ߡ�
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        // ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false,���ͼƬ��Ҫ��ȡ�����ġ�&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }
}
