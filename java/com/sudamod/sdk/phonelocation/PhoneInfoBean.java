/* Decompiler 14ms, total 353ms, lines 48 */
package com.sudamod.sdk.phonelocation;

public class PhoneInfoBean {
   private String number;
   private String phoneMark;
   private int markType;
   private long lastUpdateAt;

   public PhoneInfoBean(String number, String phoneMark, long lastUpdateAt, int markType) {
      this.number = number;
      this.phoneMark = phoneMark;
      this.lastUpdateAt = lastUpdateAt;
      this.markType = markType;
   }

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public String getPhoneMark() {
      return this.phoneMark;
   }

   public void setPhoneMark(String phoneMark) {
      this.phoneMark = phoneMark;
   }

   public int getMarkType() {
      return this.markType;
   }

   public void setMarkType(int markType) {
      this.markType = markType;
   }

   public long getLastUpdateAt() {
      return this.lastUpdateAt;
   }

   public void setLastUpdateAt(long lastUpdateAt) {
      this.lastUpdateAt = lastUpdateAt;
   }
}