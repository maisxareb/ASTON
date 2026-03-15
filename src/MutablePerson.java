class MutablePerson implements Cloneable {
    private String name;
    private String secondName;
    private int age;

    public MutablePerson(String name, String secondName, int age) {
        this.age = age;
        this.name = name;
        this.secondName = secondName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public MutablePerson clone() {
        try {
            return (MutablePerson) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Невозможно клонировать объект MutablePerson", e);
        }
    }

    @Override
    public String toString() {
        return name + " " + secondName + " " + age;
    }
}
