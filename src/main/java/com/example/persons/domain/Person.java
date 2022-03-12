package com.example.persons.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
