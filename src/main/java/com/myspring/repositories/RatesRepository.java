package com.myspring.repositories;

import com.myspring.beans.DBUtil;
import com.myspring.models.Rates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RatesRepository {

    public void addRates(Rates rate){
        String sql = "insert into rates(id, base, currency, rates) values (?, ?, ?, ?)";
        Connection connection = DBUtil.getConnection();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1,rate.getId());
            stmt.setString(2,rate.getBase());
            stmt.setDouble(3,rate.getCurrency());
            stmt.setString(4,rate.getRates());
            stmt.execute();
        }catch ( Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<Rates> getAll(){
        ArrayList<Rates> rates = new ArrayList<Rates>();
        String sql = "select*from rates";
        Connection connection = DBUtil.getConnection();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Rates rate = null;
            while (rs.next()){
                int id = rs.getInt("id");
                String base = rs.getString("base");
                double currency = rs.getDouble("currency");
                String getrate = rs.getString("rates");
                rate = new Rates((long) id, base, currency, getrate);
                rates.add(rate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rates;
    }

    public Rates getById(Long id){
        Rates rate = null;
        Connection connection = DBUtil.getConnection();
        try{
            PreparedStatement st = connection.prepareStatement("select * from rates where id =(?)");
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            if(result.next()){
                id = result.getLong("id");
                String base = result.getString("base");
                double currency = result.getDouble("currency");
                String rates = result.getString("rates");
                rate = new Rates(id, base, currency, rates);
                st.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return rate;

    }





}