<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>


	<script type="text/javascript">

	$(function(){
		$("#addBtn").click(function () {
			/*
			操作模态窗口的方式， 找到需要操作的模态窗口的Jquery对象，然后调用modal方法，为该方法传递参数
			show:表示打开模态窗口
			hide:表示关闭模态窗口
			现在我们可以alert（123），没有被写死了
			在展示模态窗口前，用ajax处理数据并展示，与数据库交互
			 */

			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			})

			$.ajax({
				url:"workbench/activity/getUserList.do",
				dataType:"json",
				type:"GET",
				success:function (data) {
					var  html = "<option></option>"
					$.each(data, function (i, n) {
						html+= "<option value = "+n.id+">"+n.name+"</option>"


					})
					var id = "${user.id}"
					$("#create-marketActivityOwner").html(html)
					$("#create-marketActivityOwner").val(id)


				}
					}

			)

			$("#createActivityModal").modal("show")

		})
		$("#saveBtn").click(function () {
			$.ajax({
						url:"workbench/activity/save.do",
						dataType:"json",
				        data: {
							owner:$.trim($("#create-marketActivityOwner").val()),
							name:$.trim($("#create-marketActivityName").val()),
							startDate:$.trim($("#create-startTime").val()),
							endDate:$.trim($("#create-endTime").val()),
							cost:$.trim($("#create-cost").val()),
							description:$.trim($("#create-describe").val())
						},
						type:"POST",
						success:function (data) {
							if (data.success) {
								pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
										,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

								$("#activityAddFrom")[0].reset();

								//添加成功后刷新市场活动列表
								$("#createActivityModal").modal("hide")

							}else {
								alert("Add activity fails")
							}



						}
					}

			)


		})


		pageList(1,2)



		$("#searchBtn").click(function (){

			$("#hidden-name").val($.trim($("#search-name").val()))
			$("#hidden-owner").val($.trim($("#search-owner").val()))
			$("#hidden-startDate").val($.trim($("#search-startTime").val()))
			$("#hidden-endDate").val($.trim($("#search-endTime").val()))



			pageList(1,2)

		})
		$("#qx").click(function (){
			$("input[name=xz]").prop("checked",this.checked)
		})

		$("#activityBody").on("click",$("input[name=xz]"),function(){
			$("#qx").prop("checked",$("input[name=xz]").length == $("input[name=xz]:checked").length)
		})

		$("#deleteBtn").click(function () {


			var xz = $("input[name=xz]:checked");

			if (xz.length == 0) {
				alert("Please choose one or more records to delete!!! ")
			}else {
				if (confirm("DELETE THESE RECORDS")) {
					var param = "";
					$.each(xz,function (i,n){
						param += "id="+$(n).val()+""
						if (i < xz.length-1) {
							param += "&"
						}
					})
					$.ajax({
								url:"workbench/activity/delete.do",
								dataType:"json",
								data: param,
								type:"post",
								success:function (data) {

									if (data.success) {
										pageList(1
												,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


									}else {
										alert("失败")
									}

								}

							}

					)


				}





			}


		})


		$("#editBtn").click(function (){
			var $xz = $("input[name=xz]:checked");

			if ($xz.length == 0) {
				alert("Please choose one reecord")

			}else if ($xz.length > 1) {
				alert('Only one record')

			}else {
				var id = $xz.val()
				$.ajax({
							url:"workbench/activity/getUserListAndAActivity.do",
							dataType:"json",
							data: {
								id:id
							},
							type:"get",
							success:function (data) {

							    var html = "<option></option>"
								$.each(data.uList, function (i,n) {
									html += "<option value='"+n.id+"'>"+n.name+"</option>"

								})
								$("#edit-Owner").html(html)

								$("#edit-Name").val(data.a.name)
								$("#hidden-id").val(data.a.id)
								$("#edit-Owner").val(data.a.owner)
								$("#edit-startTime").val(data.a.startDate)
								$("#edit-endTime").val(data.a.endDate)
								$("#edit-cost").val(data.a.cost)
								$("#edit-describe").val(data.a.description)
								$("#editActivityModal").modal("show")




							}


						}

				)



			}
		})

		$("#updateBtn").click(function () {

			$.ajax({
						url:"workbench/activity/update.do",
						dataType:"json",
						data: {
							id : $.trim($("#hidden-id").val()),
							owner:$.trim($("#edit-Owner").val()),
							name:$.trim($("#edit-Name").val()),
							startDate:$.trim($("#edit-startTimestartTime").val()),
							endDate:$.trim($("#edit-endTime").val()),
							cost:$.trim($("#edit-costcost").val()),
							description:$.trim($("#edit-describe").val())
						},
						type:"POST",
						success:function (data) {
							if (data.success) {

								pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
										,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


								//添加成功后刷新市场活动列表
								$("#editActivityModal").modal("hide")

							}else {
								alert("Update activity fails")
							}



						}
					}

			)




		})
		
		
	});

	function pageList(pageNo, pageSize){
		$("#qx").prop("checked",false)
		$("#search-name").val($.trim($("#hidden-name").val()))
		$("#search-owner").val($.trim($("#hidden-owner").val()))
		$("#search-startTime").val($.trim($("#hidden-startDate").val()))
		$("#search-endTime").val($.trim($("#hidden-endDate").val()))
		$.ajax({
					url:"workbench/activity/pageList.do",
					dataType:"json",
					data: {
						pageNo: pageNo,
						pageSize: pageSize,
						name:$.trim($("#search-name").val()),
						owner: $.trim($("#search-owner").val()),
						startDate: $.trim($("#search-startTime").val()),
						endDate: $.trim($("#search-endTime").val())

					},
					type:"GET",
					success:function (data) {
						var html =  ""
						$.each(data.dataList, function (i, n) {
							html += '<tr class="active">'
							html += '	<td><input type="checkbox" name = "xz" value="'+n.id+'" /></td>'
							html += '	<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>'
							html += '	<td>'+n.owner+'</td>'
							html += '	<td>'+n.startDate+'</td>'
							html += '	<td>'+n.endDate+'</td>'
							html += '</tr>'


						})
						$("#activityBody").html(html)
						var totalPages = data.total % pageSize == 0 ? data.total/pageSize:parseInt(data.total/pageSize) + 1


						$("#activityPage").bs_pagination({
							currentPage: pageNo, // 页码
							rowsPerPage: pageSize, // 每页显示的记录条数
							maxRowsPerPage: 20, // 每页最多显示的记录条数
							totalPages: totalPages, // 总页数
							totalRows: data.total, // 总记录条数

							visiblePageLinks: 3, // 显示几个卡片

							showGoToPage: true,
							showRowsPerPage: true,
							showRowsInfo: true,
							showRowsDefaultInfo: true,

							onChangePage : function(event, data){
								pageList(data.currentPage , data.rowsPerPage);
							}
						});






					}
				}
		)



	}
	
</script>

</head>
<body>
<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>
<input type = "hidden" id = "hidden-id">


	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id = "activityAddFrom" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
<%--					data-dismiss:表示关闭模态窗口--%>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-Owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-Owner">

								</select>
							</div>
                            <label for="edit-Name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-Name" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" >
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id = "updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id ="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id = "search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endTime">
				    </div>
				  </div>
				  
				  <button type="button" id = "searchBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
<%--					--%>
<%--					data-toggle表示触发该按钮， 将要打开一个模态窗口--%>
<%--					data-target表示要打开哪个模态窗口--%>
<%--					--%>
<%--					现在我们是通过以属性和属性值的方式来打开模态窗口，但是这样不好，没办法对按钮的功能进行扩充，
                        未来实际项目应该有我们写js代码自己来操作，不能这么写死。
                        data-toggle="modal" data-target="#editActivityModal"--%>
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id = "qx" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id = "activityBody">
<%--						<tr class="active">--%>
<%--							<td><input type="checkbox" /></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--							<td>2020-10-10</td>--%>
<%--							<td>2020-10-20</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>2020-10-10</td>--%>
<%--                            <td>2020-10-20</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id = "activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>