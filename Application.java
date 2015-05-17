package hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class Application implements CommandLineRunner
{
    public static void main(String args[]) 
    {
        SpringApplication.run(Application.class, args);

        
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception
    {
    	jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(id SERIAL, name VARCHAR(255), audio VARCHAR(255), mytime TIMESTAMP);");

        String[] entries = new String[]{    "Yuannan Cai http://david.guerrero.free.fr/Effects/SuperSheepRelease.wav 2008-11-11 13:56:40", 
                                            "Wenyu Zhang http://www.sounds.beachware.com/2illionzayp3may/hupthsz/COW3.mp3 2008-11-11 14:56:40", 
                                            "Zhilei Miao http://100megsfree4.com/cattypaws/golfball2.wav 2008-11-11 15:56:40"
                                        };

        for (String element : entries) 
        {
            String[] field = element.split(" ");
            jdbcTemplate.update("INSERT INTO customers(name, audio, mytime) VALUES(?,?,?)", field[0] + " " + field[1], field[2], field[3] + " " + field[4]);
        }
    }
}
