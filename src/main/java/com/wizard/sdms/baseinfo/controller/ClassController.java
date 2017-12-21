package com.wizard.sdms.baseinfo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.wizard.sdms.result.ResultInfo;
import com.wizard.sdms.baseinfo.model.ClassModel;
import com.wizard.sdms.baseinfo.service.IClassService;
import com.wizard.sdms.result.ResultMessage;

/**
 * 班级控制器类
 * @author wizard
 *
 */
@RestController
@RequestMapping(value="/class")
public class ClassController {
   private IClassService ClassService=null;
   @Autowired
   public void setClassService(IClassService ClassService) {
		this.ClassService = ClassService;
	}

   /**
    * 增加班级
    * @param classModel 班级类
    * @return ResultMessage 消息响应类
    * @throws Exception
    */
   @RequestMapping(value="/add",method=RequestMethod.POST)
   public ResultMessage add(ClassModel classModel) throws Exception
   {
	   ResultMessage result=new ResultMessage();
	   result.setResult("N");
	   result.setMessage("增加班级失败");
	   ClassService.add(classModel);
	   result.setResult("Y");
	   result.setMessage("增加班级成功");
	   return result;
	   
	  
   }
   

/**
 * 修改班级
 * @param classModel 班级类
 * @return	消息响应类
 * @throws Exception
 */
@RequestMapping(value="/modify",method=RequestMethod.POST)
   public ResultMessage modify(ClassModel classModel) throws Exception
   {
	   ResultMessage result=new ResultMessage();
	   result.setResult("N");
	   result.setMessage("修改班级失败");
	   ClassService.modify(classModel);
	   result.setResult("Y");
	   result.setMessage("修改班级成功");
	   return result;
	   
	  
   }
   /**
    * 删除班级
    * @param classModel 班级类
    * @return 消息响应类
    * @throws Exception
    */
   @RequestMapping(value="/delete",method=RequestMethod.POST)
   public ResultMessage delete(ClassModel classModel) throws Exception
   {
	   ResultMessage result=new ResultMessage();
	   result.setResult("N");
	   result.setMessage("删除班级失败");
	   ClassService.delete(classModel);
	   result.setResult("Y");
	   result.setMessage("删除班级成功");
	   return result;
   }
   /**
    * 按班级编号得到班级对象
    * @param typeNo 班级编号
    * @return 班级对象
    * @throws Exception
    */
   @RequestMapping(value="/get",method=RequestMethod.GET)
   public ClassModel get(@RequestParam Integer classId) throws Exception
   {
	   return ClassService.get(classId);
   }
   /**
    * 取得所有班级列表，不分页
    * @return 取得所有班级列表，不分页
    * @throws Exception
    */
   @RequestMapping(value="/list/all",method=RequestMethod.GET)
   public List<ClassModel> getListByAll() throws Exception
   {
	   return ClassService.getListByAll();
   }
   /**
    * 取得所有班级列表，分页方式
    * @param rows 每页记录数
    * @param page 当前页
    * @return 取得所有班级列表，分页方式
    * @throws Exception
    */
   @RequestMapping(value="/list/page",method=RequestMethod.GET)
   public ResultInfo<ClassModel> getListByAllWithPage(@RequestParam(required=false,defaultValue="10") int rows,@RequestParam(required=false,defaultValue="1") int page) throws Exception
   {
	   ResultInfo<ClassModel> result=new ResultInfo<ClassModel>();
	   result.setCount(ClassService.getCountByAll());
	   result.setPageCount(ClassService.getPageCountByAll(rows));
	   result.setRows(rows);
	   result.setPage(page);
	   result.setList(ClassService.getListByAllWithPage(rows, page));
	   
	   return result;
   }
   	/**
   	 * 检查此班级能否被删除
   	 * @param typeNo 班级编号
   	 * @return 检查此班级能否被删除的信息
   	 * @throws Exception
   	 */
	@RequestMapping(value="/checkcandelete",method=RequestMethod.GET,produces="application/json")
	public ResultMessage checkCanDelete(@RequestParam String className) throws Exception
	{
		ResultMessage result=new ResultMessage();
		if(ClassService.checkCanDelete(className)){
			result.setResult("Y");
			result.setMessage("此班级可以删除");
		}
		else{
			result.setResult("N");
			result.setMessage("此班级不能删除");
		}
		return result;
	}
	/**
	 * 导入班级的excel文件
	 * @param importfile excel文件
	 * @return 检查班级导入是否成功的信息
	 * @throws Exception
	 */
	@RequestMapping(value="/import",method=RequestMethod.POST)
	public ResultMessage importFromExcel(@RequestPart MultipartFile importfile) throws Exception
	{
		 ResultMessage result=new ResultMessage();
		 if(importfile!=null&&(!importfile.isEmpty())){
			 ClassService.importFromExcel(importfile.getInputStream());
			 result.setResult("Y");
			 result.setMessage("导入班级成功");
		 }
		 else{
			 result.setResult("N");
			 result.setMessage("没有上传导入Excel文件");
		 }
		 return result;
		
	}
	
	/**
	 * 
	 * @param session 会话
	 * @return 响应体
	 * @throws Exception
	 */
	@RequestMapping(value="/exporttoexcel", method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportToExcel(HttpSession session)throws Exception{
		ServletContext application = session.getServletContext();
		String sourcepath = application.getRealPath("/excelexport/Classexport.xlsx");
		String exportfilepath = application.getRealPath("/download/exportexcel"+(int)(Math.random()*1000)+".xlsx");
		ClassService.exportToExcel(new File(sourcepath), new File(exportfilepath));
		
		String mainType="application";
		String subType="vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String fileName=new String("班级导出.xlsx".getBytes("UTF-8"),"iso-8859-1");
		FileInputStream fis = new FileInputStream(exportfilepath);
		byte[] data = new byte[fis.available()];
		fis.read(data, 0, data.length);
		HttpHeaders headers=new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(new MediaType(mainType,subType));
		File excelFile = new File(exportfilepath);
		excelFile.delete();
		return new ResponseEntity<byte[]>(data,headers,HttpStatus.CREATED);
	}
   
}
