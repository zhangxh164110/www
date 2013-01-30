package cn.www.jdo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 用户表
 */
@Entity
@Table(name = "mng_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true, nullable = false, length = 50)
	private String userName; // 用户名

	@Column(nullable = false, length = 32)
	private String userPass; // 密码

	@Column(length = 50)
	private String headPath; // 头像路径

	@Column(length = 50)
	private String email; // 邮箱

	@Column
	private Integer successCount = 0; // 成功次数

	@Column
	private Integer failCount = 0; // 失败次数


	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createTime = new Date(); // 创建日期

	@Column(length = 32)
	private String findCode; // 验证码（找回密码）

	@Column
	private Integer status = 1; // 用户状态：1为正常，2为禁用
	
	@Column
	private Integer role = 2;//1为后台管理者；2为后台编辑者，3前台注册用户

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	@Column
	private String uuid; // 给激活用

	public String getFindCode() {
		return findCode;
	}

	public void setFindCode(String findCode) {
		this.findCode = findCode;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}