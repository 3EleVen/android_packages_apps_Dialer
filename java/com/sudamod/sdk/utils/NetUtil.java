/* Decompiler 6ms, total 417ms, lines 46 */
package com.sudamod.sdk.utils;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Request.Builder;

public class NetUtil {
   public static void getPhoneLocationFromNet(String url, final NetUtil.NetCallBack callback) {
      OkHttpClient mOkHttpClient = new OkHttpClient();
      Request request = (new Builder()).url(url).build();
      Call call = mOkHttpClient.newCall(request);
      call.enqueue(new Callback() {
         public void onFailure(Call call, IOException e) {
            callback.error();
         }

         public void onResponse(Call call, Response response) throws IOException {
            callback.execute(response.body().string());
         }
      });
   }

   public static Response getPhoneLocationFromNet(String url) {
      OkHttpClient mOkHttpClient = new OkHttpClient();
      Request request = (new Builder()).url(url).build();
      Call call = mOkHttpClient.newCall(request);

      try {
         return call.execute();
      } catch (IOException var5) {
         var5.printStackTrace();
         return null;
      }
   }

   public interface NetCallBack {
      void execute(String var1);

      void error();
   }
}