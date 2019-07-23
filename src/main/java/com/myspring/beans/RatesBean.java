package com.myspring.beans;

import com.myspring.models.Rates;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class RatesBean {

    public static double getRates(Rates model, String currency) throws IOException{
        URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=b2a10e26f3e64e3a8a1ed1d007fb849c");

        Scanner sc = new Scanner((InputStream) url.getContent());
        String result = "";
        while (sc.hasNext()){
            result += sc.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setBase(object.getString("base"));

        JSONObject rates = object.getJSONObject("rates");
        model.setCurrency(rates.getDouble(currency));

        double curr = model.getCurrency();

        return  curr;
    }








}
