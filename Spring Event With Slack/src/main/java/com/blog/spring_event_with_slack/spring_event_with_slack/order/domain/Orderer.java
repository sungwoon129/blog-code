package com.blog.spring_event_with_slack.spring_event_with_slack.order.domain;

import com.blog.spring_event_with_slack.spring_event_with_slack.member.MemberId;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Embeddable
public class Orderer {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "orderer_id"))
    )
    private MemberId memberId;

    @Column(name = "orderer_name")
    private String name;

    protected Orderer() {}

    public Orderer(MemberId memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orderer orderer = (Orderer) o;
        return Objects.equals(memberId, orderer.memberId) &&
                Objects.equals(name, orderer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, name);
    }
}
