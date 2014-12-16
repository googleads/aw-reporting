$( document ).ready( function() {
	enableForm(false);
});

var enableForm = function(enable) {
	$('#templateName').prop('disabled', !enable);
	$('#templateDescription').prop('disabled', !enable);
	$('#templateHtml').prop('disabled', !enable);
	$('#submitTemplate').prop('disabled', !enable);
}

$.fn.serializeObject = function () {
  var o = {};
  var a = this.serializeArray();
  $.each(a, function () {
    if (o[this.name] !== undefined) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || '');
    } else {
      o[this.name] = this.value || '';
    }
  });
  return o;
};

$(function () {
  $('form').submit(function () {
    var json = JSON.stringify($('form').serializeObject());
    console.log("JSON request: " + json);
    
$.ajax({url:"/template",success:function(result){
    alert("sent");
  },dataType:'json',
  data: json,
  type: "POST",
  contentType: "application/json;charset=utf-8",
  error: function (request, status, error) {
	  $("#templateHtml").text("failure");
	  alert(request.responseText);
	  alert(status);
  }
  });
    
    return false;
  });
});

$(function () {
	$('#isPublic').change(function(){
	     cb = $(this);
	     cb.val(cb.prop('checked'));
	 });
});

$(function () {
    $('#selectFile').on('change', function (event, files, label) {
    var file_name = this.value.replace(/\\/g, '/').replace(/.*\//, '');
    var inputFiles = this.files;
    var inputFile = inputFiles[0];

    var reader = new FileReader();
    reader.readAsText(inputFile);
    reader.onload = function (event) {
      enableForm(true);
      $('#templateName').val(file_name);
      $('#templateHtml').text(event.target.result);
    };

    reader.onerror = function (event) {
      alert("Error reading template file provided: " + event.target.error.code);
    };
  });
});