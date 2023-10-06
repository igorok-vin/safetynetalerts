package com.safetynet.safetynetalerts.model;

import java.util.List;
import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private String age;
    private List<String> medications;
    private List<String> allergies;

    public Person(String firstName, String lastName, String address, String city,
                  String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    public Person(String firstName, String lastName, String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Person(String firstName, String lastName, String address, String age,
                  String email, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.medications = medications;
        this.allergies = allergies;
    }

    public Person(String firstName, String lastName, String phone, String age,
                  List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    public Person(String firstName, String lastName, String phone, String age,
                  List<String> medications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;

    }

    public Person() {
        this.firstName = "";
        this.lastName = "";
        this.address = "";
        this.city = "";
        this.zip = "";
        this.phone = "";
        this.email = "";
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public List<String> getMedications() { return medications; }
    public void setMedications(List<String> medications) { this.medications = medications; }

    public List<String> getAllergies() { return allergies; }
    public void setAllegies(List<String> allegies) { this.allergies = allegies; }

    @Override
    public String toString() {
        return "Persons [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
                + ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Person person = (Person) obj;
        return (person.firstName.equals(this.firstName) && (person.lastName.equals(this.lastName)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}

