package com.zr.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLException;

import net.sf.json.JSONObject;
/**
 * 人脸识别：比较人脸相似度
 * 调用在线api,效率受网络影响较大
 * 
 * 
 * 需要FaceppSDK.jar及json-lib相关包
 * @author Kramer
 *
 */
public class FaceUtil {
	private static final String FACE_KEY = "ereJKaZeU4c1ZEizVEis3oubInvQ_1nO";
	private static final String FACE_SECRET = "Hv9XvR5QI43biuE6MlR_NlcfFCkAN6sk";

	public static void main(String[] args) throws Exception {
		double compare =  compareFaces("F:/ceshi.jpg","F:/ceshi1.jpg");
	}

	/**
	 * 将两个人脸进行比对，来判断是否为同一个人，返回比对结果置信度和不同误识率下的阈值。 
	 * 
	 * @param filePath1		图片路径
	 * @param filePath2
	 * @return 比对结果置信度，范围 [0,100]，小数点后3位有效数字，数字越大表示两个人脸越可能是同一个人。
	 *         注：如果传入图片但图片中未检测到人脸，则无法进行比对，返回-1。
	 */
	public static double compareFaces(String filePath1, String filePath2) {
		return getResultOfCompare(getFaceToken(filePath1), getFaceToken(filePath2));
	}

	/**
	 * 获取照片的face_token
	 * 
	 * @param imagePath
	 *            文件路径
	 * @return 若失败则返回""
	 */
	private static String getFaceToken(String imagePath) {
		File file = new File(imagePath);
		byte[] buff1 = getBytesFromFile(file);
		String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
		HashMap<String, String> map = new HashMap<>();
		HashMap<String, byte[]> byteMap = new HashMap<>();
		map.put("api_key", FACE_KEY);
		map.put("api_secret", FACE_SECRET);
		byteMap.put("image_file", buff1);
		String str = new String();
		try {
			byte[] bacd = post(url, map, byteMap);
			str = new String(bacd);
			JSONObject json = JSONObject.fromObject(str);
			String face_token = json.getJSONArray("faces").getJSONObject(0).get("face_token").toString();
			return face_token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 对比两张图片的人脸相似度
	 * 
	 * @param face_token1
	 * @param face_token2
	 * @return
	 */
	private static double getResultOfCompare(String face_token1, String face_token2) {
		String url = "https://api-cn.faceplusplus.com/facepp/v3/compare";
		HashMap<String, String> map = new HashMap<>();
		map.put("api_key", FACE_KEY);
		map.put("api_secret", FACE_SECRET);
		map.put("face_token1", face_token1);
		map.put("face_token2", face_token2);
		byte[] bacd;
		try {
			bacd = postCompare(url, map);
			String str = new String(bacd);
			JSONObject json = JSONObject.fromObject(str);
			return Double.valueOf(json.get("confidence").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private final static int CONNECT_TIME_OUT = 30000;
	private final static int READ_OUT_TIME = 50000;
	private static String boundaryString = getBoundary();

	/**
	 * 发送对比请求
	 */
	protected static byte[] postCompare(String url, HashMap<String, String> map) throws Exception {
		HttpURLConnection conne;
		URL url1 = new URL(url);
		conne = (HttpURLConnection) url1.openConnection();
		conne.setDoOutput(true);
		conne.setUseCaches(false);
		conne.setRequestMethod("POST");
		conne.setConnectTimeout(CONNECT_TIME_OUT);
		conne.setReadTimeout(READ_OUT_TIME);
		conne.setRequestProperty("accept", "*/*");
		conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
		conne.setRequestProperty("connection", "Keep-Alive");
		conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");

		DataOutputStream obos = new DataOutputStream(conne.getOutputStream());

		Iterator iter = map.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry) iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			obos.writeBytes("--" + boundaryString + "\r\n");
			obos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
			obos.writeBytes("\r\n");
			obos.writeBytes(value + "\r\n");
		}
		obos.writeBytes("--" + boundaryString + "--" + "\r\n");
		obos.writeBytes("\r\n");
		obos.flush();
		obos.close();
		InputStream ins = null;
		int code = conne.getResponseCode();
		try {
			if (code == 200) {
				ins = conne.getInputStream();
			} else {
				ins = conne.getErrorStream();
			}
		} catch (SSLException e) {
			e.printStackTrace();
			return new byte[0];
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[4096];
		int len;
		while ((len = ins.read(buff)) != -1) {
			baos.write(buff, 0, len);
		}
		byte[] bytes = baos.toByteArray();
		ins.close();
		return bytes;

	}

	protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap)
			throws Exception {
		HttpURLConnection conne;
		URL url1 = new URL(url);
		conne = (HttpURLConnection) url1.openConnection();
		conne.setDoOutput(true);
		conne.setUseCaches(false);
		conne.setRequestMethod("POST");
		conne.setConnectTimeout(CONNECT_TIME_OUT);
		conne.setReadTimeout(READ_OUT_TIME);
		conne.setRequestProperty("accept", "*/*");
		conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
		conne.setRequestProperty("connection", "Keep-Alive");
		conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");

		DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
		Iterator iter = map.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry) iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			obos.writeBytes("--" + boundaryString + "\r\n");
			obos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
			obos.writeBytes("\r\n");
			obos.writeBytes(value + "\r\n");
		}
		if (fileMap != null && fileMap.size() > 0) {
			Iterator fileIter = fileMap.entrySet().iterator();
			while (fileIter.hasNext()) {
				Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
				obos.writeBytes("--" + boundaryString + "\r\n");
				obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey() + "\"; filename=\""
						+ encode(" ") + "\"\r\n");
				obos.writeBytes("\r\n");
				obos.write(fileEntry.getValue());
				obos.writeBytes("\r\n");
			}
		}
		obos.writeBytes("--" + boundaryString + "--" + "\r\n");
		obos.writeBytes("\r\n");
		obos.flush();
		obos.close();
		InputStream ins = null;
		int code = conne.getResponseCode();
		try {
			if (code == 200) {
				ins = conne.getInputStream();
			} else {
				ins = conne.getErrorStream();
			}
		} catch (SSLException e) {
			e.printStackTrace();
			return new byte[0];
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[4096];
		int len;
		while ((len = ins.read(buff)) != -1) {
			baos.write(buff, 0, len);
		}
		byte[] bytes = baos.toByteArray();
		ins.close();
		return bytes;
	}

	private static String getBoundary() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 32; ++i) {
			sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(
					random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
		}
		return sb.toString();
	}

	private static String encode(String value) throws Exception {
		return URLEncoder.encode(value, "UTF-8");
	}

	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}
}