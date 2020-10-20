package com.cao.commons.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

import java.text.SimpleDateFormat;

public class MyOSSUtils {

    private static MyOSSUtils instance;

    /**
     * 主机地址（根据地区而变化）
     */
    private final String P_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";

    /**
     * （服务器域名，后台给）
     */
    private final String P_STSSERVER = "http://sts.xqcsh.com/index.php";

    private final String P_BUCKETNAME = "xqcsh";

    /**
     * （文件夹名字，与后台统一）
     */
    private final String OBJECT_NAME_IMAGE = "images";
    private final String OBJECT_NAME_VIDEO = "videos";


    private OSS oss;
    private SimpleDateFormat simpleDateFormat;

    public MyOSSUtils() {
    }

    public static MyOSSUtils getInstance() {
        if (instance == null) {
            instance = new MyOSSUtils();
        }
        return instance;
    }

    private void getOSs(Context context) {
        //推荐使用OSSAuthCredentialsProvider。token过期可以及时更新
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(P_STSSERVER);

        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);// 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000);// socket超时，默认15秒
        conf.setMaxConcurrentRequest(5);// 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2);// 失败后最大重试次数，默认2次
        oss = new OSSClient(context, P_ENDPOINT, credentialProvider);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        }
    }

    /**
     * 上传图片 上传文件
     * <p>
     * <p>
     *     * @param context      application上下文对象
     * <p>
     *     * @param ossUpCallback 成功的回调
     * <p>
     *     * @param img_name      上传到oss后的文件名称，图片要记得带后缀 如：.jpg
     * <p>
     *     * @param imgPath      图片的本地路径
     */
    public void upImage(Context context, final OssUpCallback ossUpCallback, final String img_name, String imgPath) {
        getOSs(context);
        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, OBJECT_NAME_IMAGE + "/" + img_name, imgPath);

        // 异步上传时可以设置进度回调。
        putObjectRequest.setProgressCallback(new OSSProgressCallback() {

            @Override
            public void onProgress(Object request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback() {

            @Override
            public void onSuccess(OSSRequest request, OSSResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("RequestId", result.getRequestId());
            }

            @Override
            public void onFailure(OSSRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }

                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback() {

            @Override
            public void onSuccess(OSSRequest request, OSSResult result) {
                Log.e("MyOSSUtils", "------getRequestId:" + result.getRequestId());
                ossUpCallback.successImg(oss.presignPublicObjectURL(P_BUCKETNAME, OBJECT_NAME_IMAGE + "/" + img_name));
            }

            @Override
            public void onFailure(OSSRequest request, ClientException clientException, ServiceException serviceException) {
                ossUpCallback.failureImg(clientException.toString());
            }

        });
    }

    /**
     * 上传图片 上传流
     * <p>
     * <p>
     *     * @param context      application上下文对象
     * <p>
     *     * @param ossUpCallback 成功的回调
     * <p>
     *     * @param img_name      上传到oss后的文件名称，图片要记得带后缀 如：.jpg
     * <p>
     *     * @param imgbyte      图片的byte数组
     */
    public void upImage(Context context, final OssUpCallback ossUpCallback, final String img_name, byte[] imgbyte) {
        getOSs(context);
        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, OBJECT_NAME_IMAGE + "/" + img_name, imgbyte);

        putObjectRequest.setProgressCallback(new OSSProgressCallback() {

            @Override
            public void onProgress(Object request, long currentSize, long totalSize) {
                ossUpCallback.inProgress(currentSize, totalSize);

            }

        });

        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback() {

            @Override
            public void onSuccess(OSSRequest request, OSSResult result) {
                Log.e("MyOSSUtils", "------getRequestId:" + result.getRequestId());
                ossUpCallback.successImg(oss.presignPublicObjectURL(P_BUCKETNAME, OBJECT_NAME_IMAGE + "/" + img_name));
            }

            @Override
            public void onFailure(OSSRequest request, ClientException clientException, ServiceException serviceException) {
                ossUpCallback.failureImg(clientException.toString());
            }

        });
    }

    /**
     * 上传视频
     * <p>
     * <p>
     *     * @param context      application上下文对象
     * <p>
     *     * @param ossUpCallback 成功的回调
     * <p>
     *     * @param video_name    上传到oss后的文件名称，视频要记得带后缀 如：.mp4
     * <p>
     *     * @param video_path    视频的本地路径
     */
    public void upVideo(Context context, final OssUpCallback ossUpCallback, final String video_name, String video_path) {
        getOSs(context);
        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME, OBJECT_NAME_VIDEO + "/" + video_name, video_path);

        putObjectRequest.setProgressCallback(new OSSProgressCallback() {

            @Override
            public void onProgress(Object request, long currentSize, long totalSize) {
                ossUpCallback.inProgress(currentSize, totalSize);
            }

        });
        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback() {

            @Override
            public void onSuccess(OSSRequest request, OSSResult result) {
                ossUpCallback.successVideo(oss.presignPublicObjectURL(P_BUCKETNAME, OBJECT_NAME_VIDEO + "/" + video_name));
            }

            @Override
            public void onFailure(OSSRequest request, ClientException clientException, ServiceException serviceException) {
                ossUpCallback.failureVideo(clientException.toString());
            }

        });
    }

    public interface OssUpCallback {

        void successImg(String img_url);

        void failureImg(String error);

        void successVideo(String video_url);

        void failureVideo(String error);

        void inProgress(long progress, long zong);

    }
}


