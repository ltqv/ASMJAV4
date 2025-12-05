package entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Users", uniqueConstraints = { @UniqueConstraint(columnNames = { "Email" }) })
public class User {
	@Id
	@Column(name = "Id")
	private String id;
	@Column(name = "Password")
	private String password;
	@Column(name = "Email")
	private String email;
	@Column(name = "Fullname", columnDefinition = ("nvarchar(255)"))
	private String fullname;
	@Column(name = "Admin")
	private Boolean admin;

	@OneToMany(mappedBy = "user")
	private List<Favorite> favorites;
	@OneToMany(mappedBy = "user")
	private List<Share> shares;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String id, String password, String email, String fullname, Boolean admin, List<Favorite> favorites,
			List<Share> shares) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.fullname = fullname;
		this.admin = admin;
		this.favorites = favorites;
		this.shares = shares;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public List<Share> getShares() {
		return shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}
}
