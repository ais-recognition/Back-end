package hello;

public class Customer 
{
    private String firstName, lastName, time;

    public Customer()
    {

    }

    public Customer(String firstName, String lastName, String myTime) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.time = myTime;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getTime()
    {
        return time;
    }

    @Override
    public String toString() 
    {
        return String.format("Customer [firstName = '%s', lastName = '%s', time = '%s']", firstName, lastName, time);
    }
}
