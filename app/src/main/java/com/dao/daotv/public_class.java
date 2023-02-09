package com.dao.daotv;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amap.api.maps.model.LatLng;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import cz.msebera.android.httpclient.entity.mime.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.WIFI_SERVICE;

public class public_class {
    public static Context now_class;
    public static ProgressBar update_schedule;
    public static Activity now_act;
    public static List<Integer> select_int;
    public static String dpath;

    public static float App_version = 1.6f;//软件版本


    //万年历接口
    public static String wnlapi = "http://47.106.68.150/dingyue/wnlapi.php?";
    public static int day = 1;
    public static int[] days;
    public static String[] day_name = {"初一","初二","初三","初四","初五","初六","初七","初八","初九","初十",
    "十一","十二","十三","十四","十五","十六","十七","十八","十九","二十","廿一","廿二","廿三","廿四","廿五","廿六",
            "廿七","廿八","廿九","三十"};

    //撒豆成兵
    public static List<LatLng> latLngss;

    //天道权限
    public static String dao_gbook = "0";
    public static String dao_time = "0";
    public static String dao_api = "http://47.106.68.150/dingyue/tiandao.php?key=";

    //留言板接口
    public static String gbook_api = "http://47.106.68.150/dingyue/gbook.php";
    public static String gbook_ip;

    //轮播图
    public static ArrayList<String> banner_url = new ArrayList<>();
    public static ArrayList<String> banner_title = new ArrayList<>();
    public static ArrayList<String> banner_id = new ArrayList<>();

    //视频参数
    public static String Json_video;
    public static String Video_id;
    public static int Video_pg;
    public static int Video_pgc;
    public static int Video_status;
    public static int player_daoism = 0;

    //类型参数
    public static String video_vod="6,7,8,9,10,11,12,22,23,24,25,41,42";
    public static String video_tv="13,14,15,16,27";
    public static String video_arts="3";
    public static String video_animation="4";

    //网站api对接
    public static String url_api = "http://47.106.68.150/api.php/provide/vod/";
    public static String player_office = "qq youku qiyi mgtv sohu pptv letv m1905 leshi cntv";
    private static OkHttpClient okHttpClient = new OkHttpClient();

    //播放参数
    public static String player_url = "https://cdn.oss-cn-hangzhou.myqcloud.com.xuetuiguang.cn/m3u8video/rdws.m3u8";
    public static String player_name = "第一集";
    public static String player_video_name = "真武大帝";

    //解析接口
    public static String player_jx = "https://json.m3u8.tv:7788/json.php?url=";
    public static String player_jx1 = "https://api.yqkfilm.com/analysis/json/?uid=29&my=bcdipsuwDFGKLPVZ49&url=";

    //奇门遁甲
    public static String gaode_apiv2 = "https://restapi.amap.com/v5/ip?key=b39e430f31088053b7ad1f5d1f93f38d&&ip=";
    public static String gaode_apiv1 = "https://restapi.amap.com/v3/ip?key=b39e430f31088053b7ad1f5d1f93f38d&&ip=";
    public static String tenxun_api = "https://apis.map.qq.com/ws/location/v1/ip?key=KQJBZ-S4JY3-5W23J-3JYFY-HTUV7-KKF7Z&&ip=";
    public static String baidu_api = "https://api.map.baidu.com/location/ip?coor=bd09ll&ak=QSboocBYGrxA7TZ4Lng79YgxTCTgyord&&ip=";

    //撒豆成兵
    public static String baidu_map = "https://api.map.baidu.com/reverse_geocoding/v3/?extensions_poi=1&ak=QSboocBYGrxA7TZ4Lng79YgxTCTgyord&output=json&coordtype=bd09lll&location=";
    public static String qq_map = "https://apis.map.qq.com/ws/geocoder/v1/?get_poi=1&poi_options=address_format=short&key=KQJBZ-S4JY3-5W23J-3JYFY-HTUV7-KKF7Z&location=";
    public static String gaode_map = "https://restapi.amap.com/v3/geocode/regeo?extensions=all&key=b39e430f31088053b7ad1f5d1f93f38d&location=";

    //斗转星移
    public static List<String> dzxy_lisy = new ArrayList<>();

    //文件夹创建
    public static void create_file_folder(String path) {
        File file_folder = null;
        try {
            file_folder = new File(path);
            if (!file_folder.exists()) {
                file_folder.mkdir();
                Toast.makeText(now_class, "创建文件夹", Toast.LENGTH_SHORT).show();
                Toast.makeText(now_class, "创建成功", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(now_class, path, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(now_class, "创建失败" + e, Toast.LENGTH_SHORT).show();
        }
    }


    public static void Download(String url, String path, String name) {
        File file = new File(path);
        File update_apk = new File(String.valueOf(path) + name);
        if (!file.exists()) {
            file.mkdir();
        }
        if (update_apk.exists()) {
            update_apk.delete();
        }
        FileDownloader.getImpl().create(url)
                .setPath(String.valueOf(update_apk))
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Toast.makeText(now_class, "开始下载", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        update_schedule.setMax(totalBytes);
                        update_schedule.setProgress(soFarBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {

                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (update_apk.exists()) {
                            update_schedule.setProgress(update_schedule.getMax());
                            open_apk(now_class,String.valueOf(update_apk));
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
    }


    private static Uri uri;
    public static void open_apk(Context context,String path) {
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context,"com.dao.daotv.fileprovider",file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
             uri = Uri.fromFile(file);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


    private static final String[][] MIME_MapTable = {
            //{后缀名，    MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/msword"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".JPEG", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.ms-powerpoint"},
            {".prop", "text/plain"},
            {".rar", "application/x-rar-compressed"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/zip"},
            {"", "*/*"}
    };

    private static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0)
            return type;
        /* 获取文件的后缀名 */
        String fileType = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (fileType == null || "".equals(fileType))
            return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (fileType.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public static String iptoint(String ip){
        String [] ip_part = ip.split("\\.");
        for (int i=0;i<4;i++){
            ip_part[i] = Integer.toBinaryString(Integer.parseInt(ip_part[i]));
            while (ip_part[i].length()<8){
                ip_part[i] = "0" + ip_part[i];
            }
        }
        ip = ip_part[0] + ip_part[1] + ip_part[2] + ip_part[3];
        return Long.valueOf(ip,2).toString();
    }

    public static String inttoip(String ip){
        ip = Integer.toBinaryString(Integer.parseInt(ip));
        while (ip.length()<32){
            ip="0"+ip;
        }
        String [] ip_part = {ip.substring(0,8),ip.substring(8,16),ip.substring(16,24),ip.substring(24,32)};
        ip = Integer.valueOf(ip_part[0],2).toString() + "." + Integer.valueOf(ip_part[1],2).toString() + "." + Integer.valueOf(ip_part[2],2).toString() + "." + Integer.valueOf(ip_part[3],2).toString();
        return ip;
    }

    public static String get_ip(Context context){
        // 获取WiFi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断WiFi是否开启
        if (wifiManager.isWifiEnabled()) {
            // 已经开启了WiFi
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = inttoip(String.valueOf(ipAddress));
            return ip;
        } else {
            // 未开启WiFi
            return getIpAddress();
        }
    }

    private static String getIpAddress() {
        try {
            NetworkInterface networkInterface;
            InetAddress inetAddress;
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
            return null;
        } catch (SocketException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    //ok网络请求
    public static void http_get(String url,Callback back) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        Call call = client.newCall(request.build());
        call.enqueue(back);
    }


    //秒转时间
    public static String timetodate(String time){
        long timer = Long.valueOf(time);
        Date date = new Date(timer*1000);
        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 北京
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // 设置北京时区
        return bjSdf.format(date).toString();
    }

    //时间转秒
    public static String datetotime(){
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        long l = c.getTimeInMillis() / 1000;
        return String.valueOf(l);
    }

    //时间比较
    public static boolean timetotime(String time){
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        long l = c.getTimeInMillis() / 1000;
        long timer = Long.valueOf(time);
        if (l>timer){
            return true;
        }
        return false;
    }


    private static android.widget.Toast toast;
    public static void shouToast(Context context,String content){
        if(toast==null){
            toast= android.widget.Toast.makeText(context,content, android.widget.Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }



    //字体渐变
    public static void setGradientFont(TextView textView, String startColor, String endColor){
        // Shader.TileMode.CLAMP：如果着色器超出原始边界范围，会复制边缘颜色
        LinearGradient gradient = new LinearGradient(0, 0, 0,
                textView.getPaint().getTextSize(),
                Color.parseColor(startColor), Color.parseColor(endColor),
                Shader.TileMode.CLAMP);

        textView.getPaint().setShader(gradient);
        // 直接调用invalidate()方法，请求重新draw()，但只会绘制调用者本身
        textView.invalidate();
    }

    //ipv4判断
    public static boolean isIpv4(String ip) {
        boolean b1 = ip.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
        return b1;
    }

    //ipv6判断
    public static boolean isIpv6(String ip) {
        boolean b1 = ip.matches("^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$");
        return b1;
    }

    //复制剪切板
    public static void copyContentToClipboard(Context context,String content) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }


    //解析字符串
    public static String encodeURIComponent(String s) {
        if (s == null) {
            return null;
        }
        String result = null;
        try {
            result = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    //转义字符串
    public static String decodeURIComponent(String s) {
        String result = null;
        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}

