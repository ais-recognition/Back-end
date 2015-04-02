package hello;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@RestController
public class TestController 
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<Customer> checking( @RequestParam(value="offset", defaultValue="1") String offset, @RequestParam(value="limit", defaultValue="1") String limit)
    {
        int startId = Integer.parseInt(offset);
        int length = Integer.parseInt(limit);

        List<Customer> results = new LinkedList<Customer>();

        for(int i = startId; i < startId + length; i++)
        {
            results.add(findById(i, jdbcTemplate));
        }

        return results;

        //return findById(startId, jdbcTemplate);
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String adding(@RequestBody Customer newEntry)
    {
        String firstName = newEntry.getFirstName();
        String lastName = newEntry.getLastName();
        String time = newEntry.getTime();

        jdbcTemplate.update("INSERT INTO customers(first_name,last_name,time) values(?,?,?)", firstName, lastName, time);

        return "Done !";
    }

    public static Customer findById(int id, JdbcTemplate jdbcTemplate)
    {

        String sql = "select id, first_name, last_name, time from customers where id = ?";

        Customer current = (Customer) jdbcTemplate.queryForObject(sql, new Object[] { id }, new RowMapper<Customer>() 
                {
                    @Override
                    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException 
                    {
                        return new Customer(rs.getString("first_name"), rs.getString("last_name"), rs.getString("time"));
                    }
                });

        return current;
    }
}

