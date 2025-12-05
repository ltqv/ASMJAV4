package entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Favorite", uniqueConstraints = { @UniqueConstraint(columnNames = { "UserId", "VideoId" }) })
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "LikeDate")
	private Date likeDate;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "VideoId")
	private Video video;

	public Favorite() {
		// TODO Auto-generated constructor stub
	}

	public Favorite(Long id, Date likeDate, User user, Video video) {
		super();
		this.id = id;
		this.likeDate = likeDate;
		this.user = user;
		this.video = video;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
}
