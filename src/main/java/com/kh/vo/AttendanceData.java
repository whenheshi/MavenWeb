package com.kh.vo;

/**
 * ÿ�տ������ݷ�װ��
 * @author Administrator
 *
 */
public class AttendanceData {
	//����
	private String code;
	//����
	private String name;
	//����
	private String date;
	//ǩ������
	private String checkType;
	//�ϰ�ʱ��
	private String onjob;
	//�°�ʱ��
	private String leavejob;
	//���������Ϣ
	private EmpExptData expData;
	
	//��ȡ��Ч�򿨴���
	public int getCheckCount(){
		//ǩ�����ͷǿգ���Ϊ�쳣ǩ��
		if(null!=getCheckType() && !"".equals(getCheckType())){
			return 0;
		}
		int count = 0;
		if(isCome())
			count ++;
		if(isGo())
			count ++;
		return count;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	//�Ƿ��ϰ��
	public boolean isCome() {
		if(null==getExpData()){
			//û�����
			if(null!=getOnjob() && getOnjob().compareTo("12:00:00")<=0){
				return true;
			}
		}else{
			if(null == getOnjob()){
				return false;
			}else if(getExpData().isAllDay()){
				return false;
			}else{
				//�����У������ο�����
				if(getOnjob().compareTo(getLeavejob())<0){
					return true;
				}
			}
		}
		return false;
	}
	//�Ƿ��°��
	public boolean isGo() {
		if(null==getExpData()){
			//û�����
			if(null!=getLeavejob() && getLeavejob().compareTo("12:00:00")>0){
				return true;
			}
		}else{
			if(null == getLeavejob()){
				return false;
			}else if(getExpData().isAllDay()){
				return false;
			}else{
				//�����У������ο�����
				if(getOnjob().compareTo(getLeavejob())<0){
					return true;
				}
			}
		}
		return false;
	}
	//���ص��쿼���쳣����
	public String getCheckType() {
		if (null != checkType && !"".equals(checkType)) {
			return checkType;
		} else if (null != getExpData() && getExpData().isAllDay()) {
			return getExpData().getType();
		}
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getOnjob() {
		return onjob;
	}

	public void setOnjob(String onjob) {
		this.onjob = onjob;
	}

	public String getLeavejob() {
		return leavejob;
	}

	public void setLeavejob(String leavejob) {
		this.leavejob = leavejob;
	}

	public EmpExptData getExpData() {
		return expData;
	}

	public void setExpData(EmpExptData expData) {
		this.expData = expData;
	}
}
