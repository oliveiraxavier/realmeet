package br.com.sw2you.realmeet.domain.entity;

import static br.com.sw2you.realmeet.util.DateUtils.now;
import static java.util.Objects.isNull;

import br.com.sw2you.realmeet.domain.model.Employee;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "allocation")
public class Allocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    private Employee employee;

    @Column(name = "subject")
    private String subject;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "end_at")
    private OffsetDateTime endAt;

    @Column(name = "start_at")
    private OffsetDateTime startAt;

    private Allocation(Builder builder) {
        id = builder.id;
        room = builder.room;
        employee = builder.employee;
        subject = builder.subject;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
        endAt = builder.endAt;
        startAt = builder.startAt;
    }

    public Allocation() {}

    @PrePersist
    public void prePersist() {
        if (isNull(createdAt)) {
            createdAt = now(); //DateUtils.now()
        }
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = now(); //DateUtils.now()
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getSubject() {
        return subject;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OffsetDateTime getEndAt() {
        return endAt;
    }

    public OffsetDateTime getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allocation that = (Allocation) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(room, that.room) &&
            Objects.equals(employee, that.employee) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(endAt, that.endAt) &&
            Objects.equals(startAt, that.startAt)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, employee, subject, createdAt, updatedAt, endAt, startAt);
    }

    @Override
    public String toString() {
        return (
            "Allocation{" +
            "id=" +
            id +
            ", room=" +
            room +
            ", employee=" +
            employee +
            ", subject='" +
            subject +
            ", createdAt=" +
            createdAt +
            ", updatedAt=" +
            updatedAt +
            ", endAt=" +
            endAt +
            ", startAt=" +
            startAt +
            '}'
        );
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Room room;
        private Employee employee;
        private String subject;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
        private OffsetDateTime endAt;
        private OffsetDateTime startAt;

        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Builder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder endAt(OffsetDateTime endAt) {
            this.endAt = endAt;
            return this;
        }

        public Builder startAt(OffsetDateTime startAt) {
            this.startAt = startAt;
            return this;
        }

        public Allocation build() {
            return new Allocation(this);
        }
    }
}
