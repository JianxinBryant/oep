package com.zr.action.ems.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zr.utils.FaceUtil;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

/**
 * 接收页面image,存到服务器; 考试监控：人脸识别和比较
 * 
 * 
 * @author Kramer
 *
 */
public class WebCamAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 上传路径
	 */
	private static final String UPLOAD_PATH = "f:/upload/";
	/**
	 * 截取图片存放路径
	 */
	private static final String IMAGE_PATH = "f:";
	/**
	 * 样图存放路径
	 */
	private static final String TARGET_IMAGE_PATH = "f:";
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;char=utf-8");
		//获取userId,确定身份
		//int userId = (int) req.getSession().getAttribute("uid");
		int userId = 1;
		//截取的图片路径
		String imagePath = uploadImage(req,IMAGE_PATH,userId);
		//样图路径
		String targetImagePath = getTargetImagePath(userId);
		//返回相似度
		double similar = FaceUtil.compareFaces(imagePath, targetImagePath);
		System.out.println("=======================");
		System.err.println("相似度:"+similar);
		boolean flag = true;
		if(similar > 80){
			flag = true;
		}else{
			flag = false;
		}
		JSONObject json = new JSONObject();
		json.put("result", flag);
		json.put("similar", similar);
		System.err.println(json.toString());
		//返回信息
		//resp.getWriter().write(json.toString());
		resp.getWriter().print(json.toString());
	}
	/**
	 * 返回该用户样图路径
	 * @param userId
	 * @return
	 */
	private static String getTargetImagePath(int userId){
		return TARGET_IMAGE_PATH+"/user"+userId+".jpg";
	}
	
	/**
	 * 返回图片保存的路径及文件名
	 * @return
	 */
	private static String uploadImage(HttpServletRequest req,String filePath,int userId){
		String imagePathName = "";
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件需要上传到的路径
		// String path = request.getRealPath("/upload");
		String path = UPLOAD_PATH;
		// 如果文件夹不存在 将创建文件夹
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(path));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(req);
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String imgStr = item.getString();
					// base64解码并生成图片
					BASE64Decoder decoder = new BASE64Decoder();
					try {
						// Base64解码
						byte[] bytes = decoder.decodeBuffer(imgStr);
						// 生成jpeg图片
						imagePathName = filePath + "/" +userId +".jpg";
						OutputStream out = new FileOutputStream(imagePathName);
						out.write(bytes);
						out.flush();
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					// request.setAttribute(name, value);
				} else {
					// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
					/**
					 * 以下三步，主要获取 上传文件的名字
					 */
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start + 1);
					// 将当前时间戳 作为的文件名
					String newfilename = Long.toString(new Date().getTime())
							+ filename.substring(filename.indexOf('.'));
					req.setAttribute(name, newfilename);
					// 真正写到磁盘上
					// 它抛出的异常 用exception 捕捉
					// item.write( new File(path,filename) );//第三方提供的
					// 手动写的
					OutputStream out = new FileOutputStream(new File(path, newfilename));
					InputStream in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];
					System.out.println("获取上传文件的总共的容量：" + item.getSize());
					// in.read(buf) 每次读到的数据存放在 buf 数组中
					while ((length = in.read(buf)) != -1) {
						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
						out.write(buf, 0, length);
					}
					in.close();
					out.close();
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imagePathName;
	}
}
