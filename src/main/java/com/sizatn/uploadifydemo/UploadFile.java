package com.sizatn.uploadifydemo;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 
 * @desc base on Uploadify(A Multiple File Upload jQuery Plugin), with jsp and servlet to implement file upload
 * @author sizatn
 * @email sizatn2013@gmail.com
 * @date Dec 14, 2015
 */
public class UploadFile extends HttpServlet {

	/** 
	 * @desc 
	 * @author sizatn
	 * @date Dec 14, 2015
	 */
	private static final long serialVersionUID = 8179926151386149122L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = createSavePath();

		List<FileItem> fileList = getFiles(request);

		String filePath = saveFile(path, fileList);
		
		writeDataToResponse(response, filePath);
	}

	/**
	 * @param path
	 * @param fileList
	 * @return filePath
	 * @desc save file
	 * @author sizatn
	 * @date Dec 14, 2015
	 */
	private String saveFile(String path, List<FileItem> fileList) {
		String fileName = "";
		String fileType = "";
		String filePath = "";

		Iterator<FileItem> it = fileList.iterator();

		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				fileName = item.getName();
				if (fileName == null || fileName.trim().equals("")) {
					continue;
				}
				
				if (fileName.lastIndexOf(".") >= 0) {
					fileType = fileName.substring(fileName.lastIndexOf("."));
				}
				
				fileName = UUID.randomUUID().toString();
				File file = new File(path.split("\\+")[0] + fileName + fileType);
				if (!file.exists()) {
					try {
						item.write(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
					filePath = path.split("\\+")[1] + fileName + fileType;
				}
			}
		}
		return filePath;
	}

	/**
	 * @param request
	 * @return fileList
	 * @desc get file from request
	 * @author sizatn
	 * @date Dec 14, 2015
	 */
	private List<FileItem> getFiles(HttpServletRequest request) {
		List<FileItem> fileList = null;

		DiskFileItemFactory dfif = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		sfu.setHeaderEncoding("utf-8");

		try {
			fileList = sfu.parseRequest(request);
		} catch (FileUploadException ex) {
			ex.printStackTrace();
		}
		return fileList;
	}

	/**
	 * @return absolutePath + relativePath
	 * @desc create save path for file and make directory
	 * @author sizatn
	 * @date Dec 14, 2015
	 */
	private String createSavePath() {
		// absolute path for save file
		String absolutePath = this.getServletContext().getRealPath("");
		
		// relative path for read file, like image file
		String relativePath = this.getServletContext().getContextPath();
		
		absolutePath = absolutePath + "/uploadfiles/";
		File file = new File(absolutePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return absolutePath + "+" + relativePath;
	}
	
	/**
	 * @param response
	 * @param data
	 * @desc write data to response
	 * @author sizatn
	 * @date Dec 17, 2015
	 */
	private void writeDataToResponse(HttpServletResponse response, String data) {
		if (data != null || data.length() > 0) {
			try {
				response.getWriter().write(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
