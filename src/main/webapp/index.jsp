<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">  
<title>Upload</title>
	<link href="css/uploadify.css" rel="stylesheet" type="text/css" />  
	<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="scripts/jquery.uploadify.min.js"></script> 
	<script type="text/javascript">  
		$(function() {  
	        $("#file_upload").uploadify({  
	            'auto' : false, // automatically upload
	            'multi' : false, // allow only one file selection at a time
	            'swf' : 'swf/uploadify.swf', // 
	            'uploader' : 'uploadfile', // server-side upload script, like servlet  
	            'progressData' : 'speed', // file upload progress  
	            'queueID' : 'file_queue', // id of a DOM element to use as the file queue  
	            'queueSizeLimit' : 2, // maximum number of files at one time
	            'fileTypeDesc' : 'Image Files Only', // The description of the selectable files
	            'fileTypeExts' : '*.gif; *.jpg; *.png', // allowable extensions that can be uploaded  
	        });  
	    });
	</script>
</head>
<body>
	<div id="file_queue"></div>
    <input type="file" name="file_upload" id="file_upload" />
    <input type="button" onclick="javascript:$('#file_upload').uploadify('upload', '*')" value="Upload the Files" /> 
    <input type="button" onclick="javascript:$('#file_upload').uploadify('stop')" value="Stop the Uploads!" /> 
</body>
</html>