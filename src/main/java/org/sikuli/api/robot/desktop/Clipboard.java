/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.api.robot.desktop;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Utility for changing and checking the contents of the system's clipboard.
 */
class Clipboard {

   public static final TextType HTML = new TextType("text/html");
   public static final TextType PLAIN = new TextType("text/plain");

   public static final Charset UTF8 = new Charset("UTF-8");
   public static final Charset UTF16 = new Charset("UTF-16");
   public static final Charset UNICODE = new Charset("unicode");
   public static final Charset US_ASCII = new Charset("US-ASCII");

   public static final TransferType READER = new TransferType(Reader.class);
   public static final TransferType INPUT_STREAM = new TransferType(InputStream.class);
   public static final TransferType CHAR_BUFFER = new TransferType(CharBuffer.class);
   public static final TransferType BYTE_BUFFER = new TransferType(ByteBuffer.class);

   private Clipboard() {
   }
   
   /**
    * Empty the current clipboard so that future attempts to fetch text will fail.
    */
   public static void clear() {
	   // from http://www.jroller.com/alexRuiz/entry/clearing_the_system_clipboard
	   getSystemClipboard().setContents(new Transferable() {
	        public DataFlavor[] getTransferDataFlavors() {
	            return new DataFlavor[0];
	          }

	          public boolean isDataFlavorSupported(DataFlavor flavor) {
	            return false;
	          }

	          public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
	            throw new UnsupportedFlavorException(flavor);
	          }
	        }, null);
   }
   
   /**
    * Get the contents of the clipboard as a String.
    * If they cannot be converted to a String or some other problem prevents the operation, return <code>null</code>
    * @return the clipboard contents as a String, or <code>null</code>
    */
   public static String getText() {
	   Transferable clipboardContents = getSystemClipboard().getContents(Clipboard.class);
	   DataFlavor[] flavors = clipboardContents.getTransferDataFlavors();
	   DataFlavor textFlavor = DataFlavor.selectBestTextFlavor(flavors);
	   Reader clipboardReader = null;
	   try {
		   clipboardReader = textFlavor.getReaderForText(clipboardContents);

		   // This block could be replaced with apache-commons-io's IOUtils
		   StringBuffer sb = new StringBuffer();
		   char[] cbuf = new char[4096];
		   int rcount = clipboardReader.read(cbuf);
		   while (rcount != -1) {
			   sb.append(cbuf, 0, rcount);
			   rcount = clipboardReader.read(cbuf);
		   }
		   return sb.toString();
	   } catch (UnsupportedFlavorException e) {
		   return null;  // got the clipboard, but it couldn't be made into text
	   } catch (IOException e) {
		   return null;  // misc error
	   } finally {
		   if (clipboardReader != null)
			   try {
				   clipboardReader.close();
			   } catch (IOException e) {}
	   }
   }

   /**
    * Dumps a given text (either String or StringBuffer) into the Clipboard, with a default MIME type
    */
   public static void putText(CharSequence data) {
      StringSelection copy = new StringSelection(data.toString());
      getSystemClipboard().setContents(copy, copy);
   }

   /**
    * Dumps a given text (either String or StringBuffer) into the Clipboard with a specified MIME type
    */
   public static void putText(TextType type, Charset charset, TransferType transferType, CharSequence data) {
      String mimeType = type + "; charset=" + charset + "; class=" + transferType;
      TextTransferable transferable = new TextTransferable(mimeType, data.toString());
      getSystemClipboard().setContents(transferable, transferable);
   }

   public static java.awt.datatransfer.Clipboard getSystemClipboard() {
      return Toolkit.getDefaultToolkit().getSystemClipboard();
   }

   private static class TextTransferable implements Transferable, ClipboardOwner {
      private String data;
      private DataFlavor flavor;

      public TextTransferable(String mimeType, String data) {
         flavor = new DataFlavor(mimeType, "Text");
         this.data = data;
      }

      public DataFlavor[] getTransferDataFlavors() {
         return new DataFlavor[]{flavor, DataFlavor.stringFlavor};
      }

      public boolean isDataFlavorSupported(DataFlavor flavor) {
         boolean b = this.flavor.getPrimaryType().equals(flavor.getPrimaryType());
         return b || flavor.equals(DataFlavor.stringFlavor);
      }

      public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
         if (flavor.isRepresentationClassInputStream()) {
            return new StringBufferInputStream(data);
         }
         else if (flavor.isRepresentationClassReader()) {
            return new StringReader(data);
         }
         else if (flavor.isRepresentationClassCharBuffer()) {
            return CharBuffer.wrap(data);
         }
         else if (flavor.isRepresentationClassByteBuffer()) {
            return ByteBuffer.wrap(data.getBytes());
         }
         else if (flavor.equals(DataFlavor.stringFlavor)){
            return data;
         }
         throw new UnsupportedFlavorException(flavor);
      }

      public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, Transferable contents) {
      }
   }

   /**
    * Enumeration for the text type property in MIME types
    */
   public static class TextType {
      private String type;

      private TextType(String type) {
         this.type = type;
      }

      public String toString() {
         return type;
      }
   }

   /**
    * Enumeration for the charset property in MIME types (UTF-8, UTF-16, etc.)
    */
   public static class Charset {
      private String name;

      private Charset(String name) {
         this.name = name;
      }

      public String toString() {
         return name;
      }
   }

   /**
    * Enumeration for the transfert type property in MIME types (InputStream, CharBuffer, etc.)
    */
   public static class TransferType {
      private Class dataClass;

      private TransferType(Class streamClass) {
         this.dataClass = streamClass;
      }

      public Class getDataClass() {
         return dataClass;
      }

      public String toString() {
         return dataClass.getName();
      }
   }
}
