package com.cao.commons.util;import java.util.Collection;/** * 集合操作工具类 * */public class CollectionUtils {    /**       * 判断集合是否为null或者0个元素       *       * @param c       * @return       */      public static boolean isNullOrEmpty(Collection c) {          if (null == c || c.isEmpty()) {              return true;          }          return false;      }      /**       * 判断数组是否为null或者0个元素       *       * @param objects       * @return       */      public static boolean isNullOrEmpty(Object[] objects) {          if (null == objects || objects.length < 1)              return true;          else              return false;      }}