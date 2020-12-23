//package com.uni.common.springboot.entity;
//import com.uni.common.springboot.core.Context;
//import com.vladmihalcea.hibernate.type.json.JsonStringType;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.Filter;
//import org.hibernate.annotations.FilterDef;
//import org.hibernate.annotations.ParamDef;
//import org.hibernate.annotations.TypeDef;
//
//import javax.persistence.*;
//
//@Setter
//@Getter
//@MappedSuperclass
//@TypeDef(name = "json", typeClass = JsonStringType.class)
//@FilterDef(name = OrganizationEntity.ORGANIZATION_FILTER, parameters =
//        {@ParamDef(name = OrganizationEntity.ORGANIZATION_UID, type = "string")})
//@Filter(name = OrganizationEntity.ORGANIZATION_FILTER,
//        condition = OrganizationEntity.ORGANIZATION_UID_COLUMN + " = :" + OrganizationEntity.ORGANIZATION_UID)
//public abstract class OrganizationEntity extends UserTimeEntity implements OrganizationAware {
//    public static final String ORGANIZATION_FILTER = "organizationFilter";
//    public static final String ORGANIZATION_UID = "organizationUid";
//    public static final String ORGANIZATION_UID_COLUMN = "organization_uid";
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = ORGANIZATION_UID_COLUMN, length = DataTypeLength.ID, nullable = false)
//    private String organizationUid;
//
//    @PrePersist
//    private void assignOrganization() {
//        if (organizationUid == null)
//            organizationUid = Context.getContext().getOrganizationUid();
//    }
//}
