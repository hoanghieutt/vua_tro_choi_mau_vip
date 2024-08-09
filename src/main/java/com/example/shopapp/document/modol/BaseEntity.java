package com.example.shopapp.document.modol;

import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.ObjectId;

@Data

public class BaseEntity {
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
