function answerRequest(isApproved){
	var answerRequest = {
			"isApproved" : isApproved,
		};
		$.ajax({
			       type : "post",
			        url : $(this).data("url"), 
	               data : JSON.stringify(answerRequest),
			contentType : 'application/json',
			   dataType : "json",
	    }).then(
	    		function(data){
	    			var message;
	    			if(data.responseJSON.isApproved){
	    				message = "承認で受け付けました。";
	    			}else{
	    				message = "否認で受け付けました。";
	    			}
    				$("result").after($('<span class="infoMessage">' + message + '</span>'));
	    		},
	    		function(data){
	    			$(".errorMessage").remove();
	    			$("#result").after($('<span class="errorMessage"></span>'));
	    		}
	    );	
}

function getUserId(){
	return $("#messageBody").data("userId");
}