package cn.www.jdo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
// 三级栏目文章表
@Table(name = "a_articleCategory")
public class ArticleCategory implements Serializable {
	private static final long serialVersionUID = 5122851263353269498L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "articleId", nullable = false)
	private Article article;// 文章ID

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId", nullable = false)
	private Category category;// 所属栏目，包括主栏目/副栏目

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}


	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
