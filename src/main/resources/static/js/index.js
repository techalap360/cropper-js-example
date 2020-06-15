var cropper;
var imageClassName = "cropper-image";
var options = {
	viewMode : 0
}

function cropSelectedImage(){
	var $image = $('.' + imageClassName);
	$image.cropper(options);
	cropper = $image.data('cropper');
}

function submitWithAjaxRequest(blob, form){
	var fileName = $('#file').val();
	var formArr = $('#mainform').serializeArray();
	var formData = new FormData()
	formData.append('file', blob, fileName);
	$.each(formArr, function(index, item){
		formData.append(item.name, item.value);
	})

	$.ajax({
		url : $(form).attr('action'),
		type : 'POST',
		cache: false,
		contentType: false,
		processData: false,
		data : formData,
		success : function(data) {
			if(data.status == 'success'){
				location.reload();
			} else {
				alert("Failed to upload image")
			}
		},
		error : function(jqXHR, status, errorThrown){
			alert("Failed to upload image");
		}
	})
}

$(document).ready(function(){
	// Selected file name will display into input field
	$(".custom-file-input").on("change", function() {
		var fileName = $(this).val().split("\\").pop();
		$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
	});

	// Preview image
	var _URL = window.URL || window.webkitURL;
	$("#file").change(function(e) {
		var image, file;
		if ((file = this.files[0])) {
			image = new Image();
			image.onload = function() {
				var imgsrc = this.src;
				$('#imageUploaderPreview').html('<img class="'+ imageClassName +'" src="'+ imgsrc +'" width="100%" height="100%"></div>');
				e.preventDefault();
				cropSelectedImage();
			}
		};
		image.src = _URL.createObjectURL(file);
	});

	// Form submit with image
	$('#mainform').on('submit', function(e){
		e.preventDefault();
		var form = $(this);
		cropper.getCroppedCanvas().toBlob(function (blob) {
			submitWithAjaxRequest(blob, form);
		});
	})
})