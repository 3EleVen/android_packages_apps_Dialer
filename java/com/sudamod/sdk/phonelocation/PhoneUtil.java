/* Decompiler 52ms, total 720ms, lines 257 */
package com.sudamod.sdk.phonelocation;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.suda.location.PhoneLocation;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.sudamod.sdk.utils.NetUtil;
import com.sudamod.sdk.utils.NetUtil.NetCallBack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PhoneUtil {
   private ContentResolver mCr;
   private Context mContext;
   private Uri mUri;
   private List<String> mQueueList;
   private Map<String, PhoneInfoBean> mMapCache;
   private static PhoneUtil mPu;
   private static final int MARK_TYPE_CUSTOM_EMPTY = -1;
   private static final int MARK_TYPE_NONE = 0;
   private static final int MARK_TYPE_CUSTOM = 1;
   private static final int MARK_TYPE_COMMON = 2;
   private static final int MARK_TYPE_CRANK_CALL = 3;
   private static final int MARK_TYPE_FRAUD_CALL = 4;
   private static final int MARK_TYPE_ADV_CALL = 5;
   private static final String ERROR = "ERROR";
   private static final String AUTHORITY = "content://com.suda.provider.PhoneLocation/phonelocation";
   private static final String API = "http://data.haoma.sogou.com/vrapi/query_number.php?number=";

   public static PhoneUtil getPhoneUtil(Context ct) {
      if (mPu == null) {
         Class var1 = PhoneUtil.class;
         synchronized(PhoneUtil.class) {
            if (mPu == null) {
               mPu = new PhoneUtil(ct);
            }
         }
      }

      return mPu;
   }

   private PhoneUtil(Context ct) {
      this.mContext = ct.getApplicationContext();
      this.mCr = this.mContext.getContentResolver();
      this.mUri = Uri.parse("content://com.suda.provider.PhoneLocation/phonelocation");
      this.mQueueList = new ArrayList();
      this.mMapCache = new ArrayMap();
      this.initData();
   }

   @Deprecated
   public synchronized String getLocalNumberInfo(String phoneNumber) {
      return this.getLocalNumberInfo(phoneNumber, true);
   }

   public String getPhoneCode(String phoneNumber) {
      phoneNumber = phoneNumber == null ? "" : phoneNumber;
      return PhoneLocation.getCodeFromPhone(phoneNumber);
   }

   public synchronized String getLocalNumberInfo(String phoneNumber, boolean useMapCache) {
      phoneNumber = phoneNumber == null ? "" : phoneNumber;
      String numberFormat = phoneNumber.replaceAll("(?:-| )", "").replace("+86", "");
      if (this.mMapCache.get(numberFormat) != null && useMapCache) {
         if (this.isNeedToUpdate(numberFormat)) {
            this.insertOrUpdate(numberFormat, this.getRequestUrl(numberFormat), true);
         }

         return ((PhoneInfoBean)this.mMapCache.get(numberFormat)).getPhoneMark();
      } else if (this.getLocalData(numberFormat)) {
         return ((PhoneInfoBean)this.mMapCache.get(numberFormat)).getPhoneMark();
      } else if (this.mQueueList.contains(numberFormat)) {
         return PhoneLocation.getCityFromPhone(numberFormat);
      } else {
         this.mQueueList.add(numberFormat);
         this.insertOrUpdate(numberFormat, this.getRequestUrl(numberFormat), false);
         return PhoneLocation.getCityFromPhone(numberFormat);
      }
   }

   public void getOnlineNumberInfo(String phoneNumber, final PhoneUtil.CallBack callBack) {
      phoneNumber = phoneNumber == null ? "" : phoneNumber;
      final String numberFormat = phoneNumber.replaceAll("(?:-| )", "").replace("+86", "");
      if (this.mMapCache.get(numberFormat) != null) {
         callBack.execute(((PhoneInfoBean)this.mMapCache.get(numberFormat)).getPhoneMark());
      } else if (this.getLocalData(numberFormat)) {
         callBack.execute(((PhoneInfoBean)this.mMapCache.get(numberFormat)).getPhoneMark());
      } else {
         NetUtil.getPhoneLocationFromNet(this.getRequestUrl(numberFormat), new NetCallBack() {
            public void execute(String response) {
               if ("ERROR".equals(PhoneUtil.this.getMark(response))) {
                  callBack.execute("");
                  PhoneUtil.this.mQueueList.remove(numberFormat);
               } else if (!TextUtils.isEmpty(PhoneUtil.this.getMark(response))) {
                  callBack.execute(PhoneUtil.this.getMark(response));
                  if (!PhoneUtil.this.mQueueList.contains(numberFormat)) {
                     PhoneUtil.this.mQueueList.add(numberFormat);
                     PhoneUtil.this.insertOrUpdateDb(numberFormat, PhoneUtil.this.getMark(response), PhoneUtil.this.getMarkType(PhoneUtil.this.getMark(response)), false);
                  }

               } else {
                  callBack.execute(PhoneLocation.getCityFromPhone(numberFormat));
                  if (!PhoneUtil.this.mQueueList.contains(numberFormat)) {
                     PhoneUtil.this.mQueueList.add(numberFormat);
                     PhoneUtil.this.insertOrUpdateDb(numberFormat, PhoneLocation.getCityFromPhone(numberFormat), 0, false);
                  }

               }
            }

            public void error() {
               callBack.execute(PhoneLocation.getCityFromPhone(numberFormat));
               PhoneUtil.this.mQueueList.remove(numberFormat);
            }
         });
      }
   }

   public void customMark(String phoneNumber, String mark, int markType) {
      String numberFormat = phoneNumber.replaceAll("(?:-| )", "").replace("+86", "");
      boolean isMarkEmpty = TextUtils.isEmpty(mark);
      this.insertOrUpdateDb(numberFormat, isMarkEmpty ? PhoneLocation.getCityFromPhone(numberFormat) : mark, isMarkEmpty ? -1 : 1, this.getLocalData(numberFormat));
   }

   private void insertOrUpdate(final String phoneNumber, String url, final boolean update) {
      NetUtil.getPhoneLocationFromNet(url, new NetCallBack() {
         public void execute(String response) {
            if ("ERROR".equals(PhoneUtil.this.getMark(response))) {
               PhoneUtil.this.mQueueList.remove(phoneNumber);
            } else if (!TextUtils.isEmpty(PhoneUtil.this.getMark(response))) {
               PhoneUtil.this.insertOrUpdateDb(phoneNumber, PhoneUtil.this.getMark(response), PhoneUtil.this.getMarkType(PhoneUtil.this.getMark(response)), update);
            } else {
               PhoneUtil.this.insertOrUpdateDb(phoneNumber, PhoneLocation.getCityFromPhone(phoneNumber), 0, update);
            }

         }

         public void error() {
            if (update) {
               PhoneUtil.this.mQueueList.remove(phoneNumber);
            }

         }
      });
   }

   private void insertOrUpdateDb(String phoneNumber, String location, int markType, boolean update) {
      Long last_time = System.currentTimeMillis();
      ContentValues values = new ContentValues();
      values.put("phone_number", phoneNumber);
      values.put("phone_location", location);
      values.put("last_update", last_time);
      values.put("mark_type", markType);
      if (update) {
         this.mCr.update(this.mUri, values, "phone_number=?", new String[]{phoneNumber});
      } else {
         this.mCr.insert(this.mUri, values);
      }

      this.mMapCache.put(phoneNumber, new PhoneInfoBean(phoneNumber, location, last_time, markType));
   }

   private boolean getLocalData(String phoneNumber) {
      Cursor c = null;

      boolean var4;
      try {
         c = this.mCr.query(this.mUri, (String[])null, "phone_number=?", new String[]{phoneNumber}, (String)null);
         boolean var3;
         if (c.moveToFirst()) {
            this.mMapCache.put(c.getString(1), new PhoneInfoBean(c.getString(1), c.getString(2), c.getLong(3), c.getInt(4)));
            var3 = true;
            return var3;
         }

         var3 = false;
         return var3;
      } catch (Exception var8) {
         var8.printStackTrace();
         var4 = false;
      } finally {
         if (c != null) {
            c.close();
         }

      }

      return var4;
   }

   private void initData() {
      Cursor c = null;

      try {
         c = this.mCr.query(this.mUri, (String[])null, "last_update > " + (System.currentTimeMillis() - 604800000L), (String[])null, (String)null);

         while(c.moveToNext()) {
            this.mMapCache.put(c.getString(1), new PhoneInfoBean(c.getString(1), c.getString(2), c.getLong(3), c.getInt(4)));
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      } finally {
         if (c != null) {
            c.close();
         }

      }

   }

   private String getMark(String response) {
      try {
         JSONObject jo = new JSONObject(response.subSequence(5, response.length() - 1).toString());
         if (jo.getString("NumInfo").equals("该号码暂无标记")) {
            return "";
         } else {
            String[] result = jo.getString("NumInfo").split("：");
            return result[1];
         }
      } catch (JSONException var4) {
         return "ERROR";
      }
   }

   private boolean isNeedToUpdate(String phoneNumber) {
      return ((PhoneInfoBean)this.mMapCache.get(phoneNumber)).getLastUpdateAt() + 259200000L < System.currentTimeMillis() && ((PhoneInfoBean)this.mMapCache.get(phoneNumber)).getMarkType() != 1 || ((PhoneInfoBean)this.mMapCache.get(phoneNumber)).getMarkType() == -1;
   }

   private int getMarkType(String mark) {
      if ("骚扰电话".equals(mark)) {
         return 3;
      } else if (!"诈骗电话".equals(mark) && !"诈骗".equals(mark)) {
         return "广告推销".equals(mark) ? 5 : 2;
      } else {
         return 4;
      }
   }

   private String getRequestUrl(String number) {
      StringBuilder builder = new StringBuilder();
      builder.append("http://data.haoma.sogou.com/vrapi/query_number.php?number=").append(number).append("&type=json&callback=show");
      return builder.toString();
   }

   public interface CallBack {
      void execute(String var1);
   }
}