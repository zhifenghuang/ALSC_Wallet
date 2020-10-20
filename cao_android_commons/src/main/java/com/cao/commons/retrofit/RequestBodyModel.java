package com.cao.commons.retrofit;

import com.cao.commons.util.GsonUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class RequestBodyModel {

    public static RequestBody getRequestBody(Object requestModel) {

        RequestBody requestBody =
                RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"), GsonUtils.objectToJson(requestModel));

        return requestBody;

    }

    public static RequestBody getRequestImageBody(String imagePath) {

        String[] str = imagePath.split("/");

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(
                        "image",
                        str[str.length - 1],
                        RequestBody.create(MediaType.parse("image/*"), new File(imagePath))).build();
        return requestBody;
    }

    public static RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }
}
