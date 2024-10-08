package com.spring.SpeedAuction.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "User")

public class UserModels {

    @Id
    private String id;
    @NotBlank
    @Size(max = 50)
    private String username;
    @NotBlank
    @Size(max = 50)
    private String first_name;
    @NotBlank
    @Size(max = 100)
    private String last_name;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    @Indexed(unique = true)
    @Size(max = 100)
    private String email;
    private String phone_number;
    @NotBlank
    @Size(max = 100)
    private String address;
    @NotBlank
    @Size(max = 50)
    private String country;
    @NotBlank
    @Size(max = 100)
    private String city;
    @NotBlank
    @Size(max = 20)
    private String postal_code;
    private String profile_picture;
    @DBRef
    private List<AuctionModels> favourites_auction_id = new ArrayList<>();
    //roll
    @DBRef
    private Set<Role> roles = new HashSet<>();
    // ["ROLE_USER", "ROLE MODERATOR"]


    public UserModels() {
    }

    public UserModels(String username, String first_name, String last_name, String password,
                      String email, String phone_number, String address, String country, String city, String postal_code, String profile_picture) {

        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.country = country;
        this.city = city;
        this.postal_code = postal_code;
        this.profile_picture = profile_picture;
    }

    public UserModels(List<AuctionModels> favourites_auction_id) {
        this.favourites_auction_id = favourites_auction_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;

    }

    public List<AuctionModels> getFavourites_auction_id() {
        return favourites_auction_id;
    }

    public void setFavourites_auction_id(List<AuctionModels> favourites_auction_id) {
        this.favourites_auction_id = favourites_auction_id;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
