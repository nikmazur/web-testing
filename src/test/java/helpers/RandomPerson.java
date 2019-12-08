package helpers;

import com.github.javafaker.Faker;

public class RandomPerson {

    public final String firstName;
    public final String lastName;
    public final String email;
    public final String phone;
    public final String address;
    public final String city;
    public final String state;
    public final String zipCode;
    public final String website;

    //Generates random person data using Java Faker
    public RandomPerson() {
        Faker faker = new Faker();
        this.firstName = faker.name().firstName();
        this.lastName = faker.name().lastName();
        this.email = faker.internet().emailAddress();
        this.phone = faker.phoneNumber().phoneNumber();
        this.address = faker.address().streetAddress();
        this.city = faker.address().cityName();
        this.state = faker.address().state();
        this.zipCode = faker.address().zipCode();
        this.website = faker.internet().url();
    }

    @Override
    public String toString() {
        return "RandomPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
