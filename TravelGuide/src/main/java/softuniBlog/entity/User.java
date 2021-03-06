package softuniBlog.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    private Integer id;

    private String email;

    private String fullName;

    private String password;

    private Set<Role> roles;

    private Set<Article> articles;

    private Set<Destination> destinations;

    private Set<Category> categories;

    private Mark mark;

    public User(String email, String fullName, String password) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;

        this.destinations = new HashSet<>();
        this.roles = new HashSet<>();
        this.articles = new HashSet<>();
        this.categories = new HashSet<>();
    }

    public User() {
    }

    @OneToOne(mappedBy = "author")
    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    public Set<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<Destination> destinations) {
        this.destinations = destinations;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "fullName", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "password", length = 60, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void deleteRole(Role role) {
        this.roles.remove(role);
    }

    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @Transient
    public boolean isAdmin() {
        return this.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

    @Transient
    public boolean isAuthor(Article article) {
        return this.getId().equals(article.getAuthor().getId());
    }

    @Transient
    public boolean isAuthor(Destination destination) {
        return this.getId().equals(destination.getAuthor().getId());
    }
}