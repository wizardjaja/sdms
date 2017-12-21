package com.wizard.sdms.baseinfo.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wizard.sdms.baseinfo.mapper.IClassMapper;
import com.wizard.sdms.baseinfo.model.ClassModel;
import com.wizard.sdms.baseinfo.service.IClassService;
/**
 * 班级service实现类
 * @author wizard
 *
 */
@Service
public class ClassServiceImpl implements IClassService {
	private IClassMapper classMapper;
	@Autowired
	public void setClassMapper(IClassMapper classMapper) {
		this.classMapper = classMapper;
	}

	@Override
	public void add(ClassModel classModel) throws Exception {
		classMapper.insert(classModel);
	}

	
	@Override
	public void modify(ClassModel classModel) throws Exception {
		// TODO Auto-generated method stub
		classMapper.update(classModel);
	}

	@Override
	public void delete(ClassModel classModel) throws Exception {
		// TODO Auto-generated method stub
		classMapper.delete(classModel);
	}

	@Override
	public ClassModel get(Integer classId) throws Exception {
		return classMapper.select(classId);
	}

	@Override
	public List<ClassModel> getListByAll() throws Exception {
		return classMapper.selectListByAll();
	}

	@Override
	public List<ClassModel> getListByAllWithPage(int rows, int page) throws Exception {
		return classMapper.selectListByAllWithPage(new RowBounds(rows*(page-1), rows));
	}

	@Override
	public int getCountByAll() throws Exception {
		return classMapper.selectCountByAll();
	}

	@Override
	public int getPageCountByAll(int rows) throws Exception {
		int count = this.getCountByAll();
		if(count%rows ==0) {
			return count/rows;
		}else {
			return count/rows + 1;
		}
	}

	@Override
	public boolean checkCanDelete(String className) throws Exception {
		List<ClassModel> list = getListByAll();
		for (ClassModel classModel : list) {
			if(classModel.getClassName().equals(className)&&className!=null) {
				return false;
			}
		}
		return true;
	}


	@Override
	public void importFromExcel(InputStream excelFile) throws Exception {
		//打开上传的excel文件
		Workbook wb = WorkbookFactory.create(excelFile);
		//取得第1个sheet
		Sheet sheet=wb.getSheetAt(0);
		//取得第1行
		Row row0=sheet.getRow(0);
		
		for (Row row : sheet) {
			if(row.getRowNum()!=0) {
				Cell c0 = row.getCell(0);
				String className = c0.getStringCellValue();
				System.out.println("0");
				ClassModel classModel = new ClassModel();
				classModel.setClassName(className);
				this.add(classModel);
			}
		}
	}

	@Override
	public void exportToExcel(File source, File exportFile) throws Exception {
		//打开excel模板文件
		//Workbook wb = WorkbookFactory.create(source);
		OPCPackage pkg = OPCPackage.open(source);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);
		//取得第一个sheet
		Sheet sheet = wb.getSheetAt(0);
		//取得所有的班级列表
		List<ClassModel> list = classMapper.selectListByAll();
		for (ClassModel classModel : list) {
			System.out.println(classModel);
		}
		int i = 1;
		for(ClassModel classModel:list){
			Row row = sheet.createRow(i);
			Cell c0 = row.createCell(0);
			c0.setCellValue(classModel.getClassName());
			i++;
		}
		FileOutputStream fos = new FileOutputStream(exportFile);
		wb.write(fos);
		fos.close();
		wb.close();

	}



}
