public final class ImmutableStudent {
    private final String id;
    private final MutablePerson person;

    public ImmutableStudent(String id, MutablePerson person) {
        this.id = id;
        this.person = person.clone();

    }

    public String getId() {
        return id;
    }

    public MutablePerson getPerson() {
        return person.clone();
    }
}

