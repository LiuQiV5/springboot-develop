//package com.uni.common.springboot.model;
//
//import com.uni.common.springboot.entity.DataTypeLength;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "user_school",uniqueConstraints =
//		{@UniqueConstraint(columnNames = {"school_guid", "userid"})})
//public class UserSchool {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	private Integer id;
//
//	/**
//	 * 学校guid
//	 */
//	@Column(name = "school_guid", length = DataTypeLength.ID, nullable = false)
//	private String schoolGuid;
//
//	/**
//	 * 用户id
//	 */
//	@Column(name = "userid", length = DataTypeLength.ID, nullable = false)
//	private Long userid;
//
//}
