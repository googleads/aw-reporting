// Control structure for the revamp project
//
// tarjei@google.com
//
//-----------------------------------------------------------
 $(document).ready(function(){
    function toggleTabs() {
      $('#categories').toggle('slow');
    }  

    var renderer   = new Renderer();
    
    renderer.doReq({
        service  : 'usertokens',
        callback : function (data) {
          if (data) {
        	  
        	if (data.length == 0) {
                var adder =  $('<div/>').addClass('usertoken').text("Please Authenticate one MCC, Enter MCC ID}");
                $('#usertokens').append( adder );
                                
                $('#table').hide();
                $('#oauth').show();
        	}
        	  
            for (var i=0;i<data.length;i++) {
            	
              $('#table').show();
              $('#oauth').hide();
            	
              var usertoken = new UserToken(data[i]);
              
              var text = "MCC: " + usertoken.getTopAccountId() + " email: " + usertoken.getEmail() + " User: " + usertoken.getUserId(); 
              var adder =  $('<div/>').addClass('usertoken').text(text);
              
              $('#usertokens').append( adder );
              
              var topAccountTexts = $('#topAccountId *');
              for (var j=0;j<topAccountTexts.length;j++) {
            	  topAccountTexts[j].innerText = topAccountTexts[j].innerText.replace('{topAccountId}',usertoken.getTopAccountId());
            	  topAccountTexts[j].href = topAccountTexts[j].href.replace('%7BtopAccountId%7D', usertoken.getTopAccountId());
            	  topAccountTexts[j].href = topAccountTexts[j].href.replace('{topAccountId}', usertoken.getTopAccountId());
              }
            }
          }
        }
      });

 });

function Renderer () { 
  this.selectedCategory = null;
  this.setRESTConfig({
	usertokens : '/mymccs',
    categories : '/accounts/2742928629',
  });
}


Renderer.prototype.setRESTConfig = function (RESTConfig) {this.RESTConfig = RESTConfig}
Renderer.prototype.getRESTConfig = function () {return this.RESTConfig}

Renderer.prototype.doReq = function (opts) {
  var RESTConfig = this.getRESTConfig();
  var url = RESTConfig[opts.service];

  for (var key in opts.keys) {
    var regex = new RegExp('{'+key+'}', 'g');
    url=url.replace(regex, opts.keys[key]);
  }

  if (opts.method == undefined)  opts.method = 'GET';
  $.ajax({
      type:opts.method,
      url:url,
      data:opts.data,
      success:opts.callback
    });
  
}



