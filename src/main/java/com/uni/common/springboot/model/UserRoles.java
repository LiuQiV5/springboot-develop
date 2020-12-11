package com.uni.common.springboot.model;

import com.uni.common.springboot.entity.DataTypeLength;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "roleid", length = DataTypeLength.ID, nullable = false)
    private Long roleid;

    @Column(name = "userid", length = DataTypeLength.ID, nullable = false)
    private Long userid;

    /**
     * 学校guid
     */
    @Column(name = "schoolguid", length = DataTypeLength.ID, nullable = false)
    private String schoolGuid;
}
