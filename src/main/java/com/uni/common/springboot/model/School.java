package com.uni.common.springboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "school",uniqueConstraints =
		{@UniqueConstraint(columnNames = {"name"})})
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 学校名称
	 */
	@Column(name = "name")
	private String name;

	@Column(name = "guid")
	private String guid;

}
