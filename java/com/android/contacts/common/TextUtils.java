package com.android.contacts.common;;

import java.lang.Character.UnicodeScript;

public class TextUtils {
   private static final String a;

   public static boolean isChineseLetter(char var0) {
      return String.valueOf(var0).matches(a);
   }

   public static boolean isCJKLetter(char var0) {
      return UnicodeScript.of(var0) == UnicodeScript.HAN || UnicodeScript.of(var0) == UnicodeScript.HANGUL || UnicodeScript.of(var0) == UnicodeScript.HIRAGANA || UnicodeScript.of(var0) == UnicodeScript.KATAKANA;
   }

   static {
      String var10002 = a(a("ì\u0097~%ß½Q\u009a\u0097~(ÜìTêà"));
      boolean var10001 = true;
      a = var10002;
   }

   private static char[] a(String var0) {
      char[] var10000 = var0.toCharArray();
      if (var10000.length < 2) {
         var10000[0] = (char)(var10000[0] ^ 97);
      }

      return var10000;
   }

   private static String a(char[] var0) {
      int var10002 = var0.length;
      char[] var10001 = var0;
      int var10000 = var10002;

      for(int var1 = 0; var10000 > var1; ++var1) {
         char var10004 = var10001[var1];
         short var10005;
         switch(var1 % 7) {
         case 0:
            var10005 = 183;
            break;
         case 1:
            var10005 = 203;
            break;
         case 2:
            var10005 = 11;
            break;
         case 3:
            var10005 = 17;
            break;
         case 4:
            var10005 = 186;
            break;
         case 5:
            var10005 = 141;
            break;
         default:
            var10005 = 97;
         }

         var10001[var1] = (char)(var10004 ^ var10005);
      }

      return (new String(var10001)).intern();
   }
}
