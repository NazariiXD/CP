package models;

public class Student {
    private String firstName;
    private String lastName;
    private String dormitory;
    private int roomNumber;
    private double fee;
    private int age;
    private boolean isBeneficiary;

    public Student(
            String firstName,
            String lastName,
            String dormitory,
            int roomNumber,
            double fee,
            int age,
            boolean isBeneficiary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dormitory = dormitory;
        this.roomNumber = roomNumber;
        this.fee = fee;
        this.age = age;
        this.isBeneficiary = isBeneficiary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public int getRoomNumber() {
        return roomNumber;
    }


    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isBeneficiary() {
        return isBeneficiary;
    }

    public void setBeneficiary(boolean beneficiary) {
        isBeneficiary = beneficiary;
    }

    @Override
    public String toString() {
        return """
                Name "%s %s"
                Age: %d
                Dormitory: %s
                Room: %d
                Fee: %.2f
                Beneficiary: %b
                """.formatted(firstName, lastName, age, dormitory, roomNumber, fee, isBeneficiary);
    }
}
