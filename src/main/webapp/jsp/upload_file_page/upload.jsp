<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>File upload input</title>
      <link rel="stylesheet" href="jsp/upload_file_page/css/style.css">
</head>
<body>
  <script class="jsbin" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<div class="file-upload">
  <button class="file-upload-btn" type="button" onclick="$('.file-upload-input').trigger( 'click' )">Upload a File</button>
  <div class="image-upload-wrap">
    <input class="file-upload-input" type='file' onchange="readURL(this);" accept="zip/*" />
    <div class="drag-text">
      <h3>Drag and drop a file or select add File</h3>
    </div>
  </div>
  <div class="file-upload-content">
    <div class="image-title-wrap">
      <button type="button" onclick="removeUpload()" class="remove-image">Remove <span class="image-title">Uploaded File</span></button>
    </div>
  </div>
</div>
    <script  src="jsp/upload_file_page/js/index.js"></script>
</body>
</html>
