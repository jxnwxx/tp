package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Nric nric;
    private final Gender gender;
    private final Phone phone;
    private final Email email;
    private final DateOfBirth dateOfBirth;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final ArrayList<Appointment> appointments;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Nric nric, Gender gender, Phone phone, Email email, DateOfBirth dateOfBirth,
                  Address address, Set<Tag> tags, ArrayList<Appointment> appointments) {
        requireAllNonNull(name, nric, gender, phone, email, dateOfBirth, address, tags, appointments);
        this.name = name;
        this.nric = nric;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.tags.addAll(tags);
        this.appointments = appointments;
    }

    public Nric getNric() {
        return nric;
    }

    public DateOfBirth getDob() {
        return dateOfBirth;
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getNric().equals(getNric());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && nric.equals(otherPerson.nric)
                && gender.equals(otherPerson.gender)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && dateOfBirth.equals(otherPerson.dateOfBirth)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, gender, phone, email, dateOfBirth, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("gender", gender)
                .add("phone", phone)
                .add("email", email)
                .add("dob", dateOfBirth)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
