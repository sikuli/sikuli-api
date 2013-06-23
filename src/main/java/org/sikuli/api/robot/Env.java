/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.api.robot;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

public class Env {

   public static String getOSVersion(){
      return System.getProperty("os.version");
   }

   public static OS getOS(){
      String os = System.getProperty("os.name").toLowerCase();
      if( os.startsWith("mac os x") )
         return OS.MAC;
      else if( os.startsWith("windows"))
         return OS.WINDOWS;
      else if( os.startsWith("linux"))
         return OS.LINUX;
      return OS.NOT_SUPPORTED;
   }

   public static boolean isWindows(){
      return getOS() == OS.WINDOWS;
   }

   public static boolean isLinux(){
      return getOS() == OS.LINUX;
   }

   public static boolean isMac(){
      return getOS() == OS.MAC;
   }

   public static String getSeparator(){
      if(isWindows())
         return ";";
      return ":";
   }


   public static boolean isLockOn(char key){
      Toolkit tk = Toolkit.getDefaultToolkit();
      switch(key){
         case '\ue025': return tk.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
         case '\ue027': return tk.getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
         case '\ue03B': return tk.getLockingKeyState(KeyEvent.VK_NUM_LOCK);
         default:
            return false;
      }
   }

   public static int getHotkeyModifier(){
      if(getOS() == OS.MAC)
         return KeyEvent.VK_META;
      else
         return KeyEvent.VK_CONTROL;
   }

}
