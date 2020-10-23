package com.wallet.retrofit;


import com.cao.commons.bean.chat.BasicResponse;
import com.cao.commons.bean.file.Ad;
import com.cao.commons.bean.file.Article;
import com.cao.commons.bean.file.Book;
import com.cao.commons.bean.file.DownloadFile;
import com.cao.commons.bean.file.FileListInfo;
import com.cao.commons.bean.file.Pdf;
import com.cao.commons.bean.file.SearchFileResult;
import com.cao.commons.bean.file.Video;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 接口请求封装
 */
public interface FileApiService {

    @FormUrlEncoded
    @POST("api/getAds")
    Observable<BasicResponse<ArrayList<Ad>>> getAds(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("api/getVideo")
    Observable<BasicResponse<ArrayList<Video>>> getVideo(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("api/getTjSchool")
    Observable<BasicResponse<FileListInfo>> getTjSchool(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("api/getBook")
    Observable<BasicResponse<ArrayList<Book>>> getBook(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("api/getDownloads")
    Observable<BasicResponse<ArrayList<DownloadFile>>> getDownloads(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("api/getArticle")
    Observable<BasicResponse<Article>> getArticle(@Field("id") int id, @Field("lang") String lang);

    @FormUrlEncoded
    @POST("api/getLive")
    Observable<BasicResponse<FileListInfo>> getLive(@Field("lang") String lang,
                                                    @Field("page") int page);

    @FormUrlEncoded
    @POST("api/video")
    Observable<BasicResponse<FileListInfo>> businessSchoolVideo(@Field("lang") String lang,
                                                                @Field("page") int page);

    @FormUrlEncoded
    @POST("api/getBookFile")
    Observable<BasicResponse<ArrayList<Book>>> getBookFile(@Field("lang") String lang,
                                                           @Field("page") int page,
                                                           @Field("type") int type);

    @FormUrlEncoded
    @POST("api/videoFile")
    Observable<BasicResponse<ArrayList<DownloadFile>>> getDownloadFile(@Field("lang") String lang,
                                                                       @Field("page") int page);

    @FormUrlEncoded
    @POST("api/getPdfs")
    Observable<BasicResponse<Pdf>> getPdfs(@Field("lang") String lang,
                                           @Field("page") int page);

    @FormUrlEncoded
    @POST("api/search")
    Observable<BasicResponse<SearchFileResult>> search(@Field("lang") String lang,
                                                       @Field("keyword") String keyword,
                                                       @Field("page") int page);

    @FormUrlEncoded
    @POST("api/getVinfo")
    Observable<BasicResponse<Video>> getVinfo(@Field("lang") String lang,
                                              @Field("id") long id);

    @FormUrlEncoded
    @POST("api/getPinfo")
    Observable<BasicResponse<Book>> getPinfo(@Field("lang") String lang,
                                             @Field("id") long id);
}