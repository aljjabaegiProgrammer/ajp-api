package com.aljjabaegi.api.entity;

import com.aljjabaegi.api.common.jpa.base.BaseEntity;
import com.aljjabaegi.api.entity.enumerated.UseYn;
import com.aljjabaegi.api.entity.key.CodeKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

/**
 * @author GEONLEE
 * @since 2024-05-29<br />
 * 2024-06-10 @SQLRestriction 추가
 */
@Getter
@Setter
@Entity(name = "code")
@SQLRestriction("use_yn = 'Y'")
public class Code extends BaseEntity {

    @EmbeddedId
    CodeKey key;

    @ManyToOne
    @JoinColumn(name = "code_group_id")
    @MapsId("codeGroupId")
    CodeGroup codeGroup = new CodeGroup();

    @Column(name = "code_nm")
    String codeName;

    @Column(name = "code_desc")
    String codeDescription;

    @Column(name = "code_order")
    Integer codeOrder;

    @Column(name = "use_yn")
    @Enumerated(EnumType.STRING)
    UseYn useYn;
}
