package com.client.api.entitiy;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(
        schema = "public",
        name = "client")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Required
    private String firstName;
    // Required
    private String lastName;
    private String mobileNumber;

    // Unique and Required
    private String idNumber;
    private long addressId;
}
