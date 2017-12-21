package com.wizard.sdms.baseinfo.mapper;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.wizard.sdms.baseinfo.model.ClassModel;
/**
 * 班级mapper类
 * @author wizard
 *
 */
public interface IClassMapper {
	/**
	 * 增加班级
	 * @param classModel 班级对象
	 * @throws Exception
	 */
	public void insert(ClassModel classModel) throws Exception;
	/**
	 * 修改班级
	 * @param classModel 班级对象
	 * @throws Exception
	 */
	public void update(ClassModel classModel) throws Exception;
	/**
	 * 删除班级
	 * @param classModel 班级对象
	 * @throws Exception
	 */
	public void delete(ClassModel classModel) throws Exception;
	/**
	 * 按班级编号查询班级数据对象
	 * @param classModel 班级编号
	 * @throws Exception
	 */
	public ClassModel select(Integer classId) throws Exception;
	/**
	 * 返回所有班级数据对象集合
	 * @throws Exception
	 */
	public List<ClassModel> selectListByAll() throws Exception;
	/**
	 * 分页返回所有班级数据对象集合
	 * @param classModel 分页条件
	 * @throws Exception
	 */
	public List<ClassModel> selectListByAllWithPage(RowBounds rb) throws Exception;
	/**
	 * 返回所有班级数据对象个数
	 * @throws Exception
	 */
	public int selectCountByAll() throws Exception;
}
