package com.htv.user.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InfoUser extends BaseDomain {
    private String fullName;
    private String avatar;
    private String title;
    private String dob;
}
