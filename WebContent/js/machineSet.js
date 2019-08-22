//一体机设置
         //常规设置
function generalSet(param){
	if (param == 'rfGet') {
		var rfModule = $('#rfModule').val();
		var url = "/JavaUhfDemo/rfGet.action";
		var data = ({
			"rfModule" : rfModule
		});
	}else if (param == 'rfSet') {
		var rfModule = $('#rfModule').val();
		var url = "/JavaUhfDemo/rfSet.action";
		var data = ({
			"rfModule" : rfModule
		});
	}else if (param == 'heartbeatGet') {
		var url = "/JavaUhfDemo/heartbeatGet.action";
		var data = ({
		});
	}else if (param == 'heartbeatSet') {
		var status = $('#heartbeatSwitch').val();
		//传text值
		var interval =$('#heartbeatTime option:selected').text();//选中的文本值

		var url = "/JavaUhfDemo/heartbeatSet.action";
		var data = ({
			"status" : status,
			"interval" : interval
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
           if(dataObj.status=="rfGetSuccess"){
        	   $('#rfModule').val(dataObj.value);
           }else if(dataObj.status=="heartbeatGetSuccess"){
        	    $('#heartbeatSwitch').val(dataObj.result);
              //根据options的text值，自动选中
         	   var count=$("#heartbeatTime").get(0).options.length;  
         	    for(var i=0;i<count;i++){  
         	        if($("#heartbeatTime").get(0).options[i].text == dataObj.interval){  
         	            $("#heartbeatTime").get(0).options[i].selected = true;            
         	            break;    
         	        }    
         	    }  
           }
       	    $("#testWriter").html(dataObj.data);
		}
	});
}


//盘点    
function inventoryModel(param){
	     //选择手动盘点和开机盘点时，其它的都不能输入
		//选中，赋值为1。否则赋值为0
	if($("input[name=mostTimes]").is(':checked')){
		$('input[name=mostTimes]').val("1");
	}else{
		$('input[name=mostTimes]').val("0");
	}
	var mode = $('#mode').val();
	var triggerDInPort = $('#triggerPin').val();
	//如果选择的是ANY，数值传递为255
	if(triggerDInPort=='4'){
		triggerDInPort='255';
	}
	var triggerLevel = $('#triggerPins').val();
	var ignoreTimeMs = $('input[name=accident]').val();
	var inventoryTimeMs = $('input[name=inventoryTime]').val();
	var maxCountReportFlag = $('input[name=mostTimes]').val();
	var data = ({
		"mode" : mode,
		"triggerDInPort" : triggerDInPort,
		"triggerLevel" : triggerLevel,
		"ignoreTimeMs" : ignoreTimeMs,
		"inventoryTimeMs" : inventoryTimeMs,
		"maxCountReportFlag" : maxCountReportFlag
	});
   if(param == 'get') {
	     var url = "/JavaUhfDemo/inventoryModelGet.action";
	}else if (param == 'set') {
		var url = "/JavaUhfDemo/inventoryModelSet.action";
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
	      	   $('#mode').val(dataObj.mode);
	      		if(dataObj.triggerDInPort=='255'){
	      			 $('#triggerPin').val('4');
				}else{
					$('#triggerPin').val(dataObj.triggerDInPort);
				}
	     	   $('#triggerPins').val(dataObj.triggerLevel);
	     	   $('input[name=accident]').val(dataObj.ignoreTimeMs);
	     	   $('input[name=inventoryTime]').val(dataObj.inventoryTimeMs);
	     	   $('input[name=mostTimes]').val(dataObj.maxCountReportFlag);
	     	   if($('#mode').val()== 2){//为触发盘点时。获取到checkbox，然后根据value值，选中checkbox
	     		     var fxks = $("input[name=mostTimes]");
		     		 for(var i=0;i<fxks.length;i++){
		     		     var f = fxks[i];
		     		     if(f.value==1){
		     		         f.checked=true;
		     		     }else{
		     		    	 f.checked=false;
		     		     }
		     		 }
	     	   }
		   	 if($('#mode').val()!= 2){//如果不是触发盘点，获取后都不能输入
	      		 $('#triggerPin').attr("disabled",true);
	 	    	 $('#triggerPins').attr("disabled",true);
	 	    	 $('input[name=accident]').attr("disabled",true);
	 	    	 $('input[name=inventoryTime]').attr("disabled",true);
	 	    	$("input[name='mostTimes']").attr("checked",false);//取消最多次数的选中状态
	 	    	 $('input[name=mostTimes]').attr("disabled",true);
	  	      }
         }
     	    $("#testWriter").html(dataObj.data);
		}
	});
}

//网络配置
function networkConfiguration(param){
		//选中，赋值为1。否则赋值为0
	if($("input[name=pingGateway]").is(':checked')){
		$('input[name=pingGateway]').val("1");
	}else{
		$('input[name=pingGateway]').val("0");
	}
	var networkType = $('#networkType').val();
	var iPAddress = $('input[name=iPAddress]').val();
	var gateway = $('input[name=gateway]').val();
	var pingGateway = $('input[name=pingGateway]').val();
	var subnetMask = $('input[name=subnetMask]').val();
	var MACAddressA = $('input[name=MACAddressA]').val();
	var MACAddressB = $('input[name=MACAddressB]').val();
	var MACAddressC = $('input[name=MACAddressC]').val();
	var MACAddressD = $('input[name=MACAddressD]').val();
	var MACAddressE = $('input[name=MACAddressE]').val();
	var MACAddressF = $('input[name=MACAddressF]').val();
	var machineIP = $('input[name=machineIP]').val();
	var machinePort = $('input[name=machinePort]').val();
	var wifiSSID = $('input[name=wifiSSID]').val();
	var wifiPassword = $('input[name=wifiPassword]').val();
	var data = ({
		"networkType" : networkType,
		"iPAddress" : iPAddress,
		"gateway" : gateway,
		"pingGateway" : pingGateway,
		"subnetMask" : subnetMask,
		"MACAddressA" : MACAddressA,
		"MACAddressB" : MACAddressB,
		"MACAddressC" : MACAddressC,
		"MACAddressD" : MACAddressD,
		"MACAddressE" : MACAddressE,
		"MACAddressF" : MACAddressF,
		"machineIP" : machineIP,
		"machinePort" : machinePort,
		"wifiSSID" : wifiSSID,
		"wifiPassword" : wifiPassword
	});
	if(param == 'get') {
	     var url = "/JavaUhfDemo/networkConfigurationGet.action";
	}else if (param == 'set') {
		var url = "/JavaUhfDemo/networkConfigurationSet.action";
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			var dataObj = JSON.parse(data);
	     if(dataObj.status=="wiredNetworkSuccess"){//有限网络
		  	$('input[name=iPAddress]').val(dataObj.ip);
		  	$('input[name=gateway]').val(dataObj.gateWay);
		  	$('input[name=pingGateway]').val(dataObj.pingGateWay);
		  	//获取到checkbox，然后根据value值，选中checkbox
		  	$('input[name=pingGateway]').val(dataObj.pingGateWay);
    	    var yourVal = dataObj.pingGateWay; 
    	    //根据value值，判断是否选中checkbox
    	    var fxks = $("input[name=pingGateway]");
    		 for(var i=0;i<fxks.length;i++){
    		     var f = fxks[i];
    		     if(f.value==1){
    		         f.checked=true;
    		     }else{
    		    	 f.checked=false;
    		     }
    		 }
		  	$('input[name=subnetMask]').val(dataObj.netmask);
		  	var str=dataObj.macAddr;
		  	$('input[name=MACAddressA]').val(str.substring(0,2));
		    $('input[name=MACAddressB]').val(str.substring(2,4));
		   	$('input[name=MACAddressC]').val(str.substring(4,6));
		    $('input[name=MACAddressD]').val(str.substring(6,8));
		  	$('input[name=MACAddressE]').val(str.substring(8,10));
		  	$('input[name=MACAddressF]').val(str.substring(10,12));
		  	$('input[name=machineIP]').val(dataObj.remoteIp);
		  	$('input[name=machinePort]').val(dataObj.remotePort);
	     }else if(dataObj.status=="wirelessNetworkSuccess"){// 无线网络
		  	$('input[name=iPAddress]').val(dataObj.ip);
	 	  	$('input[name=gateway]').val(dataObj.gateWay);
	 	  	$('input[name=pingGateway]').val(dataObj.pingGateWay);
	 	  	$('input[name=subnetMask]').val(dataObj.netmask);
	 	  	$('input[name=machineIP]').val(dataObj.remoteIp);
	 	  	$('input[name=machinePort]').val(dataObj.remotePort);
	 	  	$('input[name=wifiSSID]').val(dataObj.ssid);
	 	  	$('input[name=wifiPassword]').val(dataObj.passwd);
	    }
	 	    $("#testWriter").html(dataObj.data);
		}
	});
}



//GPIO配置
function GPIOConfiguration(param){
//判断div下的input为checkbox是否选中，选中赋值为1，否则为0
	//if(param == 'outPutPortSet'){
		$("#startTestDiv").find("input").each(function () {
			if ($(this).is(':checked')) {
			     $(this).val("1");
			}else{
				 $(this).val("0");
			}
		});
	//}
if (param == 'outPutPortGet') {
	var port = $('#outPutPort').val();
	var status = $('#outPutPorts').val();
	var url = "/JavaUhfDemo/outPutPortGet.action";
	var data = ({
		"port" : port,
		"status" : status
	});
}else if (param == 'outPutPortSet') {
	var port = $('#outPutPort').val();
	var status = $('#outPutPorts').val();
	var url = "/JavaUhfDemo/outPutPortSet.action";
	var data = ({
		"port" : port,
		"status" : status
	});
}else if (param == 'startTestGet') {
	var url = "/JavaUhfDemo/startTestGet.action";
	var data = ({
	
	});
}else if (param == 'startTestSet') {
	var D7 = $('input[id=D7]').val();
	var D6 = $('input[id=D6]').val();
	var D5 = $('input[id=D5]').val();
	var D4 = $('input[id=D4]').val();
	var D3 = $('input[id=D3]').val();
	var D2 = $('input[id=D2]').val();
	var D1 = $('input[id=D1]').val();
	var D0 = $('input[id=D0]').val();
	var url = "/JavaUhfDemo/startTestSet.action";
	var data = ({
		"D7" : D7,
		"D6" : D6,
		"D5" : D5,
		"D4" : D4,
		"D3" : D3,
		"D2" : D2,
		"D1" : D1,
		"D0" : D0
	});
}else if (param == 'inPutPortGet') {
	var port = $('#inPutPort').val();
	//var inPutPorts = $('#inPutPorts').val();
	var url = "/JavaUhfDemo/inPutPortGet.action";
	var data = ({
		"port" : port
		//"inPutPorts" : inPutPorts
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
      if(dataObj.status=="outPutPortGetSuccess"){
    	 $('#outPutPorts').val(dataObj.value);

     }else if(dataObj.status=="startTestGetSuccess"){
    	    $('input[id=D7]').val(dataObj.D7);
    		$('input[id=D6]').val(dataObj.D6);
    		$('input[id=D5]').val(dataObj.D5);
    		$('input[id=D4]').val(dataObj.D4);
    		$('input[id=D3]').val(dataObj.D3);
    		$('input[id=D2]').val(dataObj.D2);
    		$('input[id=D1]').val(dataObj.D1);
    		$('input[id=D0]').val(dataObj.D0);
    		
          
             
            /* var i=7;
             var yourVal;
             while (i>=0){
            	 var idVal="D"+i;
            	 yourVal = dataObj.idVal;
                 $("#input[id="+idVal+"]").each(function(index) {
                     if ($(this).get(index).value == yourVal) {
                     	$(this).get(index).checked = true;
                     }
                 });
                  i--;
             }*/
    		/*var fxks7 = $("input[id=D7]");
    		var fxks6 = $("input[id=D7]");
    		var fxks5 = $("input[id=D7]");
    		var fxks4 = $("input[id=D7]");
    		var fxks3 = $("input[id=D7]");
    		var fxks2 = $("input[id=D7]");
    		var fxks1 = $("input[id=D7]");
    		var fxks0 = $("input[id=D7]");
    		var arr=[fxks7,fxks6,fxks5,fxks4,fxks3,fxks2,fxks1,fxks0];
    		for(var J=0;J<arr.length;J++){
    			var a=arr[J];
    			for(var i=0;i<a.length;i++){
                    var f = a[i];
                    if(f.value==1){
                        f.checked=true;
                    }else{
                     f.checked=false;
                    }
                }
    		}*/
            
    		
    		
    		 var yourVal7 = dataObj.D7;
             $("input[id=D7]").each(function(index) {
                 if ($(this).get(index).value == 1) {
                 	$(this).get(index).checked = true;
                 }else{
                	$(this).get(index).checked = false;
                 }
             });
         
            	 var yourVal6 = dataObj.D6;
                 $("input[id=D6]").each(function(index) {
                     if ($(this).get(index).value == 1) {
                     	$(this).get(index).checked = true;
                     }else{
                     	$(this).get(index).checked = false;
                     }
                 });
                 var yourVal5 = dataObj.D5;
                 $("input[id=D5]").each(function(index) {
                     if ($(this).get(index).value == 1) {
                     	$(this).get(index).checked = true;
                     }else{
                     	$(this).get(index).checked = false;
                     }
                 });
                 var yourVal4 = dataObj.D4;
                 $("input[id=D4]").each(function(index) {
                     if ($(this).get(index).value == 1) {
                     	$(this).get(index).checked = true;
                     }else{
                     	$(this).get(index).checked = false;
                     }
                 });
                 var yourVal3 = dataObj.D3;
                 $("input[id=D3]").each(function(index) {
                     if ($(this).get(index).value == 1) {
                     	$(this).get(index).checked = true;
                     }else{
                     	$(this).get(index).checked = false;
                     }
                 });
                 var yourVal2 = dataObj.D2;
                 $("input[id=D2]").each(function(index) {
                     if ($(this).get(index).value == 1) {
                     	$(this).get(index).checked = true;
                     }else{
                     	$(this).get(index).checked = false;
                     }
                 });
                 var yourVal1 = dataObj.D1;
                 $("input[id=D1]").each(function(index) {
                     if ($(this).get(index).value == 1) {
                     	$(this).get(index).checked = true;
                     }else{
                     	$(this).get(index).checked = false;
                     }
                 });
                 var yourVal0 = dataObj.D0;
                 $("input[id=D0]").each(function(index) {
                     if ($(this).get(index).value == 1) {
                     	$(this).get(index).checked = true;
                     }else{
                     	$(this).get(index).checked = false;
                     }
                 });
    		

     }else if(dataObj.status=="inPutPortGetSuccess"){
  		$('#inPutPorts').val(dataObj.value);
     }
 	    $("#testWriter").html(dataObj.data);
     
		
	}
});
}

//定制HTTP上传信息
function httpReportAlarm(param){
	if (param == 'get') {
		var url = "/JavaUhfDemo/httpReportGet.action";
		var data = ({});
	}else if(param == 'set'){
		//方法类型
		var method = 0;
		if($('input[name="httpType"]:checked').val() == "Post"){
			method = 1;
		}
		//Action
		var actionName = $("#action").val();
		//特定信息
		var customMsg = $("#parameter").val();
		//是否启用
		var statusType = 0;
		if($('#statusType')[0].checked){
			statusType = 1;
		}
		//内容
		var D7 = $('#KeepAlive')[0].checked ? 1 : 0;
		var D6 = $('#Host')[0].checked ? 1 : 0;
		var D5 = $('#RSSI')[0].checked ? 1 : 0;
		var D4 = $('#天线号')[0].checked ? 1 : 0;
		var D3 = $('#PC')[0].checked ? 1 : 0;
		var D2 = $('#拓展数据')[0].checked ? 1 : 0;
		var D1 = $('#EPC')[0].checked ? 1 : 0;
		var D0 = $('#From')[0].checked ? 1 : 0;
		var url = "/JavaUhfDemo/httpReportSet.action";
		var data = ({
			"method":method,
			"actionName":actionName,
			"customMsg":customMsg,
			"statusType":statusType,
			"D7" : D7,
			"D6" : D6,
			"D5" : D5,
			"D4" : D4,
			"D3" : D3,
			"D2" : D2,
			"D1" : D1,
			"D0" : D0
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
			console.log(dataObj);
			if(dataObj.status == "httpReportGetSuccess"){
				if(dataObj.method == 0){
					httpType = "Get";
					$("#httpTypeGet").each(function(index) {
						if ($(this).get(index).value == "Get") {
					    	$(this).get(index).checked = true;
					    }else{
					    	$(this).get(index).checked = false;
					    }
					});
				}
				if(dataObj.method == 1){
					httpType = "Post";
					$("#httpTypePost").each(function(index) {
						if ($(this).get(index).value == "Post") {
					    	$(this).get(index).checked = true;
					    }else{
					    	$(this).get(index).checked = false;
					    }
					});
				}
				if(dataObj.actionName != null){
					$("#action").val(dataObj.actionName);
				}
				if(dataObj.customMsg != null){
					$("#parameter").val(dataObj.customMsg);
				}
				//内容
				$('input[id=KeepAlive]').val(dataObj.D7);
	    		$('input[id=Host]').val(dataObj.D6);
	    		$('input[id=RSSI]').val(dataObj.D5);
	    		$('input[id=天线号]').val(dataObj.D4);
	    		$('input[id=PC]').val(dataObj.D3);
	    		$('input[id=拓展数据]').val(dataObj.D2);
	    		$('input[id=EPC]').val(dataObj.D1);
	    		$('input[id=From]').val(dataObj.D0);
				$("input[id=KeepAlive]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				$("input[id=Host]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				$("input[id=RSSI]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				$("input[id=天线号]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				$("input[id=PC]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				$("input[id=拓展数据]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				$("input[id=EPC]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				$("input[id=From]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				
				//是否启用
				$('#statusType').val(dataObj.statusType);
				$("input[id=statusType]").each(function(index) {
					if ($(this).get(index).value == 1) {
				    	$(this).get(index).checked = true;
				    }else{
				    	$(this).get(index).checked = false;
				    }
				});
				onAction();
				onParameter();
				onContent();
			}
			$("#testWriter").html(dataObj.data);
			return;
		}
	});
}





//标签触发报警
function tagTriggerAlarm(param){
	var callPolice = $('#callPolice').val();
	//如果选择的是All，数值传递为255
	if(callPolice=='4'){
		callPolice=='255';
	}
	var police = $('#police').val();
	var accidentTime = $('input[name=accidentTime]').val();
	var continuousTime = $('input[name=continuousTime]').val();
	var reportNum = $('input[name=reportNum]').val();
	var model = $('#model').val();
	var offsetBytes = $('input[name=offsetBytes]').val();
	var EPCValue = $('input[name=EPCValue]').val();
	var state = $('input[name=state]').val();
	var data = ({
		"callPolice" : callPolice,
		"police" : police,
		"accidentTime" : accidentTime,
		"continuousTime" : continuousTime,
		"reportNum" : reportNum,
		"model" : model,
		"offsetBytes" : offsetBytes,
		"EPCValue" : EPCValue,
		"state" : state
	});
	if (param == 'set') {
		var url = "/JavaUhfDemo/alarmSet.action";
		
	}else if (param == 'get') {
		var url = "/JavaUhfDemo/alarmGet.action";
	}else if (param == 'enableAlarm') {
		var url = "/JavaUhfDemo/enableAlarm.action";
	}else if (param == 'disableAlarm') {
		var url = "/JavaUhfDemo/disableAlarm.action";
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		dataType : "text",
		async : false,
		success : function(data) {
			var dataObj = JSON.parse(data);
	   if(dataObj.status=="alarmGetSuccess"){
		    $('#callPolice').val(dataObj.port);
			if(dataObj.resultStatus == '255'){
     			 $('#police').val('4');
			}else{
				$('#police').val(dataObj.resultStatus);
			}
			$('input[name=accidentTime]').val(dataObj.ignoreSec);
			$('input[name=continuousTime]').val(dataObj.alarmSec);
			$('input[name=reportNum]').val(dataObj.alarmTimes);
			$('#model').val(dataObj.mode);
			$('input[name=offsetBytes]').val(dataObj.matchStart);
			$('input[name=EPCValue]').val(dataObj.epc);
	   }
		    $("#testWriter").html(dataObj.data);
		}
	});
	}




//输入触发报警
function inputTriggerAlarm(param){
	var triggerState = $('#triggerState').val();
	var inputAccidentTime = $('input[name=inputAccidentTime]').val();
	var inputContinuousTime = $('input[name=inputContinuousTime]').val();
	var inputTriggerPin = $('#inputTriggerPin').val();
	var inputTriggerPinState = $('#inputTriggerPinState').val();
	var inputCallPolice = $('#inputCallPolice').val();
	var inputCallPoliceState = $('#inputCallPoliceState').val();
	
	var data = ({
		"triggerState" : triggerState,
		"inputAccidentTime" : inputAccidentTime,
		"inputContinuousTime" : inputContinuousTime,
		"inputTriggerPin" : inputTriggerPin,
		"inputTriggerPinState" : inputTriggerPinState,
		"inputCallPolice" : inputCallPolice,
		"inputCallPoliceState" : inputCallPoliceState
	});
	if (param == 'set') {
		var url = "/JavaUhfDemo/inputTriggerAlarmSet.action";
		
	}else if (param == 'get') {
		var url = "/JavaUhfDemo/inputTriggerAlarmGet.action";
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
		    $('#triggerState').val(dataObj.resultStatus);
		    $('input[name=inputAccidentTime]').val(dataObj.ignoreMs);
			$('input[name=inputContinuousTime]').val(dataObj.alarmMs);
			$('#inputTriggerPin').val(dataObj.triggerDInPort);
			$('#inputTriggerPinState').val(dataObj.triggerLevel);
			$('#inputCallPolice').val(dataObj.DPort);
			$('#inputCallPoliceState').val(dataObj.DOutLevel);
	   }
		    $("#testWriter").html(dataObj.data);
		}
	});
	}


//系统
function system(param){
	if (param == 'getBoardSoftVersion') {
		var versionNum = $('input[name=versionNum]').val();
		var url = "/JavaUhfDemo/getBoardSoftVersion.action";
		var data = ({
			"versionNum" : versionNum
		});
	}else if (param == 'boardReboot') {
		var url = "/JavaUhfDemo/boardReboot.action";
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
			if(dataObj.status=="getVersionSuccess"){
				$('input[name=versionNum]').val(dataObj.version);
		   }
		    $("#testWriter").html(dataObj.data);
		}
	  });
	}

