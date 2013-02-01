package cn.www.jdo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
// 文章表
@Table(name = "a_article")
public class Article implements Serializable {
	private static final long serialVersionUID = -5121851263353269498L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;// 发布者

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	private Category category;// 所属菜单


	@Column(length = 60, nullable = false)
	private String title;// 标题

	@Temporal(TemporalType.TIMESTAMP)
	private Date publicDate;// 发表时间

	@Column(length = 30)
	private String origin;// 出处

	@Column(length = 30)
	private String author;// 作者


	@Column(length = 200)
	private String review;// 导读
	
	@Lob
	private String content;

	@Column(length = 100)
	private String keyword;// 关键字，多个用英文分号,隔开

	@Column
	private Integer status = 0;// 状态：0正常；1下架

	@Column
	private Integer readNum = 0;// 阅读计数（访问量）

	@Column(length = 100)
	private String metaKeyword;// meta元素
	
	@Column(length = 100)
	private String logo;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
