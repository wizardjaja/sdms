package com.wizard.sdms.baseinfo.model;

import org.apache.ibatis.type.Alias;

/**
  * 班级类
  * @author wizard
  *
  */
@Alias("Class")
public class ClassModel {
	/**
	 * 班级Id
	 */
	private Integer classId;
	/**
	 * 班级号
	 */
	private String className;
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
