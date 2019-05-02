package com.example.administrator.batheasy.bean1;

/**
 * ���ļ�ͷ�жϡ�ֱ�Ӷ�ȡ�ļ���ǰ�����ֽڡ� �����ļ����ļ�ͷ���£� JPEG (jpg)���ļ�ͷ��FFD8FF PNG (png)���ļ�ͷ��89504E47
 * GIF (gif)���ļ�ͷ��47494638 TIFF (tif)���ļ�ͷ��49492A00 Windows Bitmap (bmp)���ļ�ͷ��424D
 * CAD (dwg)���ļ�ͷ��41433130 Adobe Photoshop (psd)���ļ�ͷ��38425053 Rich Text Format
 * (rtf)���ļ�ͷ��7B5C727466 XML (xml)���ļ�ͷ��3C3F786D6C HTML (html)���ļ�ͷ��68746D6C3E
 * Email [thorough only] (eml)���ļ�ͷ��44656C69766572792D646174653A Outlook Express
 * (dbx)���ļ�ͷ��CFAD12FEC5FD746F Outlook (pst)���ļ�ͷ��2142444E MS Word/Excel
 * (xls.or.doc)���ļ�ͷ��D0CF11E0 MS Access (mdb)���ļ�ͷ��5374616E64617264204A
 * WordPerfect (wpd)���ļ�ͷ��FF575043 Postscript.
 * (eps.or.ps)���ļ�ͷ��252150532D41646F6265 Adobe Acrobat (pdf)���ļ�ͷ��255044462D312E
 * Quicken (qdf)���ļ�ͷ��AC9EBD8F Windows Password (pwl)���ļ�ͷ��E3828596 ZIP Archive
 * (zip)���ļ�ͷ��504B0304 RAR Archive (rar)���ļ�ͷ��52617221 Wave (wav)���ļ�ͷ��57415645 AVI
 * (avi)���ļ�ͷ��41564920 Real Audio (ram)���ļ�ͷ��2E7261FD Real Media (rm)���ļ�ͷ��2E524D46
 * MPEG (mpg)���ļ�ͷ��000001BA MPEG (mpg)���ļ�ͷ��000001B3 Quicktime (mov)���ļ�ͷ��6D6F6F76
 * Windows Media (asf)���ļ�ͷ��3026B2758E66CF11 MIDI (mid)���ļ�ͷ��4D546864
 */

public class FileValidator {

	// byte����ת����16�����ַ���
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	
	// �����ļ�byte�����ȡͼƬ�ļ���ʵ����
	public static String getTypeByStream(byte[] decode) {
		byte[] b = new byte[4];
		for(int i = 0; i < 4; i++) {
			b[i] = decode[i];
		}
		String type = bytesToHexString(b).toUpperCase();
		if (type.contains("FFD8FF")) {
			return "jpg";
		} else if (type.contains("89504E47")) {
			return "png";
		} else if (type.contains("47494638")) {
			return "gif";
		} else if (type.contains("49492A00")) {
			return "tif";
		} else if (type.contains("424D")) {
			return "bmp";
		}
		return type;
	}
}
