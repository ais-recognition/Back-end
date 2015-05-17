package hello;

public class Customer 
{
    private String userName, audio, time;

    public Customer()
    {

    }

    public Customer(String userName, String audio, String myTime) 
    {
        this.userName = userName;
        this.audio = audio;
        this.time = myTime;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getAudio()
    {
        return audio;
    }

    public String getTime()
    {
        return time;
    }

    @Override
    public String toString() 
    {
        return String.format("Customer [userName = '%s', audio = '%s', time = '%s']", userName, audio, time);
    }
}
