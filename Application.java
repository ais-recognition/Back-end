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
    	jdbcTemplate.execute("drop table customers if exists");
        jdbcTemplate.execute("create table customers(id serial, first_name varchar(255), last_name varchar(255), time varchar(255))");

        String[] entries = new String[]{    "Yuannan Cai 2015_3_29", 
                                            "Wenyu Zhang 2015_4_1", 
                                            "Zhilei Miao 2015_4_2", 
                                            "Xi Chen 2015_4_1",
                                            "Ao Hong 2015_3_27",
                                            "Yuzhong Ji 2015_2_10",
                                            "Junkai Yan 2015_3_28"};

        for (String element : entries) 
        {
            String[] field = element.split(" ");
            jdbcTemplate.update("INSERT INTO customers(first_name,last_name,time) values(?,?,?)", field[0], field[1], field[2]);
        }
    }
}
