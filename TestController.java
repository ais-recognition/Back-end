package hello;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.*;
import java.util.List;
import java.lang.System;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@RestController
public class TestController 
{
    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<Customer> checking( @RequestParam(value="offset", defaultValue="2") String offset, @RequestParam(value="limit", defaultValue="1") String limit)
    {
        int startId = Integer.parseInt(offset);
        int length = Integer.parseInt(limit);

        startId = counter.get() + 1;
        length = 3;

        List<Customer> results = new LinkedList<Customer>();

        for(int i = startId; i < startId + length; i++)
        {
            results.add(findById(i, jdbcTemplate));
        }

        return results;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String adding(@RequestBody Customer newEntry)
    {   
        System.out.println(newEntry);

        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        formatter.setTimeZone(tzInAmerica);
        Date date= new Date();
        

        String userName = newEntry.getUserName();
        String audio = newEntry.getAudio();
        String myTime = formatter.format(date).toString();

        jdbcTemplate.update("INSERT INTO customers(name, audio, mytime) values(?,?,?)", userName, audio, myTime);

        counter.incrementAndGet();

        return userName + " is added !";
    }

    public static Customer findById(int id, JdbcTemplate jdbcTemplate)
    {

        String sql = "select id, name, audio, mytime from customers where id = ?";

        Customer current = (Customer) jdbcTemplate.queryForObject(sql, new Object[] { id }, new RowMapper<Customer>() 
                {
                    @Override
                    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException 
                    {
                        return new Customer(rs.getString("name"), rs.getString("audio"), rs.getString("mytime"));
                    }
                });

        return current;
    }
}

