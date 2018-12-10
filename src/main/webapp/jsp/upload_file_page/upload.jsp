<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>File upload input</title>
  <link rel="icon" href="<c:url value="/jsp/basic_user_page/favicon.ico"/>" type="image/icon"/>
  <link rel="stylesheet" href="<c:url value="/jsp/upload_file_page/css/style.css"/>">
</head>
<body>
  <script class="jsbin" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<div class="file-upload">
  <form method="post" action="uploaded" enctype="multipart/form-data">
    <button class="file-upload-btn" type="submit" onclick="$trigger( 'click' )">Upload File</button>
    <div class="image-upload-wrap">
      <input class="file-upload-input" type='file' onchange="readURL(this);" accept="application/zip" name="file" id="file" />
      <div class="drag-text">
        <h3>Drag and drop a file or select add File</h3>
      </div>
    </div>
  </form>
  <div class="file-upload-content">
    <div class="image-title-wrap">
      <button type="button" onclick="removeUpload()" class="remove-image">Remove <span class="image-title">Uploaded File</span></button>
    </div>
  </div>
</div>
    <script  src="<c:url value="/jsp/upload_file_page/js/index.js"/>"></script>
</body>
</html>
