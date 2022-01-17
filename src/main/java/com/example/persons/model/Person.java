package com.example.persons.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
  @Id @GeneratedValue @Column private Long id;

  @Column private String name;
  @Column private String lastName;
  @Column private String address;

  @Enumerated(EnumType.STRING)
  private Color color;
}
