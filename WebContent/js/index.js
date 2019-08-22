var urlValue = $("#urlValue").val();




//设备连接
var modeTypeValue;
var pageContext;
function Connection(model) {
	pageContext=$("#urlValue").val();
	//alert(self.location.href);
	if (model == 'Serial') {
		if($("#serialConn").html()=='断开'){
			//var url = "/JavaUhfDemo/closeRFModel.action";
			var url = self.location.href+"closeRFModel.action";
			var data = ({});
		}else{
			var modeType = $("input[name=modelType]:checked").val();
			modeTypeValue = $('#'+"modelTypeSpan"+modeType).text();
			var port = $('#SerialNo option:selected').val();// 选中的值
			var serial =$('#SerialNo option:selected').text();//选中的文本值
			var band = $('#BaudRate').val();
			var BaudRate =$('#BaudRate option:selected').text();//选中的文本值
			var url = "/JavaUhfDemo/connectRFModel.action";
			//var url = pageContext+"/connectRFModel.action";
			var data = ({
				"modeType" : modeType,
				"modeTypeValue" : modeTypeValue,
				"serial" : serial,
				"port" : port,
				"BaudRate" : BaudRate,
				"band" : band
			});
		}
	} else if (model == 'Network') {
		if($("#NetworkConn").html()=='断开'){
			var url = "/JavaUhfDemo/closeNetwork.action";
			var data = ({ });
		}else{
		var modeType = $("input[name=modelType]:checked").val();
		var IPAddress = $("input[name=IPAddress]").val();// 选中的值
		var serverPort = $('input[name=serverPort]').val();// 选中的值
		modeTypeValue = $('#'+"modelTypeSpan"+modeType).text();
		var port = $('#SerialNo option:selected').val();// 选中的值
		var serial =$('#SerialNo option:selected').text();//选中的文本值
		var url = "/JavaUhfDemo/connectNWModel.action";
		var data = ({
			"modeType" : modeType,
			"IPAddress" : IPAddress,
			"modeTypeValue" : modeTypeValue,
			"port" : port,
			"serial" : serial,
			"serverPort" : serverPort
		});
		}
	} else if (model == 'Listening') {
		var monitorPort = $("input[name=monitorPort]").val();
		var url = "/JavaUhfDemo/monitorPort.action";
		var data = ({
			"monitorPort" : monitorPort
		});
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			var dataObj = JSON.parse(data);
			var connectType;
			if(dataObj.status=="connectRFSuccess"){
				 $("#serialConn").html("断开");
				 connectType="connectRF";
			}else if(dataObj.status=="connectNWSuccess"){
				 $("#NetworkConn").html("断开");
				 connectType="connectNW";
			}
		  
           if(dataObj.status=="connectRFSuccess" || dataObj.status=="connectNWSuccess"){//连接成功隐藏部分tab
				 if(modeTypeValue=='R2000'){//R2000
					    openTabOne();
			            mystorage.set('geValue', '0');
			            $("#testWriter").html("当前状态：设备连接成功！设备序列号："+dataObj.serialNo+",软件版本："+dataObj.versionNo);
					}else if(modeTypeValue=='RM801X'){//RM8011
						var paramAntNum;
						if(dataObj.antNum==1){//RM8011 单口，不显示天线设置页面
							openTabTow('1');
							paramAntNum='1';
			        	  }else{
			        		  openTabTow('2');
			        		  paramAntNum='2';
			        	  }
			             mystorage.set('geValue', '1');
			             mystorage.set('antNum', paramAntNum);
			            $("#testWriter").html("当前状态：设备连接成功！设备序列号："+dataObj.serialNo+",软件版本："+dataObj.versionNo);
					}else{//RM70XX
						openTabThree();
				        mystorage.set('geValue', '2')
			            $("#testWriter").html("当前状态：设备连接成功！RM70XX序列号:"+dataObj.serialNum+"设备序列号："+dataObj.serialNo+",软件版本："+dataObj.versionNo);
					}
				 mystorage.set('getBtn', "断开");//记住状态
				 mystorage.set('getConnectType', connectType);//记住状态
	       		return
           }else if(dataObj.status=="closeRFSuccess"){
        	   tabsClos();
        	   mystorage.clear();//整体清除
        	   $("#serialConn").html("连接");
           }else if(dataObj.status=="closeNWSuccess"){
        	   tabsClos();
        	   mystorage.clear();//整体清除
        	   $("#NetworkConn").html("连接");
           }
        	 $("#testWriter").html(dataObj.data);
		}
	});

}

//盘点测试
var gTimerId = 0;

function inventory(parame) {
	if (parame == 'start') {//开始
		var ModuleCheck = $('#ModuleCheck option:selected').val();// 选中的值
		//var automaticInventory = $('#automaticInventory').val();
		var area = $('#area option:selected').val();// 选中的值
		var start = $('#start').val();
		var length = $('#length').val();
		var url = "/JavaUhfDemo/startInventory.action";
		var data = ({
			"ModuleCheck" : ModuleCheck,
			"area":area,
			"startAddr":start,
			"wordLen":length
		});
		$("#btn").attr("disabled", true);//禁用开始按钮
	} else if (parame == 'stop') {//停止
		$("#btn").attr("disabled", false);
		var url = "/JavaUhfDemo/stopInventory.action";
		var data = ({});
	}else if(parame == 'clear'){//清空
		var tb = document.getElementById('inventoryTR');
		// 删除tr,td
		var rowNum = tb.rows.length;
		for (i = 0; i < rowNum; i++) {
			tb.deleteRow(i);
			rowNum = rowNum - 1;
			i = i - 1;
		}
		$("#tagCount").html("0");
		$("#Rate").html("0");
		$("#dateStr").html("0分0秒0毫秒");
		$("#tag").html("0");
		var url = "/JavaUhfDemo/clearInventory.action";
		var data = ({});
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : true,
		success : function(data) {
			if (data == 'success') {
				gTimerId = setInterval("getInventoryData()",300);
				//alert('success'+gTimerId);
			}
			if (data == 'stop') {
				clearInterval(gTimerId);
				//alert('stop'+gTimerId);
			}
		}
	});
}

function getInventoryData() {
		getInventory();// 调用方法
		getTagData();
};

/*function getInventoryData() {
	i = setInterval(function() {
		getInventory();// 调用方法
		getTagData();
	}, 200);

};*/

function getTagData(){
	var url = "/JavaUhfDemo/getTagData.action";
	var data = ({});
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : true,
		success : function(data) {
				var jsonReturn = eval("(" + data + ")");
				$.each(jsonReturn, function(index, value) {
					$("#tagCount").html(value.tagCount);
					$("#Rate").html(value.Rate);
					$("#dateStr").html(value.dateStr);
					$("#tag").html(value.tag);
				})
		}
	});
}

function getInventory() {
	var url = "/JavaUhfDemo/getInventory.action";
	var data = ({
		"ModuleCheck" : 'ModuleCheck'
	/* "automaticInventory" : 'automaticInventory' */
	});
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			// $("#testWriter").html(data);
			// alert(typeof data);
			var jsonReturn = eval("(" + data + ")");
			// 定时执行
			var num = 0;
			// var i = setInterval(function() {
			// num++;
			// var date = new Date();
			// document.write(date.getMinutes() + ':' + date.getSeconds() + ':'
			// + date.getMilliseconds() + '<br>');
			var tb = document.getElementById('inventoryTR');
			// 删除tr,td
			var rowNum = tb.rows.length;
			for (i = 0; i < rowNum; i++) {
				tb.deleteRow(i);
				rowNum = rowNum - 1;
				i = i - 1;
			}

			$.each(jsonReturn, function(index, value) {
				var tr = document.createElement("tr");
				var td1 = document.createElement("td")
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");
				var td4 = document.createElement("td");
				var td5 = document.createElement("td");
				var td6 = document.createElement("td");
				// if(index==num){
				td1.innerHTML = index + 1;
				td2.innerHTML = value.antennaPort;
				td3.innerHTML = value.epc;
				td4.innerHTML = value.externalData;
				td5.innerHTML = value.count;
				td6.innerHTML = value.rssi;
				td1.style.width = "60px";
				td2.style.width = "60px";
				td3.style.width = "220px";
				td5.style.width = "60px";
				td6.style.width = "60px";
				// }else{
				/*
				 * td1.innerHTML = "serialNumber"+num; td2.innerHTML =
				 * "antennaNumber"+num; td3.innerHTML = "EPCCode"+num;
				 * td4.innerHTML = "TID"+num; td5.innerHTML = "count"+num;
				 * td6.innerHTML = "rssi"+num;
				 */

				// }
				// 将创建的td添加到tr中
				tr.appendChild(td1);
				tr.appendChild(td2);
				tr.appendChild(td3);
				tr.appendChild(td4);
				tr.appendChild(td5);
				tr.appendChild(td6);

				// 使用appendChild（tr）方法，将tr添加到listbody中
				// 其中获取的listbody是要将表格添加的位置的id
				var listbody = document.getElementById("inventoryTR");
				listbody.appendChild(tr);
			});

			// if (num > 10)
			// clearInterval(i);
			// }, 1000);

		}
	});
}

//盘点区域 获取与设置
function inventoryArea(param){
	var area = $('#area option:selected').val();// 选中的值
	var start = $('#start').val();
	var length = $('#length').val();
	var data = ({
		"area" : area,
		"startAddr" : start,
		"wordLen" : length
	});
	if(param == 'get'){
		var url = "/JavaUhfDemo/getInventoryArea.action";
	}else if(param == 'set'){
		var url = "/JavaUhfDemo/setInventoryArea.action";
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
		   var dataObj = JSON.parse(data);
           if(dataObj.status=="getInventoryAreaSuccess"){
        	    $('#area').val(dataObj.area);// 选中的值
        		$('#start').val(dataObj.startAddr);
        		$('#length').val(dataObj.wordLen);
           }
    	      $("#testWriter").html(dataObj.data);
		}
	});
}


//过滤设置  获取与设置
function filterSetting(param){
	var threshold = $('#cachingNumber').val();
	var maxRepeatTimes = $('#maximumReporting').val();
	var data = ({
		"threshold" : threshold,
		"maxRepeatTimes" : maxRepeatTimes
	});
	if(param == 'get'){
		var url = "/JavaUhfDemo/filterGet.action";
	}else if(param == 'set'){
		var url = "/JavaUhfDemo/filterSet.action";
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
		   var dataObj = JSON.parse(data);
           if(dataObj.status=="filterGetSuccess"){
        	   $('#cachingNumber').val(dataObj.threshold);
        	   $('#maximumReporting').val(dataObj.maxRepeatTimes);
           }
	    	$("#testWriter").html(dataObj.data);
		}
	});
}
    

// 读写测试
function readAndWrite(param) {
	if (param == 'epcRead'||param == 'epcReadSync') {
		var epcStartAddress = $('input[name=epcStartAddress]').val();
		var epcReadLength = $('input[name=epcReadLength]').val();
		var epcPassword = $('input[name=epcPassword]').val();
		var data = ({
			"epcStartAddress" : epcStartAddress,
			"epcReadLength" : epcReadLength,
			"epcPassword" : epcPassword
		});
		if(param == 'epcRead'){
			var url = "/JavaUhfDemo/epcRead.action";
		}else{
			var url = "/JavaUhfDemo/epcReadSync.action";
		}
	}else if ( param == 'epcWrite'||param == 'epcWriteSync') {
		var epcStartAddress = $('input[name=epcStartAddress]').val();
		var epcReadLength = $('input[name=epcReadLength]').val();
		var epcPassword = $('input[name=epcPassword]').val();
		var epcWriteData = $('#epcWriteData').val();
		var data = ({
			"epcStartAddress" : epcStartAddress,
			"epcReadLength" : epcReadLength,
			"epcPassword" : epcPassword,
			"epcWriteData":epcWriteData
		});
		if(param == 'epcWrite'){
			var url = "/JavaUhfDemo/epcWrite.action";
		}else{
			var url = "/JavaUhfDemo/epcWriteSync.action";
		}
	}  else if (param == 'tidRead'||param == 'tidReadSync') {
		var tidStartAddress = $('input[name=tidStartAddress]').val();
		var tidReadLength = $('input[name=tidReadLength]').val();
		var tidPassword = $('input[name=tidPassword]').val();
		var data = ({
			"tidStartAddress" : tidStartAddress,
			"tidReadLength" : tidReadLength,
			"tidPassword" : tidPassword
		});
		if(param == 'tidRead'){
			var url = "/JavaUhfDemo/tidRead.action";
		}else{
			var url = "/JavaUhfDemo/tidReadSync.action";
		}
	} else if (param == 'userRead'||param == 'userReadSync') {
		var userStartAddress = $('input[name=userStartAddress]').val();
		var userReadLength = $('input[name=userReadLength]').val();
		var userPassword = $('input[name=userPassword]').val();
		var data = ({
			"userStartAddress" : userStartAddress,
			"userReadLength" : userReadLength,
			"userPassword" : userPassword
		});
		if(param == 'userRead'){
			var url = "/JavaUhfDemo/userRead.action";
		}else{
			var url = "/JavaUhfDemo/userReadSync.action";
		}
	}else if (param == 'userWrite'||param == 'userWriteSync') {
		var userStartAddress = $('input[name=userStartAddress]').val();
		var userReadLength = $('input[name=userReadLength]').val();
		var userPassword = $('input[name=userPassword]').val();
		var userWriteData = $('#userWriteData').val();
		var data = ({
			"userStartAddress" : userStartAddress,
			"userReadLength" : userReadLength,
			"userPassword" : userPassword,
			"userWriteData":userWriteData
		});
		if(param == 'userWrite'){
			var url = "/JavaUhfDemo/userWrite.action";
		}else{
			var url = "/JavaUhfDemo/userWriteSync.action";
		}
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
		   var dataObj = JSON.parse(data);
           if(dataObj.status=="epcReadSuccess"){
	       		  $('#epcReadeResult').val(dataObj.epc);
	       		  $('#epcWriteData').val(dataObj.result);
           }else if(dataObj.status=="tidReadSuccess"){
        		  $('#tidReadeResult').val(dataObj.result);
           		  $('#tidWriteData').val(dataObj.epc);
           }else if(dataObj.status=="userReadSuccess"){
        		  $('#userReadeResult').val(dataObj.epc);
           		  $('#userWriteData').val(dataObj.result);
           }else if(dataObj.status=="epcWriteSuccess"){
	       		  $('#epcReadeResult').val(dataObj.epc);
           }else if(dataObj.status=="userWriteSuccess"){
        	   //写入后会写epc数据到页面
	       		  $('#userReadeResult').val(dataObj.epc);
	       }
        	     $("#testWriter").html(dataObj.data);
		}
	});
}

// 权限设置
function permissions(param) {
	if (param == 'destruction') {
		var accessPassword = $('input[name=accessPassword]').val();
		var destructionPassword = $('input[name=destructionPassword]').val();
		var url = "/JavaUhfDemo/killTag.action";
		var data = ({
			"accessPassword" : accessPassword,
			"destructionPassword" : destructionPassword
		});
	}else if (param == "modifySet") {//修改
		var rpasswordType = $('#rpasswordType').val();
		var newPassword = $('input[name=newPassword]').val();
		var accessPass = $('input[name=accessPass]').val();
		var url = "/JavaUhfDemo/writeTag.action";
		var data = ({
			"rpasswordType" : rpasswordType,
			"newPassword" : newPassword,
			"accessPass" : accessPass
		});
	} else if (param = 'read') {

		var passwordType = $('#passwordType').val();

		var rePassword = $('input[name=rePassword]').val();
		var accessPasswords = $('input[name=accessPasswords]').val();
		var url = "/JavaUhfDemo/readTag.action";
		var data = ({
			"passwordType" : passwordType,
			"rePassword" : rePassword,
			"accessPasswords" : accessPasswords
		});
	}  else if (param == 'execution') {//执行设置
		var killPasswordPermissions = $("#killPasswordPermissions").val();
		var accessPasswordPermissions = $("#accessPasswordPermissions").val();
		var epcMemoryBankPermissions = $("#epcMemoryBankPermissions").val();
		var tidMemoryBankPermissions = $("#tidMemoryBankPermissions").val();
		var userMemoryBankPermissions = $("#userMemoryBankPermissions").val();
		var accessPassword = $('input[name=accessPassword]').val();

		var url = "/JavaUhfDemo/lockTag.action";
		var data = ({
			"killPasswordPermissions" : killPasswordPermissions,
			"epcMemoryBankPermissions" : epcMemoryBankPermissions,
			"tidMemoryBankPermissions" : tidMemoryBankPermissions,
			"accessPasswordPermissions" : accessPasswordPermissions,
			"userMemoryBankPermissions" : userMemoryBankPermissions,
			"accessPassword" : accessPassword
		});
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
		   var dataObj = JSON.parse(data);
           if(dataObj.status=="readTagSuccess"){
       		  $('input[name=rePassword]').val(dataObj.result);
           }
        	 $("#testWriter").html(dataObj.data);
		}
	});
}




var gReturnData;
// 通讯设置
function connProperty(param) {
	if (param == 'obtain') {
		var link = $("#link").val();
		var url = "/JavaUhfDemo/getCurrentProfile.action";
		var data = ({        
			"link" : link
		});
		gReturnData=$("#link");
	}else if (param == 'setUp') {
		var link = $("#link").val();
		var url = "/JavaUhfDemo/setCurrentProfile.action";
		var data = ({
			"link" : link
		});
	} else if (param == 'regionObtain' ) {
		var region = $("#region").val();
		var url = "/JavaUhfDemo/getRegion.action";
		var data = ({
			"region" : region
		});
		gReturnData=$("#region");
	}else if (param == 'regionSetUp') {
		var region = $("#region").val();
		var url = "/JavaUhfDemo/setRegion.action";
		var data = ({
			"region" : region
		});
	} else if (param == 'fObtain') {//频点
		var frequency = $("#frequency").val();
		var url = "/JavaUhfDemo/getFixFreq.action";
		var data = ({
			"frequency" : frequency
		});
		gReturnData=$("#frequency");
	}else if (param == 'fSetUp') {
		var frequency = $("#frequency").val();
		var url = "/JavaUhfDemo/setFixFreq.action";
		var data = ({
			"frequency" : frequency
		});
	} 
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			var dataObj = JSON.parse(data);
			//alert(dataObj.status + ":" + dataObj.value);
           if(dataObj.status=="setSuccess"){
        	 
           }else if(dataObj.status=="getSuccess"){
        	   //需要根据返回的值对应select的options，进行相应的显示
        	   gReturnData.val(dataObj.value); 
        	   //$("#link").find("option[data]").attr("selected",true);
        	   //$("#link option[value='1']").attr('selected',true);
           }
    	   $("#testWriter").html(dataObj.resultDate);
		}
	});
}

//跳频时间
function protschTxtime(param){
	var txOn = $('input[name=open]').val();
	var txOff = $('input[name=shut]').val();
	 if (param == 'get') {
		var url = "/JavaUhfDemo/getProtschTxtime.action"; 
		var data = ({
			"txOn" : txOn,
			"txOff" : txOff
		});
	}else if (param == 'set') {
		var url = "/JavaUhfDemo/setProtschTxtime.action"; 
		var data = ({
			"txOn" : txOn,
			"txOff" : txOff
		});
	} 
	 $.ajax({
			type : 'POST',
			url : url,
			data : data,
			dataType : "text",
			async : false,
			success : function(data) {
				var dataObj = JSON.parse(data);
				//alert(dataObj.status);
	           if(dataObj.status=="getSuccess"){
	        	   $("#testWriter").html(dataObj.data);
	        	   $('input[name=open]').val(dataObj.txOn);
	        	   $('input[name=shut]').val(dataObj.txOff);
	           }
	        	   $("#testWriter").html(dataObj.data);
	           
				
			}
		});
}


//算法属性
function algorithmic(param){
		var algorithm = $("#algorithm").val();
		var flip = $("#flip").val();
		var retryCount = $('input[name=retryCount]').val();
		if (param == 'set') {
			if(algorithm==0){//固定算法
				var qValue = $('input[name=QValue]').val();
				var isRepeat = $("#isRepeat").val();
				
				var data = ({
					"algorithm" : algorithm,
					"retryCount":retryCount,
					"flip" : flip,
					"qValue" : qValue,
					"isRepeat" : isRepeat
				});
					var url = "/JavaUhfDemo/setFixedAlgorithmic.action";
			}else{//动态算法
				var startingQValue = $('input[name=startingQValue]').val();
				var minQ = $('input[name=minQ]').val();
				var maxQ = $('input[name=maxQ]').val();
				var threshold = $('input[name=threshold]').val();
				var data = ({
					"algorithm" : algorithm,
					"flip" : flip,
					"retryCount" : retryCount,
					"startingQValue" : startingQValue,
					"minQ" : minQ,
					"maxQ" : maxQ,
					"threshold" : threshold
				});
					var url = "/JavaUhfDemo/setDynamicAlgorithm.action";
			}
		}else if (param == 'get') {
			var url = "/JavaUhfDemo/getAlgorithm.action";
			var data = ({ });
		}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			var dataObj = JSON.parse(data);
           if(dataObj.status=="getAlgorithmSuccess"){
        	   if(dataObj.value==0){
        		   $("#fixedAlgorithmDiv").show();
     		        $("#dynamicAlgorithmDiv").hide();
     		        
        		   $("#algorithm").val("0");
           		   $("#flip").val(dataObj.toggleTarget);
           		   $('input[name=retryCount]').val(dataObj.retryCount);
           		   $('input[name=QValue]').val(dataObj.qValue);
    			   $("#isRepeat").val(dataObj.repeatUntiNoTags);
        	   }else{//动态算法
        		   $("#dynamicAlgorithmDiv").show();
    		       $("#fixedAlgorithmDiv").hide();
           		
        		   $("#algorithm").val("1");
            	   $("#flip").val(dataObj.toggleTarget);
           		   $('input[name=retryCount]').val(dataObj.retryCount);
    			   $('input[name=startingQValue]').val(dataObj.startQValue);
    			   $('input[name=minQ]').val(dataObj.minQValue);
    			   $('input[name=maxQ]').val(dataObj.maxQValue);
    			   $('input[name=threshold]').val(dataObj.thresholdMultiplier);
        	   }
           }
        	   $("#testWriter").html(dataObj.data);
		}
	});
}
 


//Query参数
function query(param){
	var queryParameter = $("#queryParameter").val();
	var session = $("#session").val();
	var selectAll = $("#selectAll").val();
	var data = ({
		"queryParameter" : queryParameter,
		"session" : session,
		"selectAll" : selectAll
	});
	 if (param == 'get') {
		var url = "/JavaUhfDemo/getQuery.action";
	} else if (param == 'set') {
		var url = "/JavaUhfDemo/setQuery.action";
	}
	 $.ajax({
			type : 'POST',
			url : url,
			data : data,
			dataType : "text",
			async : false,
			success : function(data) {
			   var dataObj = JSON.parse(data);
	           if(dataObj.status=="getSuccess"){
	        	   $("#queryParameter").val(dataObj.target);
	       		   $("#session").val(dataObj.session);
	       	       $("#selectAll").val(dataObj.selected);
	            }
	        	   $("#testWriter").html(dataObj.data);
			}
		});
}





/*function makeRM80xxFixFreq()
{
	// 中国840 - 845M"
	// 美国902 - 928M"
	// 欧洲865 - 868"
	// 韩国917 - 924"
	// 信道,频率计算因子
	// 信道信息
	var startFreq = 0, endFreq = 0, step = 0;
	switch (selIdx) {
	case 0:
		// 中国 920-925
		startFreq = 920125;
		endFreq = 924875;
		step = 250;
		break;
	case 1:
		// 美国 902-928
		startFreq = 902250;
		endFreq = 924875;
		step = 500;
		break;
	case 2:
		// 欧洲 865-867
		startFreq = 865100;
		endFreq = 867900;
		step = 200;
		break;
	case 3:
		// 中国 840-845
		startFreq = 840125;
		endFreq = 844875;
		step = 200;
		break;
	case 4:
		// 韩国 917-924
		startFreq = 917100;
		endFreq = 923300;
		step = 200;
		break;
	}
	
	var freq = startFreq; 
	while (freq <= endFreq) {
		// --- 你要添加的频率值(/1000) 
		freq += 250;
		 
	}
}*/

//掩码设置
function maskSet(param) {
	if(param=='get' || param=='set'){
		//启用选中，赋值为1。否则赋值为0
		if($("input[name=maskStart]").is(':checked')){
			$('input[name=maskStart]').val("1");
		}else{
			$('input[name=maskStart]').val("0");
		}
	}else if(param=='criteriaGet' || param=='criteriaSet'){
		//启用选中，赋值为1。否则赋值为0
		if($("input[name=criteriaStart]").is(':checked')){
			$('input[name=criteriaStart]').val("1");
		}else{
			$('input[name=criteriaStart]').val("0");
		}
	}
	
	 if(param=='get'){
		  
			var url = "/JavaUhfDemo/maskGet.action";
			var data = ({});
	 }else if(param=='set'){
		  
			var maskStart = $('input[name=maskStart]').val();
			var startOffset = $('input[name=startOffset]').val();
			var maskValue = $('input[name=maskValue]').val();
		
			var url = "/JavaUhfDemo/maskSet.action";
			var data = ({
				"maskStart" : maskStart,
				"startOffset" : startOffset,
				"maskValue" : maskValue
			});
	 }else if(param=='criteriaGet'){
			var criteriaStart = $('input[name=criteriaStart]').val();
			var url = "/JavaUhfDemo/criteriaGet.action";
			var data = ({
				"criteriaStart" : criteriaStart
			});
	 }else if(param=='criteriaSet'){
			var criteriaStart = $('input[name=criteriaStart]').val();
			var criteriaArea = $("#criteriaArea").val();
			var criteriaSession = $("#criteriaSession").val();
			var criteriaInterception = $("#criteriaInterception").val();
			var criteriaAction = $("#criteriaAction").val();
			var criteriaStartOffset = $('input[name=criteriaStartOffset]').val();
			var criteriaLength = $('input[name=criteriaLength]').val();
			var criteriaMaskValue = $('input[name=criteriaMaskValue]').val();

			var url = "/JavaUhfDemo/criteriaSet.action";
			var data = ({
				"criteriaStart" : criteriaStart,
				"criteriaArea" : criteriaArea,
				"criteriaSession" : criteriaSession,
				"criteriaInterception" : criteriaInterception,
				"criteriaAction" : criteriaAction,
				"criteriaStartOffset" : criteriaStartOffset,
				"criteriaLength" : criteriaLength,
				"criteriaMaskValue" : criteriaMaskValue
			});
		 }
	 $.ajax({
			type : 'POST',
			url : url,
			data : data,
			dataType : "text",
			async : false,
			success : function(data) {
			   var dataObj = JSON.parse(data);
	           if(dataObj.status=="getSuccess"){
	        	   //获取到checkbox，然后根据value值，选中checkbox
	        	    $('input[name=maskStart]').val(dataObj.maskStart);
	        	    var fxks = $("input[name=maskStart]");
	        		 for(var i=0;i<fxks.length;i++){
	        		     var f = fxks[i];
	        		     if(f.value==1){
	        		         f.checked=true;
	        		     }else{
	        		    	 f.checked=false;
	        		     }
	        		 }
	   			    $('input[name=startOffset]').val(dataObj.maskOffset);
	   			    $('input[name=maskValue]').val(dataObj.mask);
	            }else if(dataObj.status=="getCriteriaSuccess"){
	        	   //获取到checkbox，然后根据value值，选中checkbox
	        	    $('input[name=criteriaStart]').val(dataObj.criteriaStart);
	        	  //获取到checkbox，然后根据value值，选中checkbox
	        	    var fxks = $("input[name=criteriaStart]");
	        		 for(var i=0;i<fxks.length;i++){
	        		     var f = fxks[i];
	        		     if(f.value==1){
	        		         f.checked=true;
	        		     }else{
	        		    	 f.checked=false;
	        		     }
	        		 }
		 			 $("#criteriaArea").val(dataObj.memBank);
		 			 $("#criteriaSession").val(dataObj.target);
		 			 $("#criteriaInterception").val(dataObj.enableTruncate);
		 			 $("#criteriaAction").val(dataObj.action);
		 			 $('input[name=criteriaStartOffset]').val(dataObj.maskOffset);
		 			 $('input[name=criteriaLength]').val(dataObj.maskCount);
		 			 $('input[name=criteriaMaskValue]').val(dataObj.mask);
	            }
	        	 $("#testWriter").html(dataObj.data);
			}
		});
}



//参数设置
function parameterSet(param){
	if (param == 'workModeSet') {
		var workMode = $('#workMode').val();
		var url = "/JavaUhfDemo/workModeSet.action";
		var data = ({
			"workMode" : workMode
		});
	}else if (param == 'powerSet') {
		var power =$('#power option:selected').text();//选中的文本值
		var antennaPort = $('#power').val();
		var url = "/JavaUhfDemo/powerSet.action";
		var data = ({
			"power" : power,
            "antennaPort":antennaPort
		});
	}else if (param == 'powerGet') {
		//var power = $('#power').val();
		var url = "/JavaUhfDemo/powerGet.action";
		var data = ({
			/*"power" : power*/
		});
	}else if (param == 'areaSet') {
		var range = $('#parmArea').val();
		var url = "/JavaUhfDemo/areaSet.action";
		var data = ({
			"range" : range
		});
	}else if (param == 'areaGet') {
		//var parmArea = $('#parmArea').val();
		var url = "/JavaUhfDemo/areaGet.action";
		var data = ({
			//"parmArea" : parmArea
		});
	}else if (param == 'frequencySet') {
		//var paramFrequency = $('#paramFrequency').val();
		var paramFrequency =$('#paramFrequency option:selected').text();//选中的文本值

		var url = "/JavaUhfDemo/frequencySet.action";
		var data = ({
			"paramFrequency" : paramFrequency
		});
	}else if (param == 'frequencyGet') {
		var paramFrequency = $('#paramFrequency').val();
		var url = "/JavaUhfDemo/frequencyGet.action";
		var data = ({
			"paramFrequency" : paramFrequency
		});
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			var dataObj = JSON.parse(data);
           if(dataObj.status=="powerGetSuccess"){
        	 //根据options的text值，自动选中
        	   var count=$("#power").get(0).options.length;  
        	    for(var i=0;i<count;i++){  
        	        if($("#power").get(0).options[i].text == dataObj.powerLevel){  
        	            $("#power").get(0).options[i].selected = true;            
        	            break;    
        	        }    
        	    }  
           }else if(dataObj.status=="areaGetSuccess"){
        	   $('#parmArea').val(dataObj.value);
           }else if(dataObj.status=="frequencyGetSuccess"){//频点获取
        	   var count=$("#paramFrequency").get(0).options.length;  
       	       for(var i=0;i<count;i++){  
	       	        if($("#paramFrequency").get(0).options[i].text == dataObj.value){  
	       	            $("#paramFrequency").get(0).options[i].selected = true;            
	       	            break;    
       	              }    
       	    } 
           }
       	    $("#testWriter").html(dataObj.data);
		}
	});
}




//Query参数
function queryParam(param){
	var DR = $("#DRSelect").val();
	var M = $("#MSelect").val();
	var TRext = $("#TRextSelect").val();
	var Sel = $("#SelSelect").val();
	var Session = $("#SessionSelect").val();
	var Target = $("#TargetSelect").val();
	var Q = $('input[name=QInput]').val();
	var data = ({
		"DR" : DR,
		"M" : M,
		"TRext" : TRext,
		"Sel" : Sel,
		"Session" : Session,
		"Target" : Target,
		"Q" : Q
	});
	 if (param == 'get') {
		var url = "/JavaUhfDemo/queryParamGet.action";
	} else if (param == 'set') {
		var url = "/JavaUhfDemo/queryParamSet.action";
	}
	 $.ajax({
			type : 'POST',
			url : url,
			data : data,
			dataType : "text",
			async : false,
			success : function(data) {
			   var dataObj = JSON.parse(data);
	           if(dataObj.status=="getSuccess"){
		       	    $("#DRSelect").val(dataObj.DR);
		       		$("#MSelect").val(dataObj.M);
		       		//返回为1
		       		$("#TRextSelect").val(dataObj.TRext);
		       		$("#SelSelect").val(dataObj.Sel);
		       		$("#SessionSelect").val(dataObj.Session);
		       		$("#TargetSelect").val(dataObj.Target);
		       		$('input[name=QInput]').val(dataObj.Q);
	            }
	        	   $("#testWriter").html(dataObj.data);
			}
		});
}