package com.wizard.sdms.baseinfo.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.wizard.sdms.baseinfo.model.ClassModel;
/**
 * 班级service类
 * @author wizard
 *
 */
public interface IClassService {
		/**
		 * 增加班级
		 * @param classModel 班级对象
		 * @throws Exception
		 */
		public void add(ClassModel classModel) throws Exception;
		/**
		 * 修改班级
		 * @param classModel 班级对象
		 * @throws Exception
		 */
		public void modify(ClassModel classModel) throws Exception;
		/**
		 * 删除班级
		 * @param classModel 班级对象
		 * @throws Exception
		 */
		public void delete(ClassModel classModel) throws Exception;
		/**
		 * 取得指定班级的列表
		 * @param classId 班级编号
		 * @return 班级对象
		 * @throws Exception
		 */
		public ClassModel get(Integer classId) throws Exception;
		/**
		 * 取得所有班级列表
		 * @return 班级对象集合
		 * @throws Exception
		 */
		public List<ClassModel> getListByAll() throws Exception;
		/**
		 * 取得所有班级列表，分页方式
		 * @param rows 每页记录数
		 * @param page 当前页
		 * @return 班级对象集合
		 * @throws Exception
		 */
		public List<ClassModel> getListByAllWithPage(int rows,int page) throws Exception;
		/**
		 * 取得班级个数
		 * @return 班级个数
		 * @throws Exception
		 */
		public int getCountByAll() throws Exception;
		/**
		 * 取得班级页数
		 * @param rows 每页记录数
		 * @return 班级页数
		 * @throws Exception
		 */
		public int getPageCountByAll(int rows) throws Exception;
		/**
		 * 检查指定班级是否可以被删除
		 * @param classId 班级编号
		 * @return 班级是否可以被删除
		 * @throws Exception
		 */
		public boolean checkCanDelete(String className) throws Exception;
		/**
		 * 从Excel文件导入班级
		 * @param excelFile excel文件输入流
		 * @throws Exception 
		 */
		public void importFromExcel(InputStream excelFile) throws Exception;
		/**
		 * 导出Excel文件
		 * @param source 模板文件
		 * @param exportFile 目标文件
		 * @throws Exception
		 */
		public void exportToExcel(File source, File exportFile) throws Exception;
}
