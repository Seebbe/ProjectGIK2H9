package com.example.demo.timers;

import com.example.demo.models.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EndAuctionTask extends TimerTask {
    private Item item;
    private Connection connection;

    @Override
    public void run() {
            item.setEnabled(0);
            updateItemInDb();
            System.out.println(item.getName() + " ändrad!" + item.getEnabled());
        System.out.println(item.getName() + " auction end time has ended. Set to enabled = 0");

        //finns det en vinnare? osv
        //koda koda
        System.out.println("Skicka mail till vinnaren om det finns en");
        System.out.println("Lägg till i tabellen completedauction");
    }

    public void updateItemInDb() {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auktionssajt", "marcus", "password");
            String sqlUpdateItem = "UPDATE item SET enabled=? WHERE id = " + this.item.getId();
            PreparedStatement statement = connection.prepareStatement(sqlUpdateItem);
            statement.setString(1, Integer.toString(this.item.getEnabled()));
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(EndAuctionTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void setItem(Item item) {
        this.item = item;
    }
}