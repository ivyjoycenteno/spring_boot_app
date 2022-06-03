package com.rijio.blog.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table( uniqueConstraints = {@UniqueConstraint( columnNames = { "title" })})
public class Post {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	private long id;
	
	@Column( nullable = false)
	private String title;
	
	@Column( nullable = false)
	private String description;
	
	@Column( nullable = false)
	private String content;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();
}
