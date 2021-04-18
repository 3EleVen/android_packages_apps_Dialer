/* Decompiler 10ms, total 352ms, lines 24 */
package com.sudamod.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class OtherUtils {
   public static boolean isWiFiActive(Context ctx) {
      ConnectivityManager connectivity = (ConnectivityManager)ctx.getSystemService("connectivity");
      if (connectivity != null) {
         NetworkInfo[] info = connectivity.getAllNetworkInfo();
         if (info != null) {
            for(int i = 0; i < info.length; ++i) {
               if (info[i].getType() == 1 && info[i].isConnected()) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}