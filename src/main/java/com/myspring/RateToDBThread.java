package com.myspring;

import com.myspring.beans.RatesBean;
import com.myspring.models.Rates;
import com.myspring.repositories.RatesRepository;
import org.telegram.telegrambots.api.objects.Message;

public class RateToDBThread extends Thread {

    public Rates model;
    public Message message;
    public String currency = "123";
    public boolean running = true;
    public RatesBean ratesBean;
    private Bot bot;
    RatesRepository ratesRepository;

    public RateToDBThread( Bot bot , Rates model, String currency, Message message) {
        this.bot = bot;
        this.model = model;
        this.currency = currency;
        this.message = message;
        ratesRepository = new RatesRepository();
    }

    @Override
    public void run(){
        Rates rates = null;
        Rates rate = null;
        while(running){

            try{
                rates = new Rates((long) (ratesRepository.getAll().size()+1), "USD", ratesBean.getRates(model, currency) , currency );
                ratesRepository.addRates(rates);
                Long id = Long.valueOf(ratesRepository.getAll().size());
                System.out.println(id);
                rate = ratesRepository.getById(id);
                bot.sendMsg(message, rate.toString());
                this.sleep(1000*6);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
