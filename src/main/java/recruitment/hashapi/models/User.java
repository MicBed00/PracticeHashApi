package recruitment.hashapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  @SequenceGenerator(name = "user_id_generator", sequenceName = "users_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "user_id_generator", strategy = GenerationType.SEQUENCE)
  @Column(nullable = false)
  public Long id;

  @Column(nullable = false)
  public String lastName;

  @Column(nullable = false)
  public String firstName;

  @Column(nullable = true)
  public String secondName;

  public User() {
  }

  public User(String lastName, String firstName) {
    this.lastName = lastName;
    this.firstName = firstName;
  }

  public User(String lastName, String firstName, String secondName) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.secondName = secondName;
  }

  @Override
  public String toString() {
    return "User{" +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", secondName='" + secondName + '\'' +
            '}';
  }
}
