package com.aljjabaegi.api.entity;

import com.aljjabaegi.api.common.jpa.annotation.SearchableField;
import com.aljjabaegi.api.common.jpa.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-04-08<br />
 * 2024-04-11 GEONLEE - DynamicSpecification 사용을 위한 @SearchableField 추가<br />
 */
@Getter
@Setter
@Entity(name = "team")
@SequenceGenerator(
        name = "TEAM_SEQ_GENERATOR"
        , sequenceName = "team_seq"
        , initialValue = 1
        , allocationSize = 1
)
public class Team extends BaseEntity {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_SEQ_GENERATOR")
    private Long teamId;

    @Column(name = "team_nm")
    @SearchableField
    private String teamName;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<MemberTeam> members;
}
