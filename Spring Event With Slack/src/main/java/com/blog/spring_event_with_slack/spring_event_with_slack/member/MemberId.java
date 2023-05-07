package com.blog.spring_event_with_slack.spring_event_with_slack.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class MemberId implements Serializable {

    @Column(name = "member_id")
    private String id;


    protected MemberId() {}

    public MemberId(String id) {
        this.id = id;
    }
    public boolean equals(String id) {
        return super.equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return Objects.equals(id, memberId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static MemberId of(String id) {
        return new MemberId(id);
    }
}
