
// 天线设置
function getAll(param){
	var url = "/JavaUhfDemo/getAll.action";
	if(param=='RM8011'){
		 tb = document.getElementById('fieldsetRM8011Table');
		 var data = ({"modelType":"0"});
	}else{
		tb = document.getElementById('fieldsetTable');
		var data = ({"modelType":"1"});
	}
	
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			var tb;
			if(param=='RM8011'){
				 tb = document.getElementById('fieldsetRM8011Table');
			}else{
				tb = document.getElementById('fieldsetTable');
			}
			   
			// 删除tr,td
			var rowNum = tb.rows.length;
			for (i = 0; i < rowNum; i++) {
				tb.deleteRow(i);
				rowNum = rowNum - 1;
				i = i - 1;
			}
			
			var jsonReturn = eval("(" + data + ")");
			$.each(jsonReturn, function(index, value) {
				if(value.failed=='获取失败'){
					$("#testWriter").html(value.failed);
					return 
				}
				var tr = document.createElement("tr");
	            var td1= document.createElement("td")
	            var td2 = document.createElement("td");
	            var td3 = document.createElement("td");  
	            var td4 = document.createElement("td");  
	            var td5 = document.createElement("td");  
	            var td6 = document.createElement("td");  
	            tr.onclick = function() { getRowDetail(this) }; //为每个tr增加单击事件   
	            if(value.antennaStatus=='1'){
	            	td1.innerHTML = "启用";
	            }else{
	            	td1.innerHTML = "禁用";
	            }
	            //td1.innerHTML = value.antennaStatus ;
				td2.innerHTML = value.antennaPort;
				td3.innerHTML = value.powerLevel;
				td4.innerHTML = value.dwellTime;
				td5.innerHTML = value.numberInventoryCycles;
				td6.innerHTML = value.swr;
				//给tr添加id属性
				tr.setAttribute('id',"tr"+value.antennaPort);
				// 将创建的td添加到tr中
				if(param=='RM8011'){
					tr.appendChild(td1);
					tr.appendChild(td2);
					//tr.appendChild(td3);
					tr.appendChild(td4);
				}else{
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					tr.appendChild(td5);
					tr.appendChild(td6);
				}
				// 使用appendChild（tr）方法，将tr添加到listbody中
				// 其中获取的listbody是要将表格添加的位置的id
				//var listbody = document.getElementById("fieldsetTable");
				var listbody;
				if(param=='RM8011'){
					listbody = document.getElementById("fieldsetRM8011Table");
				}else{
					listbody = document.getElementById("fieldsetTable");
				}
				listbody.appendChild(tr);
				$("#testWriter").html(value.success);
			})
		}
	});
}

//每个tr增加的单击事件  
function getRowDetail(row){
	//column.style.color = "blue"; //将被点击的单元格设置为蓝色   
    //alert(row.innerHTML); //弹出被点单元格里的内容  
    var cells = row.getElementsByTagName("td"); // 找到这个tr下的所有td，不能用childNodes 属性，部分浏览器不兼容
    for(var i=0;i<cells.length;i++){
      // alert("第"+(i+1)+"格的数字是"+cells[i].innerHTML);
       if(cells.length == 3){//说明是RM8011
		   if(cells[0].innerHTML  == "启用"){
			    $("#antennaEnablingRM8011").val("1");
            }else{
            	$("#antennaEnablingRM8011").val("0");
            }
		    $('input[name=numberRM8011]').val(cells[1].innerHTML);
   		    $('input[name=endNumRM8011]').val(cells[1].innerHTML);
   		    $('input[name=residingTimeRM8011]').val(cells[2].innerHTML);
       }else{
    	   if(cells[0].innerHTML  == "启用"){
			    $("#antennaEnabling").val("1");
           }else{
           	$("#antennaEnabling").val("0");
           }
			$('input[name=number]').val(cells[1].innerHTML);
			$('input[name=endNum]').val(cells[1].innerHTML);
			$('input[name=power]').val(cells[2].innerHTML);
			$('input[name=residingTime]').val(cells[3].innerHTML);
			$('input[name=diskCycle]').val(cells[4].innerHTML);
			$('input[name=bobbi]').val(cells[5].innerHTML);
       }
    }
}



function enableAll(param){
	var url = "/JavaUhfDemo/enableAll.action";
	var data = ({ });
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			   var dataObj = JSON.parse(data);
			  if(dataObj.status=='success'){
				  if(param=='RM8011'){
					   var trList = $("#fieldsetRM8011Table").children("tr")
				   }else{
					   var trList = $("#fieldsetTable").children("tr")
				   }
				     for (var i=0;i<trList.length;i++) {
					     var tdArr = trList.eq(i).find("td");
					     tdArr.eq(0).html("启用");
					   }
			    }
			  $("#testWriter").html(dataObj.data);
		}
	});
}


function disableAll(param){
	var url = "/JavaUhfDemo/disableAll.action";
	var data = ({ });
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			   var dataObj = JSON.parse(data);
			   if(dataObj.status=='success'){
				   if(param=='RM8011'){
					   var trList = $("#fieldsetRM8011Table").children("tr")
				   }else{
					   var trList = $("#fieldsetTable").children("tr")
				   }
				     for (var i=0;i<trList.length;i++) {
					     var tdArr = trList.eq(i).find("td");
					     tdArr.eq(0).html("禁用");
					   }
			    }
			   $("#testWriter").html(dataObj.data);
		}
	});
}



//天线配置
function configuration(param) {
	if(param=='get'){
		var antennaEnabling = $("#antennaEnabling").val();
		var starNum = $('input[name=number]').val();
		var endNum = $('input[name=endNum]').val();
		var power = $('input[name=power]').val();
		var residingTime = $('input[name=residingTime]').val();
		var diskCycle = $('input[name=diskCycle]').val();
		var bobbi = $('input[name=bobbi]').val();
		var url = "/JavaUhfDemo/getAntennaPort.action";
		var data = ({
			"antennaEnabling" : antennaEnabling,
			"starNum" : starNum,
			"endNum":endNum,
			"power" : power,
			"residingTime" : residingTime,
			"diskCycle" : diskCycle,
			"bobbi" : bobbi
			})
		}else if(param=='modify'){
			var antennaStatus = $("#antennaEnabling").val();
			var starNum = $('input[name=number]').val();
			var endNum = $('input[name=endNum]').val();
			var power = $('input[name=power]').val();
			var residingTime = $('input[name=residingTime]').val();
			var diskCycle = $('input[name=diskCycle]').val();
			var bobbi = $('input[name=bobbi]').val();
			var url = "/JavaUhfDemo/setAntennaPort.action";
			var data = ({
				"antennaStatus" : antennaStatus,
				"starNum" : starNum,
				"endNum":endNum,
				"power" : power,
				"residingTime" : residingTime,
				"diskCycle" : diskCycle,
				"bobbi" : bobbi
			});
	 }
		$.ajax({
			type : 'POST',
			url : url,
			data : data,
			dataType : "text",
			async : false,
			success : function(data) {
				var jsonReturn = eval("(" + data + ")");
				$.each(jsonReturn, function(index, value) {
					$("#testWriter").html(value.data);
					if(value.status=='failed'){
						return //结束
					}else{
						 var row = document.getElementById("tr"+value.antennaPort);  
						 var cells = row.getElementsByTagName("td"); // 找到这个tr下的所有td，
						 for(var i=0;i<cells.length;i++){
						    //alert("第"+(i+1)+"格的数字是"+cells[i].innerHTML);
						    if(value.antennaStatus=='1'){
						    	cells[0].innerHTML  = "启用";
	    		            }else{
	    		            	cells[0].innerHTML  = "禁用";
	    		            }
						    cells[1].innerHTML  = value.antennaPort;
						    cells[2].innerHTML  = value.powerLevel;
						    cells[3].innerHTML  = value.dwellTime;
						    cells[4].innerHTML  = value.numberInventoryCycles;
						    //cells[5].innerHTML  = value.swr;
						 }
					}
				})
			}
		});
}



//RM8011  修改天线配置
function configurationRM8011(param) {
	var antennaStatus = $("#antennaEnablingRM8011").val();
	var starNum = $('input[name=numberRM8011]').val();
	var endNum = $('input[name=endNumRM8011]').val();
	var residingTime = $('input[name=residingTimeRM8011]').val();
	var data = ({
		"antennaStatus" : antennaStatus,
		"starNum" : starNum,
		"endNum":endNum,
		"residingTime" : residingTime
		})
	if(param=='get'){
		var url = "/JavaUhfDemo/getAntennaPortRM8011.action";

	 }else if(param=='modify'){
			var url = "/JavaUhfDemo/setAntennaPortRM8011.action";
	 }
		$.ajax({
			type : 'POST',
			url : url,
			data : data,
			dataType : "text",
			async : false,
			success : function(data) {
				//$("#testWriter").html(data);
				var jsonReturn = eval("(" + data + ")");
				$.each(jsonReturn, function(index, value) {
					 /*var trList = $("#fieldsetTable").children("tr");
					 for (var i=0;i<trList.length;i++) {
	                      var tdArr = trList.eq(i).find("td");
	                      if(value.antennaStatus=='1'){
						    	//cells[0].innerHTML  = "启用";
						    	 tdArr.eq(0).html("启用");
	    		            }else{
	    		            	//cells[0].innerHTML  = "禁用";
	    		            	 tdArr.eq(0).html("禁用");
	    		            }
						   // cells[1].innerHTML  = value.antennaPort;
						    tdArr.eq(1).html(value.antennaPort);
						    //cells[2].innerHTML  = value.powerLevel;
						    //cells[3].innerHTML  = value.dwellTime;
						    tdArr.eq(3).html(value.antennaPort);
	                   }*/
					 var row = document.getElementById("tr"+value.antennaPort);  
					 // var trList = $("#fieldsetTable").children("tr");
					 //var cells = trList.getElementsByTagName("td"); // 找到这个tr下的所有td，
					 var cells = row.getElementsByTagName("td"); // 找到这个tr下的所有td，
					
					 for(var i=0;i<cells.length;i++){
					    //alert("第"+(i+1)+"格的数字是"+cells[i].innerHTML);
					    if(value.antennaStatus=='1'){
					    	cells[0].innerHTML  = "启用";
    		            }else{
    		            	cells[0].innerHTML  = "禁用";
    		            }
					    //cells[1].innerHTML  = value.antennaPort;
					    //cells[2].innerHTML  = value.powerLevel;
					    cells[2].innerHTML  = value.dwellTime;
					 }
				})
			}
		});
}

