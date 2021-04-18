/* Decompiler 1ms, total 319ms, lines 10 */
package com.sudamod.sdk.utils;

import android.suda.utils.SudaUtils;

public class LanguagesUtils {
   public static boolean isZh(boolean excludeTW) {
      return SudaUtils.isSupportLanguage(excludeTW);
   }
}