<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<link rel="stylesheet" type="text/css" href="themes/demo.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>

<script type="text/javascript" src="js/uhf.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/antennaSet.js"></script>
<script type="text/javascript" src="js/machineSet.js"></script>
</head>


<body onload="init()">

	<header></header>

	<input id="urlValue" type="hidden"
		value="${pageContext.request.contextPath}" />
	<div id="tt" class="easyui-tabs"
		style="width: 1000px; height: 700px; margin: auto">
		<div title="设备连接" style="padding: 20px; display: none;">
			<form class="titOne">
				<fieldset>
					<legend>模块选择</legend>
					<span class="spanOne" style="margin-right: 20px;">模块：</span> <span
						class="radSpan" id="modelTypeSpan0"><input type="radio"
						name="modelType" value="0" checked>R2000</span> <span
						class="radSpan" id="modelTypeSpan1"><input type="radio"
						name="modelType" value="1" />RM801X</span> <span class="radSpan"
						id="modelTypeSpan2"><input type="radio" name="modelType"
						value="2" />RM70XX</span>
				</fieldset>
				<fieldset>
					<legend>串口模式</legend>
					<div class="leftDiv">
						<span class="spanOne">串口号：</span> <select id="SerialNo"
							class="selectDiv" name="dept" data-options="editable:false">
							<option value="0">COM1</option>
							<option value="1">COM2</option>
							<option value="2">COM3</option>
							<option value="3">COM4</option>
							<option value="4">COM5</option>
							<option value="5">COM6</option>
							<option value="6">COM7</option>
							<option value="7">COM8</option>
							<option value="8">COM9</option>
							<option value="9">COM10</option>
						</select>
					</div>
					<div class="rightDivOne">
						<span class="spanOne">波特率：</span> <select id="BaudRate"
							class="selectDiv" name="dept" data-options="editable:false">
							<option value="0">115200</option>
							<option value="1">230400</option>
						</select>
						<button id="serialConn" class="" type="button"
							onclick="Connection('Serial')">连接</button>
					</div>
				</fieldset>
				<fieldset>
					<legend>网络模式</legend>
					<div>
						<div class="leftDiv">
							<span class="spanOne">IP地址：</span>
							<!-- <select name="IPAddress" class="selectDiv" name="dept" data-options="editable:false">
                            <option value="0">192.168.1.15</option>
                        </select> -->
							<input class="ipDiv" value="192.168.1.16" name="IPAddress">
						</div>
						<div class="rightDiv">
							<div>
								<span class="spanOne">服务器端口：</span>
								<!-- <select name="serverPort" class="selectDiv" name="dept" data-options="editable:false">
                                <option value="0">1200</option>
                            </select> -->
								<input class="ipDiv" value="1200" name="serverPort">

								<button id="NetworkConn" type="button"
									onclick="Connection('Network')">连接</button>
							</div>
						</div>
						<div class="cenDiv">
							<span class="spanOne">${str }</span>
							<!-- 
                        <span class="spanOne">监控端口：</span>
                        <select name="monitorPort" class="selectDiv" name="dept" data-options="editable:false">
                            <option value="0">1400</option>
                        </select>
                        <button class="" type="button" onclick="Connection('Listening')">监听</button>
                    -->
						</div>
					</div>
					<div class="threeDiv">
						<!-- <span class="spans">客户端列表：</span><br><br/>                  
					 <textarea name="" id="" cols="94" rows="16"></textarea> -->
					</div>
				</fieldset>
			</form>
		</div>
		<div title="盘点测试" style="padding: 20px; display: none;">
			<div id="contentDiv">
				<div class="leftDiv">
					<div class="titleDivOne"></div>
					<div class="titleDivTow">
						<table>
							<tr>
								<th style="width: 60px;">序号</th>
								<th style="width: 60px;">天线号</th>
								<th style="width: 220px;">EPC码</th>
								<th>TID/USER码</th>
								<th style="width: 60px;">次数</th>
								<th style="width: 60px;">Rssi</th>
							</tr>
						</table>
					</div>
					<div class="titleDivThree">
						<table id="inventoryTR">

						</table>
					</div>
				</div>
				<div class="rightDiv">
					<div class="marDiv">
						<select id="ModuleCheck" class="selectDiv" name="dept"
							data-options="editable:false">
							<option value="0">单次盘点</option>
							<option value="1" selected>模块持续盘点</option>
							<option value="2">用户持续盘点</option>
						</select>
					</div>
					<div class="marDiv">
						<!--     自动盘点: <input name="automaticInventory" class="inputDiv" value="0"> -->
					</div>
					<button id="btn" type="button" onclick="inventory('start')">开始</button>
					<div class="marDiv">
						<!--      盘点标签: <input class="inputDiv" value="0"> -->
					</div>
					<button type="button" onclick="inventory('stop')">停止</button>
					<button type="button" onclick="inventory('clear')">清空</button>
					<div>
						标签个数：<span id="tag"></span>个
					</div>
					<div>
						盘点速度：<span id="Rate"></span>次/秒
					</div>
					<div>
						盘点时间：
						<!-- <span>0</span>秒<span>0</span>毫秒 -->
						<span id="dateStr">0时0分0秒0毫秒</span>
					</div>
					<div>
						获取个数：<span id="tagCount"></span>次
					</div>
					<form>
						<fieldset>
							<legend>盘点区域</legend>
							<div>
								<select id="area" class="selectDiv" name="dept"
									data-options="editable:false">
									<option value="0">EPC</option>
									<option value="1">EPC+TID</option>
									<option value="2">EPC+USER</option>
								</select>
							</div>

							<div>
								<span>起始：</span><input id="start" class="inputDiv inputs"
									value="0" disabled="disabled">
							</div>
							<div>
								<span>长度：</span><input id="length" class="inputDiv inputs"
									value="6" disabled="disabled">
							</div>
							<button class=" btnList" type="button"
								onclick="inventoryArea('get')">获取</button>
							<button class=" btnList" type="button"
								onclick="inventoryArea('set')">设置</button>
						</fieldset>
						<fieldset>
							<legend>过滤设置</legend>
							<div>
								<span class="spans">缓存数：</span><input id="cachingNumber"
									type="number" class="inputs" value="0">
							</div>
							<div>
								<span class="spans">最大上报：</span> <input type="number"
									class="inputs" id="maximumReporting" value="0"
									data-options="increment:1">次
							</div>
							<button class=" btnList" type="button"
								onclick="filterSetting('get')">获取</button>
							<button class=" btnList" type="button"
								onclick="filterSetting('set')">设置</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<div title="读写测试" style="padding: 20px; display: none;">
			<form>
				<fieldset class="fieldsets">
					<legend>EPC区</legend>
					<div class="firstDiv">
						<span class="spans">起始地址：</span><input type="number"
							name="epcStartAddress" class="inputs" value="2"> <span
							class="spans">读取长度：</span><input type="number"
							name="epcReadLength" class="inputs" value="1"> <span
							class="spans">访问密码：</span><input name="epcPassword"
							class="inputDiv">
					</div>
					<div class="nextDiv">
						<div>
							<span class="spans">EPC</span>
							<!-- <input name="epcWriteData"
                                                              data-options="multiline:true" style="height:40px;"> -->
							<textarea id="epcWriteData" cols="90" rows="3"></textarea>
						</div>
					</div>
					<div class="lastDiv">
						<textarea style="vertical-align: middle;" cols="66" rows="4"
							id="epcReadeResult" readonly="readonly"></textarea>
						<!--<input name="epcReadeResult"  readonly="readonly">  -->
						<div
							style="width: 180px; display: inline-block; vertical-align: middle;">
							<button class="" type="button" onclick="readAndWrite('epcRead')">异步读取</button>
							<button class="" type="button" onclick="readAndWrite('epcWrite')">异步写入</button>
							</br>
							<button class="" type="button"
								onclick="readAndWrite('epcReadSync')">同步读取</button>
							<button class="" type="button"
								onclick="readAndWrite('epcWriteSync')">同步写入</button>
						</div>
					</div>
				</fieldset>
				<fieldset class="fieldsets">
					<legend>TID区</legend>
					<div class="firstDiv">
						<span class="spans">起始地址：</span><input name="tidStartAddress"
							type="number" class="inputs" value="0"> <span
							class="spans">读取长度：</span><input name="tidReadLength"
							type="number" class="inputs" value="1"> <span
							class="spans">访问密码：</span><input name="tidPassword"
							class="inputDiv">
					</div>
					<div class="nextDiv">
						<div>
							<span class="spans">TID</span>
							<!-- <input name="tidReadeResult" 
                                                              data-options="multiline:true" style="height:36px;"> -->
							<textarea cols="90" rows="2" id="tidReadeResult"></textarea>
						</div>
					</div>
					<div class="lastDiv">
						<!--  <input  name="tidWriteData" readonly="readonly"> -->
						<textarea style="vertical-align: middle;" cols="66" rows="3"
							id="tidWriteData" readonly="readonly"></textarea>
						<div
							style="width: 180px; display: inline-block; vertical-align: middle;">
							<button class=" " type="button" onclick="readAndWrite('tidRead')">异步读取</button>
							<button class=" " type="button"
								onclick="readAndWrite('tidReadSync')">同步读取</button>
						</div>
					</div>
				</fieldset>
				<fieldset class="fieldsets">
					<legend>USER区</legend>
					<div class="firstDiv">
						<span class="spans">起始地址：</span><input name="userStartAddress"
							type="number" class="inputs" value="0"> <span
							class="spans">读取长度：</span><input name="userReadLength"
							type="number" class="inputs" value="1"> <span
							class="spans">访问密码：</span><input name="userPassword"
							class="inputDiv">
					</div>
					<div class="nextDiv">
						<div>
							<span class="spans">USER</span>
							<!-- <input  name="userWriteData"
                                                              data-options="multiline:true" style="height:60px;"> -->
							<textarea cols="90" rows="5" id="userWriteData"></textarea>
						</div>
					</div>
					<div class="lastDiv">
						<textarea style="vertical-align: middle;" cols="66" rows="4"
							id="userReadeResult" readonly="readonly"></textarea>
						<div
							style="width: 180px; display: inline-block; vertical-align: middle;">
							<button class="" type="button" onclick="readAndWrite('userRead')">异步读取</button>
							<button class="" type="button"
								onclick="readAndWrite('userWrite')">异步写入</button>
							</br>
							<button class="" type="button"
								onclick="readAndWrite('userReadSync')">同步读取</button>
							<button class="" type="button"
								onclick="readAndWrite('userWriteSync')">同步写入</button>
						</div>
					</div>
				</fieldset>
				</fieldset>
			</form>
		</div>
		<div title="权限设置" style="padding: 20px; display: none;">
			<form>
				<fieldset class="firstFieldset">
					<legend>销毁</legend>
					<span>访问密码：</span><input name="accessPassword" class="inputDiv"
						value="00000000"> <span class="setSpan">销毁密码：</span><input
						name="destructionPassword" class="inputDiv" value="00000000">
					<button class="" type="button" onclick="permissions('destruction')">销毁</button>
				</fieldset>
				<fieldset class="firstFieldset">
					<legend>读取</legend>
					密码类型： <select id="passwordType" class="selectDiv" name="dept"
						data-options="editable:false">
						<option value="0">销毁密码</option>
						<option value="1" selected>访问密码</option>
					</select> <span>密&nbsp;码：</span><input class="inputDiv" name="rePassword"
						value="00000000"> <span>访问密码：</span><input
						class="inputDiv" name="accessPasswords" value="00000000">
					<button class="" type="button" onclick="permissions('read')">读取</button>
				</fieldset>
				<fieldset class="firstFieldset">
					<legend>修改</legend>
					<span>密码类型：</span><select id="rpasswordType" class="selectDiv"
						name="dept" data-options="editable:false">
						<option value="0">销毁密码</option>
						<option value="1" selected>访问密码</option>
					</select>
					<!---->
					<span>新密码：</span><input name="newPassword" class="inputDiv">
					<span>访问密码：</span><input name="accessPass" class="inputDiv"
						value="00000000">
					<button class="" type="button" onclick="permissions('modifySet')">修改</button>
				</fieldset>
				<fieldset class="firstFieldset">
					<legend>锁定</legend>
					<div>
						销毁密码区：<select id="killPasswordPermissions" class="selectDiv"
							name="dept" data-options="editable:false">
							<option value="0">可读写</option>
							<option value="1">永久可读写</option>
							<option value="2">授权状态可读写</option>
							<option value="3">永久不可读写</option>
							<option value="4" selected>维持原状</option>
						</select> <span>EPC：</span> <select id="epcMemoryBankPermissions"
							class="selectDiv" name="dept" data-options="editable:false">
							<option value="0">可读写</option>
							<option value="1">永久可读写</option>
							<option value="2">授权状态可读写</option>
							<option value="3">永久不可读写</option>
							<option value="4" selected>维持原状</option>
						</select> <span>USER：</span> <select id="userMemoryBankPermissions"
							class="selectDiv" name="dept" data-options="editable:false">
							<option value="0">可读写</option>
							<option value="1">永久可读写</option>
							<option value="2">授权状态可读写</option>
							<option value="3">永久不可读写</option>
							<option value="4" selected>维持原状</option>
						</select>
						<button class="" type="button" onclick="permissions('execution')">执行设置</button>
					</div>
					<div>
						访问密码区：<select id="accessPasswordPermissions" class="selectDiv"
							name="dept" data-options="editable:false">
							<option value="0">可读写</option>
							<option value="1">永久可读写</option>
							<option value="2">授权状态可读写</option>
							<option value="3">永久不可读写</option>
							<option value="4" selected>维持原状</option>
						</select> <span>TID：</span> <select id="tidMemoryBankPermissions"
							class="selectDiv" name="dept" data-options="editable:false">
							<option value="0">可读写</option>
							<option value="1">永久可读写</option>
							<option value="2">授权状态可读写</option>
							<option value="3">永久不可读写</option>
							<option value="4" selected>维持原状</option>
						</select> <span>访问密码：</span><input name="accessPassword" type="password"
							class="inputDiv" value="00000000">
					</div>
				</fieldset>
			</form>
		</div>
		<div title="天线设置" style="padding: 20px; display: none;">
			<div class="getTableOne">
				<div class="btnDivList">
					<button class="" type="button" onclick="getAll('all')">全部获取</button>
					<button class="" type="button" onclick="enableAll('all')">全部打开</button>
					<button class="" type="button" onclick="disableAll('all')">全部关闭</button>
				</div>
				<div class="tabDiv">
					<div class="leftDiv">
						<div class="titleDivOne"></div>
						<div class="titleDivTow">
							<table>
								<tr>
									<th>使能</th>
									<th>天线号</th>
									<th>功率</th>
									<th>驻留时间（ms）</th>
									<th>盘讯周期</th>
									<th>驻波比</th>
								</tr>
							</table>
						</div>
						<div class="titleDivThree">
							<table id="fieldsetTable">

							</table>
						</div>
					</div>
				</div>
				<form>
					<fieldset class="fieldsetContent">
						<legend>修改天线配置</legend>
						<div>
							<span class="spans">天线使能：</span> <select id="antennaEnabling"
								class="selectDiv" name="dept" data-options="editable:false">
								<option value="0">禁用</option>
								<option value="1">启用</option>
							</select> <span class="spans">天线号：</span> <input name="number" input
								class="inputsOne" value="0">--<input type="number"
								class="inputsOne" name="endNum" value="0"> <span
								class="spans">天线功率：</span><input name="power" type="number"
								class="inputs" value="0">(dBm)
						</div>
						<div>
							<span class="spans">驻留时间：</span><input name="residingTime"
								type="number" class="inputs" value="0"> <span
								class="spans">盘讯周期：</span><input name="diskCycle" type="number"
								class="inputs" value="0"> <span class="spans">驻波比：</span><input
								name="bobbi" class="inputDiv" value="100" disabled>
						</div>
						<div>

							<button class="" type="button" onclick="configuration('get')">获取</button>
							<button class="" type="button" onclick="configuration('modify')">修改</button>

						</div>
					</fieldset>
				</form>
			</div>
			<div class="getTableTow">
				<!-- RM8011 天线设置-->
				<div class="btnDivList">
					<button class="" type="button" onclick="getAll('RM8011')">全部获取</button>
					<button class="" type="button" onclick="enableAll('RM8011')">全部打开</button>
					<button class="" type="button" onclick="disableAll('RM8011')">全部关闭</button>
				</div>
				<div class="tabDiv">
					<div class="leftDiv">
						<div class="titleDivOne"></div>
						<div class="titleDivTow">
							<table>
								<tr>
									<th>使能</th>
									<th>天线号</th>
									<!--  <th>功率</th> -->
									<th>驻留时间（ms）</th>
								</tr>
							</table>
						</div>
						<div class="titleDivThree">
							<table id="fieldsetRM8011Table">
							</table>
						</div>
					</div>
				</div>
				<form>
					<fieldset class="fieldsetContent">
						<legend>修改天线配置</legend>
						<div>
							<span class="spans">天线使能：</span> <select
								id="antennaEnablingRM8011" class="selectDiv" name="dept"
								data-options="editable:false">
								<option value="0">禁用</option>
								<option value="1">启用</option>
							</select> <span class="spans">天线号：</span> <input name="numberRM8011" input
								class="inputsOne" value="0">--<input type="number"
								class="inputsOne" name="endNumRM8011" value="0">
						</div>
						<div>
							<span class="spans">驻留时间：</span><input name="residingTimeRM8011"
								type="number" class="inputs" value="0">
						</div>
						<div>
							<button class="" type="button"
								onclick="configurationRM8011('get')">获取</button>
							<button class="" type="button"
								onclick="configurationRM8011('modify')">修改</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>


		<div title="通讯设置" style="padding: 20px; display: none;">
			<form>
				<fieldset class="content">
					<legend>连接属性</legend>
					<div>
						链接： <select id="link" class="selectDiv" name="dept"
							data-options="editable:false">
							<option value="0">0:DSB_ASK/M0/40KHZ</option>
							<option value="1">1:PR_ASK/M2/250khz</option>
							<option value="2">2:PR_ASK/M2/300khz</option>
							<option value="3">3:DSB_ASK/M0/400khz</option>
						</select>
						<button class="btnOneDiv" type="button"
							onclick="connProperty('obtain')">获取</button>
						<button class="" type="button" onclick="connProperty('setUp')">设置</button>
						<span class="firSpan">区域：</span> <select id="region"
							class="selectDiv" name="dept" data-options="editable:false">
							<option value=0>840-845(China)</option>
							<option value=1>920-925(China)</option>
							<option value=2>902-928(OpenArea)</option>
						</select>
						<button class="btnOneDiv" type="button"
							onclick="connProperty('regionObtain')">获取</button>
						<button class="" type="button"
							onclick="connProperty('regionSetUp')">设置</button>
					</div>
					<div class="inDiv">
						<span class="spansOne">跳频时间：</span> 开 <input name="open"
							class="inputDiv" value="0">ms 关 <input name="shut"
							class="inputDiv" value="0">ms
						<button class="" type="button" onclick="protschTxtime('get')">获取</button>
						<button class="" type="button" onclick="protschTxtime('set')">设置</button>

					</div>
					<div class="divOne">
						<span>频点：</span> <input id="frequency" type="number"
							class="inputs" step="0.125" value="920.625">

						<button class="" type="button" onclick="connProperty('fObtain')">获取</button>
						<button class="" type="button" onclick="connProperty('fSetUp')">设置</button>

					</div>
				</fieldset>
				<fieldset class="content">
					<legend>算法属性</legend>
					<div class="divTow">
						算法选择：<select id="algorithm" class="selectDiv" name="dept"
							data-options="editable:false">
							<option value="0">固定算法</option>
							<option value="1" selected>动态算法</option>
						</select> <span class="spansTow">翻转：</span> <select id="flip"
							class="selectDivOne" name="dept" data-options="editable:false">
							<option value="0">禁用</option>
							<option value="1">启用</option>
						</select> 重试次数：<input type="number" class="inputs" name="retryCount"
							value="0">
					</div>
					<div class="divTow" id="dynamicAlgorithmDiv">
						起始Q值：<input name="startingQValue" type="number" class="inputs"
							value="4"> <span class="spansTow">Q值范围：</span> <input
							type="number" class="inputs" name="minQ" value="0">&nbsp;-&nbsp;-&nbsp;
						<input name="maxQ" type="number" class="inputs" value="15">
						<span class="spansTow">阀值：</span><input type="number"
							class="inputs" name="threshold" value="4">
					</div>
					<div class="divTow" id="fixedAlgorithmDiv" style="display: none;">
						Q值：<input name="QValue" type="number" class="inputs" value="4">
						<span class="spansTow">是否重复：</span> <select class="selectDiv"
							id="isRepeat" name="dept" data-options="editable:false">
							<option value="0">禁用</option>
							<option value="1">启用</option>
						</select>
					</div>
					<div class="divThree">
						<button class="" type="button" onclick="algorithmic('get')">获取</button>
						<button class="" type="button" onclick="algorithmic('set')">设置</button>
					</div>
				</fieldset>
				<fieldset class="content">
					<legend>Query参数</legend>
					<div>
						<span class="spansFour"> Target： <select
							id="queryParameter" class="selectDiv" name="dept"
							data-options="editable:false">
								<option value="0">A</option>
								<option value="1">B</option>
						</select>
						</span> <span class="spansFive">Session：</span> <select id="session"
							class="selectDiv" name="dept" data-options="editable:false">
							<option value="0">Session0</option>
							<option value="1">Session1</option>
							<option value="2">Session2</option>
							<option value="3">Session3</option>
						</select> <span class="spansFive">Sel：</span> <select id="selectAll"
							class="selectDiv" name="dept" data-options="editable:false">
							<option value="0">SELECT_ALL</option>
							<option value="1">SELECT_DEASSERTED</option>
							<option value="2">SELECT_ASSERTED</option>

						</select>
						<button class="btnDiv" type="button" onclick="query('get')">获取</button>
						<button type="button" onclick="query('set')">设置</button>

					</div>
				</fieldset>
				<!--  <fieldset class="content">
                <legend>系统</legend>
                <div class="divFour">
                    <span class="spansSix">升级：<input type="text" class="inputDiv"></span>&nbsp;<span
                        class="spansSeven"><input type="text" class="inputDiv"></span>
                    <button class="">浏览...</button>
                    <button class="">升级</button>

                </div>
            </fieldset> -->
			</form>
		</div>
		<div title="掩码设置" style="padding: 20px; display: none;">
			<form>
				<fieldset class="contentOne">
					<legend>Post-Singulation</legend>
					<div>
						<input type="checkbox" name="maskStart" id="inputOne" value="">
						<label for="inputOne">启用</label>
						<button class=" btnList" type="button" onclick="maskSet('get')">获取</button>
						<button class=" btnList" type="button" onclick="maskSet('set')">设置</button>

					</div>
					<div>
						起始偏移(bit)：<input name="startOffset" value="0" type="number"
							class="inputs"> <span class="spansThree">Mask值(hex)：</span><input
							name="maskValue" class="inputDiv">
					</div>
				</fieldset>
				<fieldset class="contentOne">
					<legend>SelectCriteria</legend>
					<div>
						<input type="checkbox" name="criteriaStart" id="inputTow" value="">
						<label for="inputTow">启用</label>
						<button class=" btnList" type="button"
							onclick="maskSet('criteriaGet')">获取</button>
						<button class=" btnList" type="button"
							onclick="maskSet('criteriaSet')">设置</button>

					</div>
					<div>
						区域：<select class="selectDiv" id="criteriaArea" name="dept"
							data-options="editable:false">
							<option value="0">RFU</option>
							<option value="1">EPC</option>
							<option value="2">TID</option>
							<option value="3">USER</option>
						</select> <span class="spansThree">会话：</span> <select class="selectDiv"
							id="criteriaSession" name="dept" data-options="editable:false">
							<option value="0">S0</option>
							<option value="1">S1</option>
							<option value="2">S2</option>
							<option value="3">S3</option>
							<option value="4">SELECTED</option>
						</select> <span class="spansThree">截取：</span> <select class="selectDiv"
							id="criteriaInterception" name="dept"
							data-options="editable:false">
							<option value="0">禁用</option>
							<option value="1">启用</option>
						</select> <span class="spansThree">Action：</span> <select class="selectDiv"
							id="criteriaAction" name="dept" data-options="editable:false">
							<option value="0">ASLINVA_DSLINVB</option>
							<option value="1">ASLINVA_NOTHING</option>
							<option value="2">NOTHING_DSLINVB</option>
							<option value="3">NSLINVS_NOTHING</option>
							<option value="4">DSLINVB_ASLINVA</option>
							<option value="5">DSLINVB_NOTHING</option>
							<option value="6">NOTHING_ASLINVA</option>
							<option value="7">NOTHING_NSLINVS</option>
						</select>
					</div>
					<div>
						起始偏移(bit)：<input type="number" class="inputs"
							name="criteriaStartOffset" value="0"> <span
							class="spansFour">长度(bit)：</span><span class="lenSpan"> <input
							name="criteriaLength" class="inputDiv" value="0"></span> <span
							class="spansFour">Mask值(hex)：</span> <input
							name="criteriaMaskValue" class="inputDiv">
					</div>
				</fieldset>
			</form>
		</div>
		<div title="一体机设置" style="padding: 20px; display: none;">
			<form class="formDiv">
				<div class="AIOSetL">
					<fieldset>
						<legend>常规设置</legend>
						<div>
							<span class="spansFive">RF模块：</span> <span class="bigSpan">
								<select id="rfModule" class="selectDiv" name="dept">
									<option value="0">R2000</option>
									<option value="1">RM8011</option>
							</select>
							</span>
							<button class="btnList" type="button"
								onclick="generalSet('rfGet')">获取</button>
							<button class="" type="button" onclick="generalSet('rfSet')">设置</button>
						</div>
						<div>
							<span class="spansFive">心跳：</span> <span class="smallSpan">
								<select id="heartbeatSwitch" class="selectDiv" name="dept">
									<option value="0">Off</option>
									<option value="1">On</option>
							</select>
							</span> <span class="smallSpan"> <select id="heartbeatTime"
								class="selectDiv" name="dept">
									<option value="0">5</option>
									<option value="1">10</option>
									<option value="2">15</option>
									<option value="3">20</option>
									<option value="4">30</option>
									<option value="5">60</option>
							</select> 秒
							</span>
							<button class="btnList" type="button"
								onclick="generalSet('heartbeatGet')">获取</button>
							<button class="" type="button"
								onclick="generalSet('heartbeatSet')">设置</button>
						</div>
					</fieldset>
					<fieldset>
						<legend>盘点</legend>
						<div>
							<span class="spansFive">盘点模式：</span> <span class="bigSpan">
								<select id="mode" class="selectDiv" name="dept">
									<option value="0">手动盘点</option>
									<option value="1">开机盘点</option>
									<option value="2">触发盘点</option>
							</select>
							</span>
							<button class="btnList" type="button"
								onclick="inventoryModel('get')">获取</button>
							<button class="" type="button" onclick="inventoryModel('set')">设置</button>
							<br />
						</div>
						<div>
							<span class="spansFive">触发引脚：</span> <span class="bigSpan">
								<select id="triggerPin" class="selectDiv" name="dept"
								disabled="disabled">
									<option value="0">DIN0</option>
									<option value="1">DIN1</option>
									<option value="2">DIN2</option>
									<option value="3">DIN3</option>
									<option value="4">ANY</option>
							</select>
							</span> <span class="secSpan"> <select id="triggerPins"
								class="selectDiv" name="dept" disabled="disabled">
									<option value="0">低电平</option>
									<option value="1">高电平</option>
							</select>
							</span>
						</div>
						<div>
							<span class="spansFive">误触：</span> <span class="smallInput">
								<input name="accident" class="inputDiv" value="0"
								disabled="disabled" />
							</span> 盘点： <span class="smallInput"> <input name="inventoryTime"
								class="inputDiv" value="0" disabled="disabled" />ms
							</span> <input type="checkbox" name="mostTimes" id="inputThree"
								value="0" disabled="disabled" style="background-color: #E8E8E8;">
							<label for="inputThree">最多次数</label>
						</div>
					</fieldset>
					<fieldset>
						<legend>网络配置</legend>
						<div>
							<span class="spansFive">网络类型：</span> <span class="bigSpan">
								<select id="networkType" class="selectDiv" name="dept">
									<option value="0">有线网络</option>
									<option value="1">Wifi</option>
							</select>
							</span>
						</div>
						<div>
							<span class="spansFive">IP 地址：</span> <span class="bigInput">
								<input name="iPAddress" id="iPAddress" class="inputDiv"
								value="192.168.1.16" />
							</span>
						</div>
						<div>
							<span class="spansFive">网关：</span> <span class="bigInput">
								<input name="gateway" class="inputDiv" value="192.168.1.1" />
							</span> <input type="checkbox" name="pingGateway" id="inputFour"
								value="0"> <label for="inputFour">Ping网关</label>
						</div>
						<div>
							<span class="spansFive">子网掩码：</span> <span class="bigInput">
								<input name="subnetMask" class="inputDiv" value="255.255.255.0" />
							</span>
						</div>
						<div>
							<span class="spansFive">MAC地址：</span> <span
								class="smallInputList"> <input name="MACAddressA"
								class="inputDiv" value="15" />- <input name="MACAddressB"
								class="inputDiv" value="3A" />- <input name="MACAddressC"
								class="inputDiv" value="C2" />- <input name="MACAddressD"
								class="inputDiv" value="78" />- <input name="MACAddressE"
								class="inputDiv" value="01" />- <input name="MACAddressF"
								class="inputDiv" value="BD" />
							</span>
						</div>
						<div>
							<div>
								<span>位机IP/端口：</span> <span class="bigInput"> <input
									name="machineIP" id="machineIP" class="inputDiv"
									value="192.168.1.123" />
								</span> <span class="smallInput"> <input name="machinePort"
									id="machinePort" class="inputDiv" value="1400" />
								</span>
							</div>

							<div>
								<span class="spansFive">WIFI SSID：</span> <span class="secInput">
									<input name="wifiSSID" class="inputDiv" disabled="disabled">
								</span>
							</div>
							<div>
								<span class="spansFive">WIFI密码：</span> <span class="secInput">
									<input name="wifiPassword" class="inputDiv" disabled="disabled">
								</span>
								<button class="oneList" type="button"
									onclick="networkConfiguration('get')">获取</button>
								<button class="" type="button"
									onclick="networkConfiguration('set')">设置</button>
							</div>
						</div>
					</fieldset>
				</div>
				<div class="AIOSetR">
					<fieldset>
						<legend>GPIO配置</legend>
						<div>
							<span class="spansFive">输出端口：</span> <span class="rightSpan">
								<select class="selectDiv" id="outPutPort" name="dept">
									<option value="0">DOUT0</option>
									<option value="1">DOUT1</option>
									<option value="2">DOUT2</option>
									<option value="3">DOUT3</option>
							</select>
							</span> <span class="rightSpan"> <select class="selectDiv"
								id="outPutPorts" name="dept">
									<option value="0">低</option>
									<option value="1">高</option>
							</select>
							</span>

							<button class="btnList" type="button"
								onclick="GPIOConfiguration('outPutPortGet')">获取</button>
							<button class="" type="button"
								onclick="GPIOConfiguration('outPutPortSet')">设置</button>
							<br />
						</div>
						<div>
							<div>
								<span class="spansFive">启动测试：</span>
								<div class="setDiv" id="startTestDiv"
									style="display: inline-block; vertical-align: top; line-height: 20px;">
									<input type="checkbox" name="" id="D7" value=""> <label
										for="D7">D7</label> <input type="checkbox" name="" id="D6"
										value=""> <label for="D6">D6</label> <input
										type="checkbox" name="" id="D5" value=""> <label
										for="D5">D5</label> <input type="checkbox" name="" id="D4"
										value=""> <label for="D4">D4</label><br> <input
										type="checkbox" name="" id="D3" value=""> <label
										for="D3">D3</label> <input type="checkbox" name="" id="D2"
										value=""> <label for="D2">D2</label> <input
										type="checkbox" name="" id="D1" value=""> <label
										for="D1">D1</label> <input type="checkbox" name="" id="D0"
										value=""> <label for="D0">D0</label>
								</div>
								<div class="btnDiv">
									<button class="" type="button"
										onclick="GPIOConfiguration('startTestGet')">获取</button>
									<button class="" type="button"
										onclick="GPIOConfiguration('startTestSet')">设置</button>
								</div>
							</div>
						</div>
						<div>
							<span class="spansFive">输入端口：</span> <span class="rightSpan">
								<select id="inPutPort" class="selectDiv" name="dept">
									<option value="0">DIN0</option>
									<option value="1">DIN1</option>
									<option value="2">DIN2</option>
									<option value="3">DIN3</option>
							</select>
							</span> <span class="rightSpan"> <select class="selectDiv"
								id="inPutPorts" name="dept">
									<option value="0">低</option>
									<option value="1">高</option>
							</select>
							</span>

							<button class="rightBtn" type="button"
								onclick="GPIOConfiguration('inPutPortGet')">获取</button>
						</div>
					</fieldset>
					<fieldset>
						<legend>标签触发报警</legend>
						<div>
							<span>报警：</span> <span class="rightSpan"> <select
								id="callPolice" class="selectDiv" name="dept">
									<option value="0">DOUT0</option>
									<option value="1">DOUT1</option>
									<option value="2">DOUT2</option>
									<option value="3">DOUT3</option>
									<option value="4">ALL</option>
							</select>
							</span> <span class="rightSpan"> <select id="police"
								class="selectDiv" name="dept">
									<option value="0">低电平</option>
									<option value="1">高电平</option>
							</select>
							</span> 误触： <span class="smallInputList"> <input
								name="accidentTime" class="inputDiv" value="0" />秒
							</span> 持续：<span class="smallInputList"> <input
								name="continuousTime" class="inputDiv" value="3" />秒
							</span>
						</div>
						<div>
							<span>上报次数：</span> <span class="smallInputList"> <input
								name="reportNum" class="inputDiv" value="1" />次
							</span>
						</div>
						<div>
							<span>模式：</span> <span class="rightSpan"> <select
								id="model" class="selectDiv" name="dept">
									<option value="0">特定标签</option>
									<option value="1">任何标签</option>
									<option value="2">无标签</option>
							</select>
							</span> 偏移字节： <span class="smallInputList"> <input
								name="offsetBytes" class="inputDiv" value="0" />
							</span> EPC： <span class="secInputList"> <input name="EPCValue"
								class="inputDiv" />
							</span>
						</div>
						<div>
							<span>状态：</span> <span class="bigInputList"> <input
								name="state" class="inputDiv" value="无报警信息">
							</span>
						</div>
						<div class="noFloat">
							<button class="btnOne" type="button"
								onclick="tagTriggerAlarm('set')">设置</button>
							<button class="" type="button" onclick="tagTriggerAlarm('get')">获取</button>
							<button class="btnTow" type="button"
								onclick="tagTriggerAlarm('enableAlarm')">启用报警</button>
							<button class="" type="button"
								onclick="tagTriggerAlarm('disableAlarm')">禁用报警</button>
						</div>
					</fieldset>
					<fieldset>
						<legend>输入触发报警</legend>
						<div>
							<span class="spansFive">状态：</span> <span class="rightSpan">
								<select id="triggerState" class="selectDiv" name="dept">
									<option value="0">Off</option>
									<option value="1">On</option>
							</select>
							</span> 误触：<span class="smallInput"><input
								name="inputAccidentTime" class="inputDiv" value="0">ms</span>
							持续：<span class="smallInput"><input
								name="inputContinuousTime" class="inputDiv" value="2000"
								readonly="readonly" />ms</span>
						</div>
						<div>
							<span class="spansFive">触发引脚：</span> <span class="rightSpan">
								<select id="inputTriggerPin" class="selectDiv" name="dept"
								disabled="disabled">
									<option value="0">DIN0</option>
									<option value="1">DIN1</option>
									<option value="2">DIN2</option>
									<option value="3">DIN3</option>
									<option value="4">ANY</option>
							</select>
							</span> <span class="rightSpan"> <select
								id="inputTriggerPinState" class="selectDiv" name="dept"
								disabled="disabled">
									<option value="0">低电平</option>
									<option value="0">高电平</option>
							</select>
							</span>
						</div>
						<div>
							<span class="spansFive">报警：</span> <span class="rightSpan">
								<select id="inputCallPolice" class="selectDiv" name="dept"
								disabled="disabled">
									<option value="0">DOUT0</option>
									<option value="1">DOUT1</option>
									<option value="2">DOUT2</option>
									<option value="3">DOUT3</option>
									<option value="4">ALL</option>
							</select>
							</span> <span class="rightSpan"> <select
								id="inputCallPoliceState" class="selectDiv" name="dept"
								disabled="disabled">
									<option value="0">低电平</option>
									<option value="1">高电平</option>
							</select>
							</span>
							<button class="btnDiv" type="button"
								onclick="inputTriggerAlarm('get')">获取</button>
							<button class="" type="button" onclick="inputTriggerAlarm('set')">设置</button>

						</div>
					</fieldset>
				</div>
				<fieldset class="tetFie">
					<legend>定制HTTP上传信息</legend>
					<div>
						<span class="spansFive">方法类型：</span> <span class="rightSpan">
							<span class="radSpan" id=""> <input type="radio"
								name="httpType" class="httpType" id="httpTypeGet" value="Get" />
								Get
						</span> <span class="radSpan" id=""> <input type="radio"
								name="httpType" class="httpType" id="httpTypePost" value="Post"
								checked="checked" /> Post
						</span>
						</span> <span class="spansFive">Action：</span> <span class="rightSpan">
							<span class="smallInput"> <input name="action" id="action"
								oninput="onAction()" class="inputDiv" style="width: 150px;" />
						</span>
						</span> <span class="spansFive">特定信息：</span> <span class="rightSpan">
							<span class="smallInput"> <input name="parameter"
								id="parameter" oninput="onParameter()" class="inputDiv"
								style="width: 150px;" />
						</span>
						</span>
					</div>
					<div class="AIOSetL" style="width: 100%;">
						<span class="spansFive">内容：</span> <input type="checkbox"
							name="content" onclick="onContent()" id="From" value="">
						<label for="From">From</label> <input type="checkbox"
							name="content" onclick="onContent()" id="EPC" value=""> <label
							for="EPC">EPC</label> <input type="checkbox" name="content"
							onclick="onContent()" id="拓展数据" value=""> <label
							for="拓展数据">拓展数据</label> <input type="checkbox" name="content"
							onclick="onContent()" id="PC" value=""> <label for="PC">PC</label>
						<input type="checkbox" name="content" onclick="onContent()"
							id="天线号" value=""> <label for="天线号">天线号</label> <input
							type="checkbox" name="content" onclick="onContent()" id="RSSI"
							value=""> <label for="RSSI">RSSI</label> <input
							type="checkbox" name="content" onclick="onContent()" id="Host"
							value=""> <label for="Host">Host</label> <input
							type="checkbox" name="content" onclick="onContent()"
							id="KeepAlive" value=""> <label for="KeepAlive">Keep
							alive</label>
					</div>
					<div class="fieldsets2">
						<div style="width: 50%; float: left;">
							<span class="spansFive">预览(参考)：</span>
							<textarea cols="50" rows="3" id="dataUrl" wrap="off"></textarea>
						</div>
						<div class="AIOSetL"
							style="width: 50%; float: right; margin-right: 0px;">
							<div style="width: 50%; float: left; margin-left: 20px;">
								<input type="checkbox" name="" id="statusType" value="0">
								<label for="statusType">启用</label>
							</div>
							<div style="width: 40%; float: right;">
								<button class="btnDiv" type="button"
									onclick="httpReportAlarm('get')">获取</button>
								<button class="" type="button" onclick="httpReportAlarm('set')">设置</button>
							</div>
						</div>
					</div>
				</fieldset>
				<fieldset class="tetFie">
					<legend>系统</legend>
					<!-- <div>
                    <span class="spansFive">升级：</span>
                    <span class="bigInputOne">
                        <input class="inputDiv">
                    </span>
                    <span class="bigInputTow">
                        <input class="inputDiv" value="状态">
                    </span>

                    <button class="">浏览…</button>
                    <button class="">升级</button>
                </div> -->
					<div>
						<span class="spansFive">版本号：</span> <span class="bigInputThree">
							<input class="inputDiv" name="versionNum">
						</span>
						<button class="" type="button"
							onclick="system('getBoardSoftVersion')">获取</button>
						<button class="" type="button" onclick="system('boardReboot')">设备重启</button>
					</div>
				</fieldset>
			</form>
		</div>
		<div title="参数设置" style="padding: 20px; display: none;">
			<form>
				<fieldset class="firstFie">
					<legend>常规</legend>
					<div>
						<span class="spansFour">工作模式：</span> <select id="workMode"
							class="selectDiv" name="dept">
							<option value="0" selected>High Sensitivity</option>
							<option value="1">Dense Reader</option>
						</select>
						<button class=" btnList" type="button"
							onclick="parameterSet('workModeSet')">设置</button>
					</div>
					<div>
						<span class="spansFour">功率：</span> <select id="power"
							class="selectDiv" name="dept">
							<option value="0">19</option>
							<option value="1">20</option>
							<option value="2">21</option>
							<option value="3">22</option>
							<option value="4">23</option>
							<option value="5">24</option>
							<option value="6">25</option>
							<option value="7">26</option>
							<option value="8">27</option>
							<option value="9">28</option>
							<option value="10">29</option>
							<option value="11" selected>30</option>
						</select>
						<button class=" btnList" type="button"
							onclick="parameterSet('powerSet')">设置</button>
						<button class=" btnList" type="button"
							onclick="parameterSet('powerGet')">获取</button>
					</div>
					<div>
						<span class="spansFour">区域：</span> <select id="parmArea"
							class="selectDiv" name="dept">
							<option value="0" selected>中国920-925M</option>
							<option value="1">中国840-845M</option>
							<option value="2">美国902-928M</option>
							<option value="3">欧洲865-868</option>
							<option value="4">韩国917-924</option>
						</select>
						<button class=" btnList" type="button"
							onclick="parameterSet('areaSet')">设置</button>
						<button class=" btnList" type="button"
							onclick="parameterSet('areaGet')">获取</button>
					</div>
					<div>
						<span class="spansFour">频点：</span> <select class="selectDiv"
							id="paramFrequency" name="dept">

						</select>
						<button class=" btnList" type="button"
							onclick="parameterSet('frequencySet')">设置</button>
						<button class=" btnList" type="button"
							onclick="parameterSet('frequencyGet')">获取</button>
					</div>
				</fieldset>
				<fieldset class="lastFie">
					<legend>Query参数</legend>
					<div>
						<span class="spansFour">DR：</span> <select class="selectDiv"
							id="DRSelect" name="dept" disabled="disabled">
							<option value="0">8</option>
							<option value="1">64</option>
						</select> <span class="spansFour">M：</span> <select class="selectDiv"
							name="dept" id="MSelect" disabled="disabled">
							<option value="0">1</option>
							<option value="1">2</option>
							<option value="2">3</option>
							<option value="3">4</option>
						</select> <span class="spansFour">TRext：</span> <span class="lastSpan">
							<select class="selectDiv" name="dept" id="TRextSelect"
							disabled="disabled">
								<option value="0">No pilot tone</option>
								<option value="1">Use pilot tone</option>
						</select>
						</span> <span class="spansFour">Sel：</span> <span class="lastSpan">
							<select class="selectDiv" name="dept" id="SelSelect">
								<option value="0">All(00)</option>
								<option value="1">All(01)</option>
								<option value="2">~SL(10)</option>
								<option value="3">SL(11)</option>
						</select>
						</span> <span class="spansFour">Session：</span> <span class="lastSpan">
							<select class="selectDiv" id="SessionSelect" name="dept">
								<option value="0">S0</option>
								<option value="1">S1</option>
								<option value="2">S2</option>
								<option value="3">S3</option>
						</select>
						</span>
					</div>
					<div>
						<span class="spansFour">Target：</span> <select class="selectDiv"
							name="dept" id="TargetSelect">
							<option value="0">A</option>
							<option value="1">B</option>
						</select> <span class="spansFour">Q：</span> <input type="number"
							class="inputs" value="4" name="QInput">
						<button class=" btnList" type="button" onclick="queryParam('set')">设置</button>
						<button type="button" onclick="queryParam('get')">获取</button>
					</div>
				</fieldset>
			</form>

		</div>
	</div>
	<footer> <span id="testWriter"></span> </footer>
	<script type="text/javascript">


/* $(".connectBtn").click(function (e) {
    e.preventDefault();
    for (var i = 0; i < $(".radSpan").length; i++) {
        if ($("input[name=modelType]:checked").val() == 0) {
            openTabOne();
            mystorage.set('geValue', '0');
        } else if ($("input[name=modelType]:checked").val() == 1) {
            openTabTow();
            mystorage.set('geValue', '1')
        } else if ($("input[name=modelType]:checked").val() == 2) {
            openTabThree();
            mystorage.set('geValue', '2')
        }
    }
}); */





function init() {
    getIndex();
    var getMystorage = mystorage.get('geValue');
    var getTab = mystorage.get('getTab');
    var getBtn = mystorage.get('getBtn');
    var connectTypes = mystorage.get('getConnectType'); 
    var paramAntNum = mystorage.get('antNum'); 
   
    if(getMystorage == false){
    	tabsClos();
    }else{
    	if (getMystorage == 0) {
            openTabOne();
            $(":radio[name='modelType'][value='0']").prop("checked", "checked");
            $('#tt').tabs('select', getTab);
            //根据存储的连接方式显示给不同的div是连接还是断开的状态
            if(connectTypes=='connectRF'){//串口模式
            	 $("#serialConn").html(getBtn);
            }else{//网络模式
            	 $("#NetworkConn").html(getBtn);
            }
        } else if (getMystorage == 1) {
        	if(paramAntNum=='1'){
        		  openTabTow('1');
        	}else{
        		openTabTow('2');
        	}
          
            $(":radio[name='modelType'][value='1']").prop("checked", "checked");
            $('#tt').tabs('select', getTab);
            if(connectTypes=='connectRF'){
           	 $("#serialConn").html(getBtn);
           }else{
           	 $("#NetworkConn").html(getBtn);
           }        } else if (getMystorage == 2) {
            openTabThree();
            $(":radio[name='modelType'][value='2']").prop("checked", "checked");
            $('#tt').tabs('select', getTab);
            if(connectTypes=='connectRF'){
           	 $("#serialConn").html(getBtn);
           }else{
           	 $("#NetworkConn").html(getBtn);
           }
        }
    }
   
    	
    
}







//显示全部页面
function tabs() {
	var arr=["设备连接","盘点测试","读写测试","权限设置","天线设置","通讯设置","掩码设置","一体机设置","参数设置"];
    for (var i = 0; i < arr.length; i++) {
        tab_option = $('#tt').tabs('getTab', arr[i]).panel('options').tab;
        tab_option.show(); //隐藏数据录入tab
    }
}
//隐藏相对应的页面
function tabsCloseOne() {
	var arr=["一体机设置","参数设置"];
    for (var i = 0; i < arr.length; i++) {
        tab_option = $('#tt').tabs('getTab', arr[i]).panel('options').tab;
        tab_option.hide(); //隐藏数据录入tab
    }

}
//隐藏相对应的页面
function tabsCloseTow() {
	var arr=["一体机设置","通讯设置"];
    for (var i = 0; i < arr.length; i++) {
        tab_option = $('#tt').tabs('getTab', arr[i]).panel('options').tab;
        tab_option.hide(); //隐藏数据录入tab
    }
}

function tabsCloseTows() {
	var arr=["一体机设置","通讯设置","天线设置"];
    for (var i = 0; i < arr.length; i++) {
        tab_option = $('#tt').tabs('getTab', arr[i]).panel('options').tab;
        tab_option.hide(); //隐藏数据录入tab
    }
}

//断开时，隐藏其它页面
function tabsClos(){
	var arr=["盘点测试","读写测试","权限设置","天线设置","通讯设置","掩码设置","一体机设置","参数设置"];
	for(var i=0;i<arr.length;i++){
		tab_option = $('#tt').tabs('getTab', arr[i]).panel('options').tab;
		tab_option.hide(); //隐藏数据录入tab
   }
	 getMystorage = false;
}

//隐藏相对应的页面
function tabsCloseThree() {
	var arr=["参数设置"];
    for (var i = 0; i < arr.length; i++) {
        tab_option = $('#tt').tabs('getTab', arr[i]).panel('options').tab;
        tab_option.hide(); //隐藏数据录入tab
    }
}
//存储被选中的页签
function getIndex() {
    $('#tt').tabs({
        onSelect: function (title) {
            mystorage.set('getTab', title)
        }
    });
}

function openTabOne() {
    tabs();
    tabsCloseOne();
    $(".getTableTow").hide();
    $(".getTableOne").show();
}
function openTabTow(param) {
    tabs();
    if(param=='1'){
    	tabsCloseTows();
    	 $(".getTableTow").hide();
    	 $(".getTableOne").hide();
    }else{
    	tabsCloseTow();
    	 $(".getTableTow").show();
    	 $(".getTableOne").hide();
    }
   
}
function openTabThree() {
    tabs();
    tabsCloseThree();
    $(".getTableTow").hide();
    $(".getTableOne").show();
}







    $("#algorithm").change(function(){
        console.log($(this).val())
	    if($(this).val()==0){
	        $("#fixedAlgorithmDiv").show();
	        $("#dynamicAlgorithmDiv").hide();
	    }
	    if($(this).val()==1){
	        $("#dynamicAlgorithmDiv").show();
	        $("#fixedAlgorithmDiv").hide();
	    }
    });
   
    //盘点区域为epc时设置只读
    $("#area").change(function(){
        console.log($(this).val())
	    if($(this).val()==0){
	    	$("#start").attr("disabled",true);
	        $("#length").attr("disabled",true);
	        
	    }else{
	    	$("#start").attr("disabled",false);
	        $("#length").attr("disabled",false);
	    }
    });
    
    
    //一体机设置中  如果盘点模式选择触发盘点   下面的5个才能选
    $("#mode").change(function(){
        console.log($(this).val())
	    if($(this).val()!=2){
	    	$('#triggerPin').attr("disabled",true);
	    	$('#triggerPins').attr("disabled",true);
	    	$('input[name=accident]').attr("disabled",true);
	    	$('input[name=inventoryTime]').attr("disabled",true);
	    	$('input[name=mostTimes]').attr("disabled",true);
	    	                  
	    }else{
	    	$('#triggerPin').attr("disabled",false);
	    	$('#triggerPins').attr("disabled",false);
	    	$('input[name=accident]').attr("disabled",false);
	    	$('input[name=inventoryTime]').attr("disabled",false);
	    	$('input[name=mostTimes]').attr("disabled",false);
	    }
    });
    
    
    
  //一体机设置中  网络配置
    $("#networkType").change(function(){
        console.log($(this).val())
	    if($(this).val()==0){//选有限网络时，ssid和密码不可用
	    	$('input[name=wifiSSID]').attr("disabled",true) ;
	    	$('input[name=wifiPassword]').attr("disabled",true);
	    	
	    	$('input[name=pingGateway]').attr("disabled",false);
	    	$('input[name=MACAddressA]').attr("disabled",false);
	    	$('input[name=MACAddressB]').attr("disabled",false);
	    	$('input[name=MACAddressC]').attr("disabled",false);
	    	$('input[name=MACAddressD]').attr("disabled",false);
	    	$('input[name=MACAddressE]').attr("disabled",false);
	    	$('input[name=MACAddressF]').attr("disabled",false);
	    }else{//选wifi时，mac地址和ping网关不可用
	    	$('input[name=pingGateway]').attr("disabled",true);
	    	$('input[name=MACAddressA]').attr("disabled",true);
	    	$('input[name=MACAddressB]').attr("disabled",true);
	    	$('input[name=MACAddressC]').attr("disabled",true);
	    	$('input[name=MACAddressD]').attr("disabled",true);
	    	$('input[name=MACAddressE]').attr("disabled",true);
	    	$('input[name=MACAddressF]').attr("disabled",true);
	    	
	    	$('input[name=wifiSSID]').attr("disabled",false);
	    	$('input[name=wifiPassword]').attr("disabled",false);
	    }
    });
    
  //标签触发报警   的模式选择
  $("#model").change(function(){
        console.log($(this).val())
	    if($(this).val()==0){//为特定标签时，偏移字节和epc可用
	    	$('input[name=offsetBytes]').attr("disabled",false);
			$('input[name=EPCValue]').attr("disabled",false);
	    }else{//为任何标签/无标签时，偏移字节和epc不可用
	    	$('input[name=offsetBytes]').attr("disabled",true);
			$('input[name=EPCValue]').attr("disabled",true);
	    }
    });
    
  //一体机设置中 输入触发报警选择Off时，除了误触，其它5个都不可用。。
    $("#triggerState").change(function(){
        console.log($(this).val())
	    if($(this).val()==0){
			$('input[name=inputContinuousTime]').attr("readOnly",true);
			$('#inputTriggerPin').attr("disabled",true);
			$('#inputTriggerPinState').attr("disabled",true);
			$('#inputCallPolice').attr("disabled",true);
			$('#inputCallPoliceState').attr("disabled",true);
	    }else{
	    	$('input[name=inputContinuousTime]').attr("readOnly",false);
			$('#inputTriggerPin').attr("disabled",false);
			$('#inputTriggerPinState').attr("disabled",false);
			$('#inputCallPolice').attr("disabled",false);
			$('#inputCallPoliceState').attr("disabled",false);
	    }
    });
  
    //页面加载默认显示如下
    var i=0;
    var freq = 920125; 
	while (freq <= 924875) {
		// --- 你要添加的频率值(/1000) 
		//根据id查找对象，
		var obj=document.getElementById('paramFrequency');
		//添加一个选项
		//obj.add(new Option("文本","值")); //这个只能在IE中有效
		obj.options.add(new Option(freq/1000,i)); //这个兼容IE与firefox
		freq += 250;
		 i++;
	}
    
    //区域选择时显示不同的频点
    $("#parmArea").change(function(){
    	//删除所有选项option
    	 var obj=document.getElementById('paramFrequency');
    	 obj.options.length=0;
    	 //获取选中的值
        var value=$(this).val();
        var startFreq = 0, endFreq = 0, step = 0;
    	switch (parseInt(value)) {
    	case 0:
    		// 中国 920-925
    		startFreq = 920125;
    		endFreq = 924875;
    		step = 250;
    		break;
    	case 1:
    		// 中国 840-845
    		startFreq = 840125;
    		endFreq = 844875;
    		step = 200;
    		break;
    		
    	case 2:
    		// 美国 902-928
    		startFreq = 902250;
    		endFreq = 924875;
    		step = 500;
    		break;
    	case 3:
    		// 欧洲 865-867
    		startFreq = 865100;
    		endFreq = 867900;
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
    	var i=0;
    	while (freq <= endFreq) {
    		// --- 你要添加的频率值(/1000) 
    		//根据id查找对象，
    		var obj=document.getElementById('paramFrequency');
    		//添加Option
    		//obj.add(new Option("文本","值")); //这个只能在IE中有效
    		                         //文本和value值
    		obj.options.add(new Option(freq/1000,i)); //这个兼容IE与firefox
    		freq += 250;
    		 i++;
    	}
    });
    
    $("#dataUrl").html("Post HTTP/1.1");
    var httpType="Post";//方法类型
    var action="";//Action
    var parameter="";//特定信息
    var content="";//内容
    var dataUrl="";//拼接内容 
    var host ="";
    $(".httpType").click(function(){
    	httpType = $(this).val();
    	if(httpType != ""){
    		dataUrl = httpType+" ";
    	}
    	if(action != ""){
    		dataUrl += ""+action+"?";
    	}
    	if(parameter != ""){
    		dataUrl += parameter;
    	}    
    	if(content != ""){
    		dataUrl += content;
    	}
    	if(host != ""){
    		$("#dataUrl").html(dataUrl.trim()+" "+"HTTP/1.1"+"&#10"+host);	
    	}else{
    		$("#dataUrl").html(dataUrl.trim()+" "+"HTTP/1.1");
    	}
    });
    
    function onAction(){
    	action = $("#action").val();
    	if(httpType != ""){
    		dataUrl = httpType+" ";
    	}
    	if(action != ""){
    		dataUrl += ""+action+"?";
    	}
    	if(parameter != ""){
    		dataUrl += parameter;
    	}    
    	if(content != ""){
    		dataUrl += content;
    	}
    	if(host != ""){
    		$("#dataUrl").html(dataUrl+" "+"HTTP/1.1"+"&#10"+host);	
    	}else{
    		$("#dataUrl").html(dataUrl+" "+"HTTP/1.1");
    	}	  
    }
    
    function onParameter(){
    	parameter = $("#parameter").val();
    	if(httpType != ""){
    		dataUrl = httpType+" ";
    	}
    	if(action != ""){
    		dataUrl += ""+action+"?";
    	}
    	if(parameter != ""){
    		dataUrl += parameter;
    	}    
    	if(content != ""){
    		dataUrl += content;
    	}
    	if(host != ""){
    		$("#dataUrl").html(dataUrl+" "+"HTTP/1.1"+"&#10"+host);	
    	}else{
    		$("#dataUrl").html(dataUrl+" "+"HTTP/1.1");
    	}
    }

    function onContent(){
    	content = "";
    	host = "";
    	$('input[name="content"]:checked').each(function(){
	    	if($(this).attr("id") == "From"){
	    		content += "FromDev="+$("#iPAddress").val()+"&";
	    	}
	    	if($(this).attr("id") == "EPC"){
	    		content += "epc=EPC_VAL"+"&";
	    	}
	    	if($(this).attr("id") == "拓展数据"){
	    		content += "externData=externData_VAL"+"&";
	    	}
	    	if($(this).attr("id") == "PC"){
	    		content += "pc=PC_VAL"+"&";
	    	}
	    	if($(this).attr("id") == "天线号"){
	    		content += "antPort=ant_VAL"+"&";
	    	}
	    	if($(this).attr("id") == "RSSI"){
	    		content += "rssi=RSSI_VAL"+"&";
	    	}
	    	if($(this).attr("id") == "Host"){
	    		host = "Host:"+$("#machineIP").val()+":"+$("#machinePort").val();
	    	}	 
	    });
	    if(httpType != ""){
    		dataUrl = httpType+" ";
    	}
    	if(action != ""){
    		dataUrl += ""+action+"?";
    	}
    	if(parameter != ""){
    		dataUrl += parameter;
    	} 
	    if(content != ""){
		    //去掉最后一个&
		    content = content.substr(0, content.length-1);
    		dataUrl += content;
    	}
    	if(host != ""){
    		$("#dataUrl").html(dataUrl+" "+"HTTP/1.1"+"&#10"+host);	
    	}else{
    		$("#dataUrl").html(dataUrl+" "+"HTTP/1.1");
    	}    
    }
    </script>


</body>
</html>

