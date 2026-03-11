public final class ImmutableStudent {
    private final String id;
    private final MutablePerson person;

    public ImmutableStudent(String id, MutablePerson person) {
        this.id = id;
        this.person = new MutablePerson(
                person.getName(),
                person.getSecondName(),
                person.getAge()
        );
    }

    public String getId() {
        return id;
    }

    public MutablePerson getPerson() {
        return new MutablePerson(
                person.getName(),
                person.getSecondName(),
                person.getAge()
        );
    }
}

