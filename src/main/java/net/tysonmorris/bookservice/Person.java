package net.tysonmorris.bookservice;

public class Person {

    private String firstName;
    private String lastName;

    Person (String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String toString() {
        return String.format(
                "{ \"firstName\" : \"%s\", \"lastName\" : \"%s\" }",
                this.firstName,
                this.lastName
        );
    }
}
