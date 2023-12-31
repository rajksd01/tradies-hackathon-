package com.tradiesKraken.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class User implements UserDetails {

    @Id
    private String userId;

    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Please provide email")
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email !!")
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password is required !!")
    private String password;

    @Column(length = 10)
    private String phone;

    private String imageUrl;

    @Column(name = "createdAt")
    private Date createdAt;

    private String role;

    private String aadharNo;

    private boolean active;

    private double rating;

    private int noOfRatings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "accepterUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ServiceReq> acceptedServices = new ArrayList<>();

    @OneToMany(mappedBy = "reqUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ServiceReq> requestedServices = new ArrayList<>();

    @ManyToOne
    private Location location;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", phone=" + phone + ", imageUrl="
                + imageUrl + ", createdAt=" + createdAt + ", role=" + role + ", aadharNo=" + aadharNo + ", active="
                + active + "]";
    }

}
