/* Decompiler 352ms, total 2656ms, lines 988 */
package com.sudamod.sdk.utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtils {
   private static final String FOLDER_SEPARATOR = "/";
   private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
   private static final String TOP_PATH = "..";
   private static final String CURRENT_PATH = ".";
   private static final char EXTENSION_SEPARATOR = '.';
   private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

   public static boolean hasLength(CharSequence str) {
      return str != null && str.length() > 0;
   }

   public static boolean hasLength(String str) {
      return hasLength((CharSequence)str);
   }

   public static boolean isNotEmpty(CharSequence text) {
      return text != null && text.length() > 0;
   }

   public static boolean isEmpty(CharSequence text) {
      return text == null || text.length() == 0;
   }

   public static boolean isBlank(CharSequence cs) {
      int strLen;
      if (cs != null && (strLen = cs.length()) != 0) {
         for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(cs.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public static boolean hasText(CharSequence str) {
      if (!hasLength(str)) {
         return false;
      } else {
         int strLen = str.length();

         for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean hasText(String str) {
      return hasText((CharSequence)str);
   }

   public static boolean containsWhitespace(CharSequence str) {
      if (!hasLength(str)) {
         return false;
      } else {
         int strLen = str.length();

         for(int i = 0; i < strLen; ++i) {
            if (Character.isWhitespace(str.charAt(i))) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean containsWhitespace(String str) {
      return containsWhitespace((CharSequence)str);
   }

   public static String deleteWhitespace(String str) {
      if (isEmpty((CharSequence)str)) {
         return str;
      } else {
         int sz = str.length();
         char[] chs = new char[sz];
         int count = 0;

         for(int i = 0; i < sz; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
               chs[count++] = str.charAt(i);
            }
         }

         if (count == sz) {
            return str;
         } else {
            return new String(chs, 0, count);
         }
      }
   }

   public static String trimWhitespace(String str) {
      if (!hasLength(str)) {
         return str;
      } else {
         StringBuilder sb = new StringBuilder(str);

         while(sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
         }

         while(sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
         }

         return sb.toString();
      }
   }

   public static String trim(String text) {
      return text == null ? null : text.trim();
   }

   public static String trimAllWhitespace(String str) {
      if (!hasLength(str)) {
         return str;
      } else {
         StringBuilder sb = new StringBuilder(str);
         int index = 0;

         while(sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
               sb.deleteCharAt(index);
            } else {
               ++index;
            }
         }

         return sb.toString();
      }
   }

   public static String reduceLineBreaks(String text) {
      if (isEmpty((CharSequence)text)) {
         return text;
      } else {
         String textTrimed = trimWhitespace(text);
         return textTrimed.replaceAll("(\\r\\n|\\r|\\n)+", "\n");
      }
   }

   public static String replaceLineBreaks(String text) {
      return isEmpty((CharSequence)text) ? text : text.replaceAll("\\r\\n", "  ").replaceAll("\\n", " ");
   }

   public static String trimLeadingWhitespace(String str) {
      if (!hasLength(str)) {
         return str;
      } else {
         StringBuilder sb = new StringBuilder(str);

         while(sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
         }

         return sb.toString();
      }
   }

   public static String trimTrailingWhitespace(String str) {
      if (!hasLength(str)) {
         return str;
      } else {
         StringBuilder sb = new StringBuilder(str);

         while(sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
         }

         return sb.toString();
      }
   }

   public static String trimLeadingCharacter(String str, char leadingCharacter) {
      if (!hasLength(str)) {
         return str;
      } else {
         StringBuilder sb = new StringBuilder(str);

         while(sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
            sb.deleteCharAt(0);
         }

         return sb.toString();
      }
   }

   public static String trimTrailingCharacter(String str, char trailingCharacter) {
      if (!hasLength(str)) {
         return str;
      } else {
         StringBuilder sb = new StringBuilder(str);

         while(sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
            sb.deleteCharAt(sb.length() - 1);
         }

         return sb.toString();
      }
   }

   public static boolean startsWithIgnoreCase(String str, String prefix) {
      if (str != null && prefix != null) {
         if (str.startsWith(prefix)) {
            return true;
         } else if (str.length() < prefix.length()) {
            return false;
         } else {
            String lcStr = str.substring(0, prefix.length()).toLowerCase();
            String lcPrefix = prefix.toLowerCase();
            return lcStr.equals(lcPrefix);
         }
      } else {
         return false;
      }
   }

   public static boolean endsWithIgnoreCase(String str, String suffix) {
      if (str != null && suffix != null) {
         if (str.endsWith(suffix)) {
            return true;
         } else if (str.length() < suffix.length()) {
            return false;
         } else {
            String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
            String lcSuffix = suffix.toLowerCase();
            return lcStr.equals(lcSuffix);
         }
      } else {
         return false;
      }
   }

   public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
      for(int j = 0; j < substring.length(); ++j) {
         int i = index + j;
         if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
            return false;
         }
      }

      return true;
   }

   public static int countOccurrencesOf(String str, String sub) {
      if (str != null && sub != null && str.length() != 0 && sub.length() != 0) {
         int count = 0;

         int idx;
         for(int pos = 0; (idx = str.indexOf(sub, pos)) != -1; pos = idx + sub.length()) {
            ++count;
         }

         return count;
      } else {
         return 0;
      }
   }

   public static String replace(String inString, String oldPattern, String newPattern) {
      if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
         StringBuilder sb = new StringBuilder();
         int pos = 0;
         int index = inString.indexOf(oldPattern);

         for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
            sb.append(inString.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
         }

         sb.append(inString.substring(pos));
         return sb.toString();
      } else {
         return inString;
      }
   }

   public static String delete(String inString, String pattern) {
      return replace(inString, pattern, "");
   }

   public static String deleteAny(String inString, String charsToDelete) {
      if (hasLength(inString) && hasLength(charsToDelete)) {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < inString.length(); ++i) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
               sb.append(c);
            }
         }

         return sb.toString();
      } else {
         return inString;
      }
   }

   public static String quote(String str) {
      return str != null ? "'" + str + "'" : null;
   }

   public static Object quoteIfString(Object obj) {
      return obj instanceof String ? quote((String)obj) : obj;
   }

   public static String unqualify(String qualifiedName) {
      return unqualify(qualifiedName, '.');
   }

   public static String unqualify(String qualifiedName, char separator) {
      return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
   }

   public static String capitalize(String str) {
      return changeFirstCharacterCase(str, true);
   }

   public static String uncapitalize(String str) {
      return changeFirstCharacterCase(str, false);
   }

   private static String changeFirstCharacterCase(String str, boolean capitalize) {
      if (str != null && str.length() != 0) {
         StringBuilder sb = new StringBuilder(str.length());
         if (capitalize) {
            sb.append(Character.toUpperCase(str.charAt(0)));
         } else {
            sb.append(Character.toLowerCase(str.charAt(0)));
         }

         sb.append(str.substring(1));
         return sb.toString();
      } else {
         return str;
      }
   }

   public static String getFilename(String path) {
      if (path == null) {
         return null;
      } else {
         int separatorIndex = path.lastIndexOf("/");
         return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
      }
   }

   public static String getFilenameExtension(String path) {
      if (path == null) {
         return null;
      } else {
         int extIndex = path.lastIndexOf(46);
         if (extIndex == -1) {
            return null;
         } else {
            int folderIndex = path.lastIndexOf("/");
            return folderIndex > extIndex ? null : path.substring(extIndex + 1);
         }
      }
   }

   public static String stripFilenameExtension(String path) {
      if (path == null) {
         return null;
      } else {
         int extIndex = path.lastIndexOf(46);
         if (extIndex == -1) {
            return path;
         } else {
            int folderIndex = path.lastIndexOf("/");
            return folderIndex > extIndex ? path : path.substring(0, extIndex);
         }
      }
   }

   public static String applyRelativePath(String path, String relativePath) {
      int separatorIndex = path.lastIndexOf("/");
      if (separatorIndex != -1) {
         String newPath = path.substring(0, separatorIndex);
         if (!relativePath.startsWith("/")) {
            newPath = newPath + "/";
         }

         return newPath + relativePath;
      } else {
         return relativePath;
      }
   }

   public static String cleanPath(String path) {
      if (path == null) {
         return null;
      } else {
         String pathToUse = replace(path, "\\", "/");
         int prefixIndex = pathToUse.indexOf(58);
         String prefix = "";
         if (prefixIndex != -1) {
            prefix = pathToUse.substring(0, prefixIndex + 1);
            pathToUse = pathToUse.substring(prefixIndex + 1);
         }

         if (pathToUse.startsWith("/")) {
            prefix = prefix + "/";
            pathToUse = pathToUse.substring(1);
         }

         String[] pathArray = delimitedListToStringArray(pathToUse, "/");
         List<String> pathElements = new LinkedList();
         int tops = 0;

         int i;
         for(i = pathArray.length - 1; i >= 0; --i) {
            String element = pathArray[i];
            if (!".".equals(element)) {
               if ("..".equals(element)) {
                  ++tops;
               } else if (tops > 0) {
                  --tops;
               } else {
                  pathElements.add(0, element);
               }
            }
         }

         for(i = 0; i < tops; ++i) {
            pathElements.add(0, "..");
         }

         return prefix + toString((Collection)pathElements, "/");
      }
   }

   public static boolean pathEquals(String path1, String path2) {
      return cleanPath(path1).equals(cleanPath(path2));
   }

   public static Locale parseLocaleString(String localeString) {
      String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
      String language = parts.length > 0 ? parts[0] : "";
      String country = parts.length > 1 ? parts[1] : "";
      validateLocalePart(language);
      validateLocalePart(country);
      String variant = "";
      if (parts.length >= 2) {
         int endIndexOfCountryCode = localeString.indexOf(country) + country.length();
         variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
         if (variant.startsWith("_")) {
            variant = trimLeadingCharacter(variant, '_');
         }
      }

      return language.length() > 0 ? new Locale(language, country, variant) : null;
   }

   private static void validateLocalePart(String localePart) {
      for(int i = 0; i < localePart.length(); ++i) {
         char ch = localePart.charAt(i);
         if (ch != '_' && ch != ' ' && !Character.isLetterOrDigit(ch)) {
            throw new IllegalArgumentException("Locale part \"" + localePart + "\" contains invalid characters");
         }
      }

   }

   public static String toLanguageTag(Locale locale) {
      return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
   }

   public static String[] addStringToArray(String[] array, String str) {
      if (isEmpty((Object[])array)) {
         return new String[]{str};
      } else {
         String[] newArr = new String[array.length + 1];
         System.arraycopy(array, 0, newArr, 0, array.length);
         newArr[array.length] = str;
         return newArr;
      }
   }

   public static String[] concatenateStringArrays(String[] array1, String[] array2) {
      if (isEmpty((Object[])array1)) {
         return array2;
      } else if (isEmpty((Object[])array2)) {
         return array1;
      } else {
         String[] newArr = new String[array1.length + array2.length];
         System.arraycopy(array1, 0, newArr, 0, array1.length);
         System.arraycopy(array2, 0, newArr, array1.length, array2.length);
         return newArr;
      }
   }

   public static String[] mergeStringArrays(String[] array1, String[] array2) {
      if (isEmpty((Object[])array1)) {
         return array2;
      } else if (isEmpty((Object[])array2)) {
         return array1;
      } else {
         List<String> result = new ArrayList();
         result.addAll(Arrays.asList(array1));
         String[] arr$ = array2;
         int len$ = array2.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String str = arr$[i$];
            if (!result.contains(str)) {
               result.add(str);
            }
         }

         return toStringArray((Collection)result);
      }
   }

   public static String[] sortStringArray(String[] array) {
      if (isEmpty((Object[])array)) {
         return new String[0];
      } else {
         Arrays.sort(array);
         return array;
      }
   }

   public static String[] toStringArray(Collection<String> collection) {
      return collection == null ? null : (String[])collection.toArray(new String[collection.size()]);
   }

   public static String[] toStringArray(Enumeration<String> enumeration) {
      if (enumeration == null) {
         return null;
      } else {
         List<String> list = Collections.list(enumeration);
         return (String[])list.toArray(new String[list.size()]);
      }
   }

   public static String[] trimArrayElements(String[] array) {
      if (isEmpty((Object[])array)) {
         return new String[0];
      } else {
         String[] result = new String[array.length];

         for(int i = 0; i < array.length; ++i) {
            String element = array[i];
            result[i] = element != null ? element.trim() : null;
         }

         return result;
      }
   }

   public static String[] removeDuplicateStrings(String[] array) {
      if (isEmpty((Object[])array)) {
         return array;
      } else {
         Set<String> set = new TreeSet();
         Collections.addAll(set, array);
         return toStringArray((Collection)set);
      }
   }

   public static String[] split(String toSplit, String delimiter) {
      if (hasLength(toSplit) && hasLength(delimiter)) {
         int offset = toSplit.indexOf(delimiter);
         if (offset < 0) {
            return null;
         } else {
            String beforeDelimiter = toSplit.substring(0, offset);
            String afterDelimiter = toSplit.substring(offset + delimiter.length());
            return new String[]{beforeDelimiter, afterDelimiter};
         }
      } else {
         return null;
      }
   }

   public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
      return splitArrayElementsIntoProperties(array, delimiter, (String)null);
   }

   public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete) {
      if (isEmpty((Object[])array)) {
         return null;
      } else {
         Properties result = new Properties();
         String[] arr$ = array;
         int len$ = array.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String element = arr$[i$];
            if (charsToDelete != null) {
               element = deleteAny(element, charsToDelete);
            }

            String[] splittedElement = split(element, delimiter);
            if (splittedElement != null) {
               result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
            }
         }

         return result;
      }
   }

   public static String[] tokenizeToStringArray(String str, String delimiters) {
      return tokenizeToStringArray(str, delimiters, true, true);
   }

   public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
      if (str == null) {
         return null;
      } else {
         StringTokenizer st = new StringTokenizer(str, delimiters);
         ArrayList tokens = new ArrayList();

         while(true) {
            String token;
            do {
               if (!st.hasMoreTokens()) {
                  return toStringArray((Collection)tokens);
               }

               token = st.nextToken();
               if (trimTokens) {
                  token = token.trim();
               }
            } while(ignoreEmptyTokens && token.length() <= 0);

            tokens.add(token);
         }
      }
   }

   public static String[] delimitedListToStringArray(String str, String delimiter) {
      return delimitedListToStringArray(str, delimiter, (String)null);
   }

   public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
      if (str == null) {
         return new String[0];
      } else if (delimiter == null) {
         return new String[]{str};
      } else {
         List<String> result = new ArrayList();
         int pos;
         if ("".equals(delimiter)) {
            for(pos = 0; pos < str.length(); ++pos) {
               result.add(deleteAny(str.substring(pos, pos + 1), charsToDelete));
            }
         } else {
            int delPos;
            for(pos = 0; (delPos = str.indexOf(delimiter, pos)) != -1; pos = delPos + delimiter.length()) {
               result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
            }

            if (str.length() > 0 && pos <= str.length()) {
               result.add(deleteAny(str.substring(pos), charsToDelete));
            }
         }

         return toStringArray((Collection)result);
      }
   }

   public static String[] commaDelimitedListToStringArray(String str) {
      return delimitedListToStringArray(str, ",");
   }

   public static Set<String> commaDelimitedListToSet(String str) {
      Set<String> set = new TreeSet();
      String[] tokens = commaDelimitedListToStringArray(str);
      Collections.addAll(set, tokens);
      return set;
   }

   public static String toString(Collection<?> coll, String delim, String prefix, String suffix) {
      if (coll != null && !coll.isEmpty()) {
         StringBuilder sb = new StringBuilder();
         Iterator it = coll.iterator();

         while(it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
               sb.append(delim);
            }
         }

         return sb.toString();
      } else {
         return "";
      }
   }

   public static <K, V> String toString(Map<K, V> map, String delim, String prefix, String suffix) {
      if (map != null && !map.isEmpty()) {
         Set<Entry<K, V>> items = map.entrySet();
         StringBuilder sb = new StringBuilder();
         Iterator<Entry<K, V>> it = items.iterator();
         ArrayList strings = new ArrayList();

         while(it.hasNext()) {
            Entry<K, V> entry = (Entry)it.next();
            strings.add(entry.getKey() + "=" + entry.getValue());
         }

         Collections.sort(strings);
         ListIterator it2 = strings.listIterator();

         while(it2.hasNext()) {
            sb.append(prefix).append((String)it2.next()).append(suffix);
            if (it2.hasNext()) {
               sb.append(delim);
            }
         }

         return sb.toString();
      } else {
         return "";
      }
   }

   public static String toString(Collection<?> coll, String delim) {
      return toString(coll, delim, "", "");
   }

   public static <K, V> String toString(Map<K, V> map, String delim) {
      return toString(map, delim, "", "");
   }

   public static String toString(Collection<?> coll) {
      return toString(coll, ",");
   }

   public static String toString(Bundle bundle) {
      if (bundle != null && !bundle.isEmpty()) {
         StringBuilder builder = new StringBuilder();
         List<String> keys = new ArrayList(bundle.keySet());
         Collections.sort(keys);
         builder.append("{");
         Iterator i$ = keys.iterator();

         while(i$.hasNext()) {
            String key = (String)i$.next();
            builder.append(key).append(" = ").append(bundle.get(key)).append(", ");
         }

         builder.append("}");
         return builder.toString();
      } else {
         return "{}";
      }
   }

   public static String toString(SharedPreferences sp) {
      Map<String, String> map = new HashMap();
      Map<String, ?> sps = sp.getAll();
      List<String> keys = new ArrayList(sps.keySet());
      Collections.sort(keys);
      Iterator i$ = keys.iterator();

      while(i$.hasNext()) {
         String key = (String)i$.next();
         Object object = sps.get(key);
         String value = String.valueOf(object);
         map.put(key, value);
      }

      return toString((Map)map, ",");
   }

   public static String toString(Intent intent) {
      StringBuilder builder = new StringBuilder();
      if (intent != null) {
         builder.append("Intent: {");
         builder.append("Action=").append(intent.getAction()).append(", ");
         builder.append("Data=").append(intent.getData()).append(", ");
         String categories = toString((Collection)intent.getCategories());
         builder.append("Categories=[").append(categories).append("], ");
         builder.append("Component=").append(intent.getComponent()).append(", ");
         builder.append("Type=").append(intent.getType()).append(", ");
         builder.append("Package=").append(intent.getPackage()).append(", ");
         Bundle bundle = intent.getExtras();
         if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator i$ = keys.iterator();

            while(i$.hasNext()) {
               String key = (String)i$.next();
               builder.append("Extra={").append(key).append("=").append(bundle.get(key)).append("}");
            }
         }

         builder.append("}");
      }

      return builder.toString();
   }

   public static <K, V> String toString(Map<K, V> map) {
      return toString(map, ",");
   }

   public static String toString(Object[] arr, String delim) {
      if (isEmpty(arr)) {
         return "";
      } else if (arr.length == 1) {
         return String.valueOf(arr[0]);
      } else {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < arr.length; ++i) {
            if (i > 0) {
               sb.append(delim);
            }

            sb.append(arr[i]);
         }

         return sb.toString();
      }
   }

   public static String toString(Object[] arr) {
      return toString(arr, ",");
   }

   public static String bytesToHex(byte[] bytes) {
      char[] hexChars = new char[bytes.length * 2];

      for(int j = 0; j < bytes.length; ++j) {
         int v = bytes[j] & 255;
         hexChars[j * 2] = HEX_DIGITS[v >>> 4];
         hexChars[j * 2 + 1] = HEX_DIGITS[v & 15];
      }

      return new String(hexChars);
   }

   public static byte[] hexToBytes(String text) {
      int len = text.length();
      byte[] data = new byte[len / 2];

      for(int i = 0; i < len; i += 2) {
         data[i / 2] = (byte)((Character.digit(text.charAt(i), 16) << 4) + Character.digit(text.charAt(i + 1), 16));
      }

      return data;
   }

   public static boolean nullSafeEquals(String text1, String text2) {
      return text1 != null && text2 != null ? text1.equals(text2) : false;
   }

   public static String toLowerCase(String text) {
      return text == null ? null : text.toLowerCase(Locale.US);
   }

   public static boolean nullSafeEqualsIgnoreCase(String text1, String text2) {
      if (text1 == null) {
         return text2 == null;
      } else {
         return text1.equalsIgnoreCase(text2);
      }
   }

   public static String safeSubString(String text, int end) {
      return safeSubString(text, 0, end);
   }

   public static String safeSubString(String text, int start, int end) {
      if (text != null && text.length() != 0) {
         if (start >= 0 && end >= 0 && end > start) {
            int len = end - start;
            return text.length() <= len ? text : text.substring(start, end);
         } else {
            return text;
         }
      } else {
         return text;
      }
   }

   public static String getHumanReadableByteCount(long bytes) {
      int unit = 1024;
      if (bytes < (long)unit) {
         return bytes + " B";
      } else {
         int exp = (int)(Math.log((double)bytes) / Math.log((double)unit));
         String pre = "KMGTPE".charAt(exp - 1) + "";
         return String.format("%.1f %sB", (double)bytes / Math.pow((double)unit, (double)exp), pre);
      }
   }

   public static boolean isEmpty(Object[] array) {
      return array == null || array.length == 0;
   }

   public static boolean isEmpty(Set set) {
      return set == null || set.size() == 0;
   }

   public static String toSafeFileName(String name) {
      int size = name.length();
      StringBuilder builder = new StringBuilder(size * 2);

      for(int i = 0; i < size; ++i) {
         char c = name.charAt(i);
         boolean valid = c >= 'a' && c <= 'z';
         valid = valid || c >= 'A' && c <= 'Z';
         valid = valid || c >= '0' && c <= '9';
         valid = valid || c == '_' || c == '-' || c == '.';
         if (valid) {
            builder.append(c);
         } else {
            builder.append('x');
            builder.append(Integer.toHexString(i));
         }
      }

      return builder.toString();
   }

   public static String join(Collection ls, String str) {
      StringBuilder stringBuilder = new StringBuilder();
      Iterator i$ = ls.iterator();

      while(i$.hasNext()) {
         Object obj = i$.next();
         stringBuilder.append(obj.toString()).append(str);
      }

      return stringBuilder.toString();
   }

   public static boolean isChinese(String s) {
      return test("[一-龥]", s);
   }

   public static boolean test(String regex, String body) {
      return test(regex, body, 0);
   }

   public static boolean test(String regex, String body, int flags) {
      return isEmpty((CharSequence)body) ? false : Pattern.compile(regex, flags).matcher(body).find();
   }

   public static String match(String regex, String body) {
      Matcher matcher = Pattern.compile(regex).matcher(body);
      return matcher.find() ? matcher.group(0) : null;
   }
}