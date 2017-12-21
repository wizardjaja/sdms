/**
 * 班级管理主JS程序
 */
/**
 * 班级管理主控制JS
 */
$(function(){
	var classId=null;
	
	//显示班级表格
	$("#classGrid").jqGrid({
		url: 'class/list/page.mvc',
		datatype: "json",
		mtype:"GET",		
		colModel: [
			{ label: '班级', name: 'className', width: 150 }
		],
		viewrecords: true, // show the current page, data rang and total records on the toolbar
		autowidth:true,
		height: 370,
		rowNum: 10,
		rowList:[5,10,15],
		jsonReader:{
			root:"list",
			page:"page",
			total:"pageCount",
			records:"count",
			id:"classId"
		},
		pager: "#classGridPager",
		multiselect:false,
		onSelectRow:function(id){
			classId=id;
		},
		loadComplete:function(data){
			//js只要不是false就都是true
			if(data.message){
				BootstrapDialog.alert({title:"提示",message:data.message});
			}
			
		},
		loadError:function(xhr,status,error){
			BootstrapDialog.alert({title:"提示",message:error});
			
		}
	});
	//点击增加处理
	$("a#classAddLink").on("click",function(){
		$("#modelbody").load("class/add.html",function(){
			$("#ModalLabel").html("增加班级");
			//取得系统功能列表
			/*$.getJSON("function/list/all.mvc",function(funtionList){
				if(funtionList!=null){
					if(funtionList.message){
						BootstrapDialog.alert({title:"提示",message:data.message});
					}
					else{
						for(var i=0;i<funtionList.length;i++){
							$("div#userfunctions").append("<label class='checkbox-inline'><input type='checkbox' name='functionNos' value='"+funtionList[i].no+"'>"+funtionList[i].name+"</label>");
						}
					}
				}
			});*/
			//验证
			$("form#classAddForm").validate({
				rules:{
					className:{
						required:true,
						remote:"class/checkNameExist.mvc"
					}
				},
				messages:{
					className:{
						required:"班级不能为空",
						remote:"此班级已经存在"
					}
				}
				
			});
			//拦截用户增加
			$("form#classAddForm").ajaxForm(function(data){
				if(data.result=="Y"){
					$("#classGrid").trigger("reloadGrid");
				}
				BootstrapDialog.alert({title:"提示",message:data.message});
				$('#classModal').modal("hide");
			});
			//点击取消按钮处理
			$("input[type='button'][value='取消']").on("click",function(){
				$('#classModal').modal("hide");
			});
		});
		$('#classModal').modal("show");
		
	});
	//点击修改处理
	$("a#classModifyLink").on("click",function(){
		if(classId==null){
			BootstrapDialog.alert({title:"提示",message:"请选择要修改的班级"});
		}
		else{
			$("#modelbody").load("class/modify.html",function(){
				$("#ModalLabel").html("修改班级");
				
				/*//取得系统功能列表
				$.getJSON("function/list/all.mvc",function(funtionList){
					if(funtionList!=null){
						for(var i=0;i<funtionList.length;i++){
							$("div#userfunctions").append("<label class='checkbox-inline'><input type='checkbox' name='functionNos' value='"+funtionList[i].no+"'>"+funtionList[i].funName+"</label>");
						}
					}*/
					//取得指定的用户
					$.getJSON("class/get.mvc",{classId:classId},function(data1){
						$("input[name='classId']").val(data1.classId);
						$("input[name='className']").val(data1.className);
					});
					
				
				
				//验证
				$("form#classModifyForm").validate({
					rules:{
						className:{
							required:true,
							remote:"class/checkNameExist.mvc"
						}
					},
				messages:{
					className:{
						required:"班级不能为空",
						remote:"此班级已经存在"
					}
				}
				});
				//拦截用户修改表单提交
				$("form#classModifyForm").ajaxForm(function(data){
					if(data.result=="Y"){
						$("#classGrid").trigger("reloadGrid");
					}
					BootstrapDialog.alert({title:"提示",message:data.message});
					$('#classModal').modal("hide");
				});
				$("input[type='button'][value='取消']").on("click",function(){
					$('#classModal').modal("hide");
				});
			});
			$("div.modal-dialog").css("width","1400px");
			$('#classModal').modal("show");
		}
	});
	
	//点击删除处理
	$("a#classRemoveLink").on("click",function(){
		if(classId==null){
			BootstrapDialog.alert({title:"提示",message:"请选择要删除的班级"});
		}
		else{
			$.getJSON("class/checkcandelete.mvc",{classId:classId},function(data){
				if(data.result=="Y"){
					BootstrapDialog.confirm({
						title:"删除确认",
						message:"您确认要删除此班级么?",
						callback:function(result){
							if(result){
								$.post("class/delete.mvc",{classId:classId},function(data){
									if(data.result=="Y"){
										classId=null;
										 $("#classGrid").trigger("reloadGrid");
									}
									BootstrapDialog.alert({title:"提示",message:data.message});
									
								});
							}
						}
					});
					
				}
				else{
					BootstrapDialog.alert({title:"警告",message:data.message});
				}
			});
		}
	});
	
	//点击查看处理
	$("a#classViewLink").on("click",function(){
		if(classId==null){
			BootstrapDialog.alert({title:"提示",message:"请选择要查看的班级"});
		}
		else{
			$("#ModalLabel").html("查看班级");
			$("#modelbody").load("class/view.html",function(){
				
				$.getJSON("class/get.mvc",{classId:classId},function(data){
					if(data!=null){
						$("input[name='className']").val(data.className);
					}
					if(data.photoFileName!=null){
						if(data.photoContentType.indexOf("image")==0){
							$("div#classPhoto").html("<img src='class/downphoto.mvc?classId="+data.classId+"'  width='1000'/>");	
						}
						else{
							$("div#classPhoto").html("<a href='class/downphoto.mvc?classId="+data.classId+"'>下载</a>");
						}
					}
					else{
						$("div#classPhoto").html("无附件");
					}
				});
				
				$("input[type='button'][value='返回']").on("click",function(){
					$('#classModal').modal("hide");
				});
				
			});
			$("div.modal-dialog").css("width","1400px");
			$('#classModal').modal("show");
		}
	});
	
	//点击导入处理
	$("a#classImportLink").on("click",function(){
		$("#ModalLabel").html("导入班级");
		$("#modelbody").load("class/import.html",function(){
			$("input[type='button'][value='取消']").on("click",function(){
				 $('#classModal').modal("hide");
			});
			
			$("form#classImportForm").ajaxForm(function(data){
				if(data.result=="Y"){
					 $("#classGrid").trigger("reloadGrid");
				}
				BootstrapDialog.alert({title:"提示",message:data.message});
				$('#classModal').modal("hide");
			});
		});
		$("div.modal-dialog").css("width","600px");
		$('#classModal').modal("show");
	});
	
	//点击导出处理
	$("a#classExportLink").on("click",function(){
		$("#ModalLabel").html("导出班级");
		$("#modelbody").load("class/export.html",function(){
			$("input[type='button'][value='关闭']").on("click",function(){
				 $('#classModal').modal("hide");
			});
		});
		$("div.modal-dialog").css("width","600px");
		$('#classModal').modal("show");
	});
});


