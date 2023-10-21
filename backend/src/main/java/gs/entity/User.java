package gs.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "user",
    uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = "email")
    }
)

public class User {

    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    @Column(
        name = "user_id",
        updatable = false
    )
    private long userId;

    @Column(
        name = "email",
        nullable = false,
        unique = true   
    )
    private String email;

    @Column(
        name = "username",
        nullable = false
    )
    private String username;

    @Column(
        name = "password",
        nullable = false
    )

    // USER MANAGEMENT - START 
    // Join user and role table to relate user to a particular role 
    // Many users may have many different role
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // Create a table called user_role which hold all the user and it's associated role details
    @JoinTable(name = "user_role", 
        joinColumns = {
            @JoinColumn(name = "user_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "role_id")
        }
    )
    // Store the role of the user. Using SET as one user might have many roles.
    private Set<Role> role;
    // USER MANAGEMENT - END

    // CAN ENCRYPT THE PASSWORD IF NEEDBE
    private String password;

    public User(){
    };

    public User(long userId, String email, String username, String password) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }
    
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // USER MANAGEMENT GETTERS & SETTERS - START 
    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
    // USER MANAGEMENT GETTERS & SETTERS - END
}